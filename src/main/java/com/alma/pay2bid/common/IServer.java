package com.alma.pay2bid.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by E122371M on 27/09/16.
 */
public interface IServer extends Remote {
    void register(IClient client) throws RemoteException;
    void raise_bid(IClient client, int new_bid) throws RemoteException;
    void time_elapsed(IClient client) throws RemoteException;
}
