package com.alma.pay2bid.common;

import com.alma.pay2bid.Auction;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Thomas Minier
 */
public interface IServer extends Remote {
    void place_auction(Auction auction) throws RemoteException;
    void register(IClient client) throws RemoteException;
    void raise_bid(IClient client, int new_bid) throws RemoteException;
    void time_elapsed(IClient client) throws RemoteException;
}
