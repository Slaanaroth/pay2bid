package com.alma.pay2bid.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by E122371M on 27/09/16.
 */
public interface IServer extends Remote {
    void inscription(IClient client) throws RemoteException;
    void encherir(IClient client, int bid) throws RemoteException;
    void temps_ecoule(IClient client) throws RemoteException;
}
