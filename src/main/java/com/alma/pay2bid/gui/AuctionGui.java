package com.alma.pay2bid.gui;

import com.alma.pay2bid.bean.AuctionBean;
import com.alma.pay2bid.client.observer.INewAuctionObserver;
import com.alma.pay2bid.client.observer.IObserver;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Folkvir on 02/10/2016.
 */
public class AuctionGui{
    AuctionBean auction;

    /**
     * PROPERTIES FOR MAIN PANEL
     */
    JPanel auctionPanel;
    JLabel auctionPrice;
    JLabel auctionPriceLabel;
    public JTextField auctionBid;
    JLabel auctionBidLabel;

    /**
     * PROPERTIES FOR NEW AUCTION FRAME
     */
    JPanel newAuctionPanel;
    JLabel nameLabel;
    JTextField  name;
    JLabel priceLabel;
    public JTextField  price;
    JLabel descriptionLabel;
    JTextField  description;
    public JLabel statusAuction;

    public AuctionGui(AuctionBean a){
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

        newAuctionPanel = new JPanel();
        newAuctionPanel.setLayout(new GridLayout(4,3,5,5));

        nameLabel = new JLabel("Name : ");
        name = new JTextField();
        nameLabel.setLabelFor(name);

        priceLabel = new JLabel("Price : ");
        price = new JTextField();
        priceLabel.setLabelFor(price);

        descriptionLabel = new JLabel("Description : ");
        description = new JTextField();
        descriptionLabel.setLabelFor(description);

        statusAuction = new JLabel("",JLabel.CENTER);

        newAuctionPanel.add(nameLabel);
        newAuctionPanel.add(name);

        newAuctionPanel.add(priceLabel);
        newAuctionPanel.add(price);

        newAuctionPanel.add(descriptionLabel);
        newAuctionPanel.add(description);
    }

    public void  setProperties(AuctionBean a){
        auctionPriceLabel.setText(String.valueOf(a.getPrice()));
    }


}
