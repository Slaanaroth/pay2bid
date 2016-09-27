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

    public void submit(Auction auction) throws RemoteException {

    }

    public void bid_sold(IClient buyer) throws RemoteException {

    }

    public void new_price(int price) throws RemoteException {

    }
}
