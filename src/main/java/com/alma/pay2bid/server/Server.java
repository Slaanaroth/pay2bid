package com.alma.pay2bid.server;

import com.alma.pay2bid.bean.AuctionBean;
import com.alma.pay2bid.client.IClient;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author Thomas Minier
 * @date 27/09/16
 */
public class Server extends UnicastRemoteObject implements IServer {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getCanonicalName());

    private boolean auctionInProgress = false;
    private AuctionBean currentAuction;
    private IClient winner;
    private int nbParticipants = 0;
    private List<IClient> clients = new ArrayList<IClient>();
    private Queue<AuctionBean> auctions = new LinkedList<AuctionBean>();

    public Server() throws RemoteException {
        super();
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
        if (!auctionInProgress && (auctions.size() == 1) && (clients.size() > 1)) {
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
            c.newPrice(currentAuction.getUUID(), newPrice);
        }
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

            // launch the next auction if there is one available and enough clients
            if (!auctions.isEmpty() && (clients.size() > 1)) {
                launchAuction();
            }
        }
    }

    public static void main(String[] args) {
        try {
            String name = "com.alma.pay2bid.server.Server";
            IServer server = new Server();
            //TODO: parameterize port
            int port = 1099;
            Registry registry = LocateRegistry.createRegistry(port);
            registry.rebind(name, server);
            LOGGER.info("Server up and running at localhost on port " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
