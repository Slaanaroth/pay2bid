package com.alma.pay2bid.gui.observers;

import com.alma.pay2bid.Auction;

/**
 * @author Thomas Minier
 * @date 03/10/16
 */
public interface NewAuctionObserver {
    void execute(Auction auction);
}
