package com.alma.pay2bid.client.observable;

/*import com.alma.pay2bid.client.IClient;*/

import com.alma.pay2bid.client.observer.IBidSoldObserver;

/**
 *
 */
public interface IBidSoldObservable /*extends IObservable<IClient, IBidSoldObserver>*/ {
    boolean addBidSoldObserver(IBidSoldObserver observer);

    boolean removeBidSoldObserver(IBidSoldObserver observer);
}
