package com.alma.pay2bid.gui.listeners;

import com.alma.pay2bid.bean.AuctionBean;
import com.alma.pay2bid.client.IClient;
import com.alma.pay2bid.gui.AuctionGui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Folkvir on 02/10/2016.
 */
public class SubmitAuctionListener implements ActionListener{
    private AuctionGui auction;
    private IClient client;
    private JFrame auctionFrame;
    private JLabel statusLabel;

    public SubmitAuctionListener(AuctionGui a, IClient c, JFrame auctionFrame, JLabel statusLabel){
        this.auction = a;
        this.client = c;
        this.auctionFrame = auctionFrame;
        this.statusLabel = statusLabel;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("actionPerformed into SubmitAuctionListener");
        try {
            // send the new auction to the server through the client
            AuctionBean a = new AuctionBean(Integer.parseInt(auction.price.getText()), auction.getName().getText(), auction.getDescription().getText());
            client.submit(a);

            // close the menu & refresh the status label
            auctionFrame.setVisible(false);
            auctionFrame = null;
            statusLabel.setText("New auction sent...");
        } catch(Exception e){
            auction.getStatusAuction().setText("Price must be an Integer");
        }
    }
}
