package com.alma.pay2bid.client.observer;

import com.alma.pay2bid.bean.AuctionBean;

import java.util.UUID;

/**
 * @author Thomas Minier
 * @date 03/10/16
 */
public interface INewPriceObserver /*extends IObserver<Integer>*/ {
    void updateNewPrice(UUID auctionID, Integer price);
}
