package com.alma.pay2bid.client.observable;

import com.alma.pay2bid.client.observer.INewPriceObserver;

/**
 *
 */
public interface INewPriceObservable /*extends IObservable<Integer, INewPriceObserver>*/ {
    boolean addNewPriceObserver(INewPriceObserver observer);

    boolean removeNewPriceObserver(INewPriceObserver observer);
}
