package com.alma.pay2bid.client.observer;

import com.alma.pay2bid.bean.AuctionBean;

/**
 * @author Thomas Minier
 * @date 03/10/16
 */
public interface INewAuctionObserver /*extends IObserver<AuctionBean>*/ {
    void updateNewAuction(AuctionBean auction);
}
