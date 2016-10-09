package com.alma.pay2bid.client;

import com.alma.pay2bid.bean.AuctionBean;
import com.alma.pay2bid.client.observer.IBidSoldObserver;
import com.alma.pay2bid.client.observer.INewAuctionObserver;
import com.alma.pay2bid.client.observer.INewPriceObserver;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 *
 */
public interface IClient extends Remote, Serializable {
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
     * @param auctionID
     * @param price
     * @throws RemoteException
     */
    void newPrice(UUID auctionID, int price) throws RemoteException;

    /**
     * @return
     * @throws RemoteException
     */
    String getName() throws RemoteException;
}
