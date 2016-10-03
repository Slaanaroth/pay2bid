package com.alma.pay2bid.common;

import com.alma.pay2bid.Auction;
import com.alma.pay2bid.gui.observers.BidSoldObserver;
import com.alma.pay2bid.gui.observers.NewAuctionObserver;
import com.alma.pay2bid.gui.observers.NewPriceObserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by E122371M on 27/09/16.
 */
public interface IClient extends Remote {
    void newAuction(Auction auction) throws RemoteException;

    void submit(Auction auction) throws RemoteException;

    void bidSold(IClient buyer) throws RemoteException;

    void newPrice(int price) throws RemoteException;

    String getName() throws RemoteException;

    void registerBidSoldObserver(BidSoldObserver observer) throws RemoteException;

    void registerNewAuctionObserver(NewAuctionObserver observer) throws RemoteException;

    void registerNewPriceObserver(NewPriceObserver observer) throws RemoteException;
}
