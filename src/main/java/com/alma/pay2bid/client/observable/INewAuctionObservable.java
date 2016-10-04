package com.alma.pay2bid.client.observable;

/*import com.alma.pay2bid.bean.AuctionBean;*/

import com.alma.pay2bid.client.observer.INewAuctionObserver;

/**
 *
 */
public interface INewAuctionObservable /*extends IObservable<AuctionBean, INewAuctionObserver>*/ {
    boolean addNewAuctionObserver(INewAuctionObserver observer);

    boolean removeNewAuctionObserver(INewAuctionObserver observer);
}
