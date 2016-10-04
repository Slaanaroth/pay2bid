package com.alma.pay2bid.client.observer;

/**
 * @author Thomas Minier
 * @date 03/10/16
 */
public interface INewPriceObserver /*extends IObserver<Integer>*/ {
    void updateNewPrice(Integer price);
}
