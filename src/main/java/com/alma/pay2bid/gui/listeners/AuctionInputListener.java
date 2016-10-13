package com.alma.pay2bid.gui.listeners;

import com.alma.pay2bid.gui.ClientGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TEST ACTION LISTENER FOR NEW AUCTION
 */
public class AuctionInputListener implements ActionListener {
    private ClientGui gui;

    public AuctionInputListener(ClientGui gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        if("newAuction".equals(command) && (gui.getAuctionFrame() == null))  {
            gui.newAuctionView();
        }
    }
}
