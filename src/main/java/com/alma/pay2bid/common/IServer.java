package com.alma.pay2bid.common;

import com.alma.pay2bid.Auction;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Thomas Minier
 */
public interface IServer extends Remote {
    void placeAuction(Auction auction) throws RemoteException;
    void register(IClient client) throws RemoteException;
    void raiseBid(IClient client, int newBid) throws RemoteException;
    void timeElapsed(IClient client) throws RemoteException;
}
