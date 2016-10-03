package com.alma.pay2bid.gui.observers;

import com.alma.pay2bid.common.IClient;

/**
 * @author Thomas Minier
 * @date 03/10/16
 */
public interface BidSoldObserver {
    void execute(IClient buyer);
}
