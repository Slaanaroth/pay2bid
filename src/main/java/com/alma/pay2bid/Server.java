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
    private List<IClient> clients = new ArrayList<>();
    private Queue<Auction> auctions = new ConcurrentLinkedQueue<>();

    public Server() throws RemoteException {

    }

    public void place_auction(Auction auction) throws RemoteException {
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

    public void raise_bid(IClient client, int new_bid) throws RemoteException {
        // TODO see if this method need to be synchronized
        int currentPrice = auctions.element().getPrice();
        auctions.element().setPrice(currentPrice + new_bid);
    }

    public void time_elapsed(IClient client) throws RemoteException {
        // notify that the client's chrono is finished
        // ...

        // launch the next auction if they are no more clients in the previous one
        // ...
    }
}
