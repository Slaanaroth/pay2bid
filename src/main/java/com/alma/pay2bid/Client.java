package com.alma.pay2bid;

import com.alma.pay2bid.common.IClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by E122371M on 27/09/16.
 */
public class Client extends UnicastRemoteObject implements IClient {

    protected Client() throws RemoteException {
    }

    public void soumettre() throws RemoteException {

    }

    public void objet_vendu(IClient acheteur) throws RemoteException {

    }

    public void nouveau_prix(int prix) throws RemoteException {

    }
}
