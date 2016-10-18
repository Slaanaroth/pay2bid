package com.alma.pay2bid.client.observable;

import com.alma.pay2bid.client.observer.ITimerObserver;

/**
 *
 */
public interface ITimerObservable  {
    boolean addTimerObserver(ITimerObserver observer);

    boolean removeTimerObserver(ITimerObserver observer);
}
