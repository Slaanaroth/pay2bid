package com.alma.pay2bid;

import com.alma.pay2bid.common.IClient;
import com.alma.pay2bid.common.IServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

/**
 * @author Thomas Minier
 * @date 27/09/16
 */
public class Server extends UnicastRemoteObject implements IServer {

    private static final Logger LOGGER = Logger.getLogger("com.alma.pay2bid.Server.logger");
    private Auction currentAuction;
    private IClient winner;
    private int nbParticipants = 0;
    private List<IClient> clients = new ArrayList<>();
    private Queue<Auction> auctions = new ConcurrentLinkedQueue<>();

    public Server() throws RemoteException {

    }

    public void placeAuction(Auction auction) throws RemoteException {
        auctions.add(auction);
    }

    public synchronized void register(IClient client) throws RemoteException {
        try {
            wait();
            clients.add(client);
        } catch (InterruptedException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    private synchronized void validateRegistrations() {
        notifyAll();
    }

    public void raiseBid(IClient client, int newBid) throws RemoteException {
        // TODO : see if this method need to be synchronized
        int currentPrice = auctions.element().getPrice();
        auctions.element().setPrice(currentPrice + newBid);
        winner = client;
    }

    public void timeElapsed(IClient client) throws RemoteException {
        nbParticipants--;

        // TODO : what if two clients reach this block when nbParticipants == 0 ? needs to synchronized ? 
        if(nbParticipants == 0) {
            // notify all the clients to show the winner
            for(IClient c : clients) {
                c.bidSold(winner);
            }
            // validate the registrations of clients in the monitor's queue
            validateRegistrations();
            nbParticipants = clients.size();
            // TODO : if the queue is empty, wait for a new auction to be placed
            // launch the next auction
            currentAuction = auctions.poll();
            // ...
        }

    }
}
