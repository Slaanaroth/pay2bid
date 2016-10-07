package com.alma.pay2bid.client;

import com.alma.pay2bid.bean.AuctionBean;
import com.alma.pay2bid.client.observer.IBidSoldObserver;
import com.alma.pay2bid.client.observer.INewAuctionObserver;
import com.alma.pay2bid.client.observer.INewPriceObserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 */
public interface IClient extends Remote {
    /**
     * @param auction
     * @throws RemoteException
     */
    void newAuction(AuctionBean auction) throws RemoteException;

    /**
     * @param auction
     * @throws RemoteException
     */
    void submit(AuctionBean auction) throws RemoteException;

    /**
     * @param buyer
     * @throws RemoteException
     */
    void bidSold(IClient buyer) throws RemoteException;

    /**
     * @param price
     * @throws RemoteException
     */
    void newPrice(int price,AuctionBean auction) throws RemoteException;

    /**
     * @return
     * @throws RemoteException
     */
    String getName() throws RemoteException;
}
