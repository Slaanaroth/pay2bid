package com.alma.pay2bid.gui;

import com.alma.pay2bid.Auction;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Folkvir on 02/10/2016.
 */
public class AuctionGui {
    Auction auction;
    JPanel auctionPanel;
    JLabel auctionPrice;
    JLabel auctionPriceLabel;
    JTextField auctionBid;
    JLabel auctionBidLabel;

    public AuctionGui(Auction a){
        auction = a;
        auctionPanel  = new JPanel();
        auctionPanel.setMaximumSize(new Dimension(500, 150));
        auctionPanel.setLayout(new GridLayout(4, 3, 5, 5));
        //p.setBackground(Color.cyan);

        //CREATE THE PRICE LABEL
        auctionPrice = new JLabel(" Price : ");
        auctionPriceLabel = new JLabel("");
        auctionPriceLabel.setText(Integer.toString(a.getPrice()));
        auctionPriceLabel.setLabelFor(auctionPrice);
        auctionPanel.add(auctionPrice);
        auctionPanel.add(auctionPriceLabel);

        //CREATE THE BID FIELD
        auctionBid = new JTextField("",JLabel.TRAILING);
        auctionBidLabel = new JLabel("Bid : ");

        auctionBidLabel.setLabelFor(auctionBid);
        auctionPanel.add(auctionBidLabel);
        auctionPanel.add(auctionBid);
        auctionPanel.setBorder(BorderFactory.createTitledBorder(a.getName()));
    }

    public void  setProperties(Auction a){
        auctionPriceLabel.setText(String.valueOf(a.getPrice()));
    }
}
