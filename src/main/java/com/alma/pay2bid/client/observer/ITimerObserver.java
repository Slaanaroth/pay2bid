package com.alma.pay2bid.client.observer;

import com.alma.pay2bid.client.IClient;

/**
 * @author Thomas Minier
 * @date 03/10/16
 */
public interface ITimerObserver /*extends IObserver<IClient>*/ {
    void updateTimer(String time);
}
