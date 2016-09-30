package com.alma.pay2bid;

import com.alma.pay2bid.common.IClient;
import com.alma.pay2bid.common.IServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;

/**
 * @author Thomas Minier
 * @date 27/09/16
 */
public class Server extends UnicastRemoteObject implements IServer {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getCanonicalName());

    private boolean auctionInProgress = false;
    private Auction currentAuction;
    private IClient winner;
    private int nbParticipants = 0;
    private List<IClient> clients = new ArrayList<IClient>();
    private Queue<Auction> auctions = new LinkedList<Auction>();

    public Server() throws RemoteException {

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
    public synchronized void placeAuction(Auction auction) throws RemoteException {
        auctions.add(auction);
        if (auctions.size() == 1) {
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
        int currentPrice = currentAuction.getPrice();
        int newPrice = currentPrice + newBid;
        currentAuction.setPrice(newPrice);
        winner = client;

        LOGGER.info("New bid '" + currentPrice + "' placed by client " + client.toString());

        // transmit the new price to the clients
        for (IClient c : clients) {
            c.newPrice(newPrice);
        }

        //TODO: reamorcer chrono ?
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
            // notify all the clients to show the winner
            for (IClient c : clients) {
                c.bidSold(winner);
            }

            // validate the registrations of clients in the monitor's queue
            validateRegistrations();

            // launch the next auction if there is one available
            if (!auctions.isEmpty()) {
                launchAuction();
            }
        }
    }
}
