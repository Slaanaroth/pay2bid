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
 * Created by E122371M on 27/09/16.
 */
public class Server extends UnicastRemoteObject implements IServer {

    private static final Logger LOGGER = Logger.getLogger("com.alma.pay2bid.Server.logger");
    private List<IClient> clients = new ArrayList<>();
    private Queue<Auction> auctions = new ConcurrentLinkedQueue<>();

    public Server() throws RemoteException {

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

    }

    public void time_elapsed(IClient client) throws RemoteException {

    }
}
