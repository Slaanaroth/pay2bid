package com.alma.pay2bid.common;

import com.alma.pay2bid.Auction;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by E122371M on 27/09/16.
 */
public interface IClient extends Remote {
    void submit(Auction auction) throws RemoteException;
    void bidSold(IClient buyer) throws RemoteException;
    void newPrice(int price) throws RemoteException;
}
