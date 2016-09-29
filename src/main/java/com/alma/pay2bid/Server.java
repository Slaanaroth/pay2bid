package com.alma.pay2bid;

import com.alma.pay2bid.common.IClient;
import com.alma.pay2bid.common.IServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.logging.Logger;

/**
 * @author Thomas Minier
 * @date 27/09/16
 */
public class Server extends UnicastRemoteObject implements IServer {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getCanonicalName());
    private Auction currentAuction;
    private IClient winner;
    private int nbParticipants = 0;
    private List<IClient> clients = new ArrayList<>();
    private BlockingQueue<Auction> auctions = new LinkedBlockingQueue<>();

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

    public synchronized void raiseBid(IClient client, int newBid) throws RemoteException {
        int currentPrice = auctions.element().getPrice();
        auctions.element().setPrice(currentPrice + newBid);
        winner = client;
    }

    public synchronized void timeElapsed(IClient client) throws RemoteException, InterruptedException {
        nbParticipants--;
        
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
            currentAuction = auctions.take();
            // ...
        }
    }
}
