package com.alma.pay2bid.client.observable;

import com.alma.pay2bid.client.observer.IObserver;

/**
 *
 */
public interface IObservable<T, Observer extends IObserver<T>> {
    boolean addObserver(Observer observer);

    boolean removeObserver(Observer observer);
}
