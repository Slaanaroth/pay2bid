package com.alma.pay2bid;

import com.alma.pay2bid.common.IClient;
import com.alma.pay2bid.common.IServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by E122371M on 27/09/16.
 */
public class Server extends UnicastRemoteObject implements IServer {

    protected Server() throws RemoteException {
    }

    public void register(IClient client) throws RemoteException {

    }

    public void raise_bid(IClient client, int new_bid) throws RemoteException {

    }

    public void time_elapsed(IClient client) throws RemoteException {

    }
}
