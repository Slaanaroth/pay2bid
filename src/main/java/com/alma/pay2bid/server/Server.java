package com.alma.pay2bid.server;

import com.alma.pay2bid.bean.AuctionBean;
import com.alma.pay2bid.client.ClientState;
import com.alma.pay2bid.client.IClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author Thomas Minier
 * @date 27/09/16
 */
public class Server extends UnicastRemoteObject implements IServer {

    /**
     * Daemon responsible of detecting closed connection with clients
     */
    private class ConnectionDaemon extends TimerTask {

        @Override
        public void run() {
            List<IClient> clientsToRemove = new ArrayList<IClient>();
            for(IClient client : clients) {
                try {
                    client.getName();
                } catch (RemoteException e) {
                    LOGGER.info("detected a closed connection with " + client.toString());
                    clientsToRemove.add(client);
                }
            }
            clients.removeAll(clientsToRemove);
        }
    }

    private static final Logger LOGGER = Logger.getLogger(Server.class.getCanonicalName());
    private static final long CHECK_CONN_DELAY = 30000; // TODO : set a more accurate value for this @Thomas

    private boolean auctionInProgress = false;
    private AuctionBean currentAuction;
    private IClient winner;
    private int nbParticipants = 0;
    private List<IClient> clients = new ArrayList<IClient>();
    private Queue<AuctionBean> auctions = new LinkedList<AuctionBean>();
    private HashMap<IClient, Integer> bidByClient = new HashMap<IClient, Integer>();

    private static final int MIN_NUMBER_CLIENTS = 1;

    public Server() throws RemoteException {
        super();
        Timer daemonTimer = new Timer();
        daemonTimer.schedule(new ConnectionDaemon(), 0, CHECK_CONN_DELAY);
    }

    /**
     *
     */
    private void launchAuction() throws RemoteException {
        auctionInProgress = true;
        nbParticipants = clients.size();

        currentAuction = auctions.poll();
        LOGGER.info("Auction '" + currentAuction.getName() + "' launched !");

        // notify the client's that a new auction has begun
        for (IClient client : clients) {
            client.newAuction(currentAuction);
        }
    }

    /**
     * @param auction
     * @throws RemoteException
     */
    @Override
    public synchronized void placeAuction(AuctionBean auction) throws RemoteException {
        // generate a new UUID for the incoming auction, then put it in the queue
        auction.setUuid(UUID.randomUUID());
        auctions.add(auction);
        LOGGER.info("Auction '" + auction.getName() + "' placed in queue");
        if (!auctionInProgress && (auctions.size() == 1) && (clients.size() >= MIN_NUMBER_CLIENTS)) {
            launchAuction();
        }
    }

    /**
     * @param client
     * @throws RemoteException
     */
    @Override
    public synchronized void register(IClient client) throws RemoteException {
        try {
            while (auctionInProgress) {
                wait();
            }
            clients.add(client);
            LOGGER.info("client " + client.toString() + " connected");
        } catch (InterruptedException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    /**
     *
     * @param client
     * @throws RemoteException
     */
    @Override
    public void disconnect(IClient client) throws RemoteException {
        LOGGER.info("Disconnect : Client " + client.toString());
        clients.remove(client);
    }

    /**
     *
     */
    private synchronized void validateRegistrations() {
        auctionInProgress = false;
        notifyAll();
    }

    /**
     * @param client
     * @param newBid
     * @throws RemoteException
     */
    @Override
    public synchronized void raiseBid(IClient client, int newBid) throws RemoteException {
        if(client.getState() == ClientState.WAITING) {
            bidByClient.put(client, newBid);
            client.setState(ClientState.RAISING);
            LOGGER.info("New bid '" + newBid + "' placed by client " + client.toString());
        } // TODO : else throw a exception ? @Thomas
    }

    /**
     * @param client
     * @throws RemoteException
     * @throws InterruptedException
     */
    @Override
    public synchronized void timeElapsed(IClient client) throws RemoteException, InterruptedException {
        nbParticipants--;
        if (nbParticipants == 0) {
            // case of a blank round : the auction is completed
            if(bidByClient.size() == 0) {
                // notify all the clients to show the winner
                for (IClient c : clients) {
                    c.bidSold(winner);
                }

                // validate the registrations of clients in the monitor's queue
                validateRegistrations();

                // launch the next auction if there is one available and enough clients
                if (!auctions.isEmpty() && (clients.size() >= MIN_NUMBER_CLIENTS)) {
                    launchAuction();
                }
            } else {
                // compute the winner of the current round
                int maxBid = Integer.MIN_VALUE;
                for(Map.Entry<IClient, Integer> pair : bidByClient.entrySet()) {
                    if(pair.getValue() > maxBid) {
                        maxBid = pair.getValue();
                        winner = pair.getKey();
                    }
                }
                LOGGER.info("End of a round. Bid = " + maxBid + " - The current winner is " + client.toString());

                // clean the data structures before the next round
                nbParticipants = clients.size();
                bidByClient.clear();

                // notify the clients of the new price & start a new round
                for (IClient c : clients) {
                    c.newPrice(currentAuction.getUUID(), maxBid);
                }
            }
        }
    }
}
