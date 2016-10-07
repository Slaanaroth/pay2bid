package com.alma.pay2bid.gui.listeners;

import com.alma.pay2bid.gui.AuctionGui;
import com.alma.pay2bid.gui.ClientGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Folkvir on 02/10/2016.
 */
public class AuctionGuiListener implements ActionListener{
    private AuctionGui auction;
    private ClientGui client;

    public AuctionGuiListener(AuctionGui a, ClientGui c){
        this.auction = a;
        this.client = c;
    }
    public void actionPerformed(ActionEvent e) {
        System.out.println("actionPerformed into AuctionGuiListener");
        try{
            int i = Integer.valueOf(auction.price.getText());
            System.out.println("new Auction available");
            client.sendAuction(auction);
        }catch(Exception exp){
            auction.statusAuction.setText("Price must be an Integer");
        }
    }
}
