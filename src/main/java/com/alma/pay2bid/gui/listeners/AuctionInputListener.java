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
            /*statusLabel.setText("New Auction sent.");

            Auction a = new Auction(10,"Noix de coco x10", "");

            //TEST DE LA FONCTION SETAUCTIONPRICE
            addAuctionPanel(a);
            a.setPrice(50);
            LOGGER.info(a.getPrice());
            setAuctionPrice(a);*/
        }
    }
}
