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

    public void inscription(IClient client) throws RemoteException {

    }

    public void encherir(IClient client, int bid) throws RemoteException {

    }

    public void temps_ecoule(IClient client) throws RemoteException {

    }
}
