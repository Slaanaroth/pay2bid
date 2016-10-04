package com.alma.pay2bid.client.observer;

/**
 *
 */
public interface IObserver<T> {
    /**
     * @param t
     */
    void update(T t);
}
