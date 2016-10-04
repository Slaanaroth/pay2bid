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
    void newPrice(int price) throws RemoteException;

    /**
     * @return
     * @throws RemoteException
     */
    String getName() throws RemoteException;

    /**
     * @param observer
     * @throws RemoteException
     */
    void addBidSoldObserver(IBidSoldObserver observer) throws RemoteException;

    /**
     * @param observer
     * @throws RemoteException
     */
    void addNewAuctionObserver(INewAuctionObserver observer) throws RemoteException;

    /**
     * @param observer
     * @throws RemoteException
     */
    void addNewPriceObserver(INewPriceObserver observer) throws RemoteException;

    /**
     * @param observer
     * @throws RemoteException
     */
    void removeBidSoldObserver(IBidSoldObserver observer) throws RemoteException;

    /**
     * @param observer
     * @throws RemoteException
     */
    void removeNewAuctionObserver(INewAuctionObserver observer) throws RemoteException;

    /**
     * @param observer
     * @throws RemoteException
     */
    void removeNewPriceObserver(INewPriceObserver observer) throws RemoteException;
}
