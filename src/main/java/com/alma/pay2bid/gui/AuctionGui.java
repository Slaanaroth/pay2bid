package com.alma.pay2bid.gui;

import com.alma.pay2bid.bean.AuctionBean;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Folkvir on 02/10/2016.
 */
public class AuctionGui{
    AuctionBean auction;

    /**
     * Properties for the main panel
     */
    private JPanel auctionPanel;
    private JLabel auctionPriceLabel;
    private JLabel auctionPriceValue;
    private JTextField auctionBid;
    private JLabel auctionBidLabel;

    /**
     * Properties for the input's action frame
     */
    private JPanel newAuctionPanel;
    private JLabel nameLabel;
    private JTextField  name;
    private JLabel priceLabel;
    public JTextField  price;
    private JLabel descriptionLabel;
    private JTextField  description;
    private JLabel statusAuction;

    public AuctionGui(AuctionBean a){
        auction = a;
        auctionPanel = new JPanel();
        auctionPanel.setMaximumSize(new Dimension(500, 150));
        auctionPanel.setLayout(new GridLayout(4, 3, 5, 5));
        //p.setBackground(Color.cyan);

        // Create the price label
        auctionPriceLabel = new JLabel(" Price : ");
        auctionPriceValue = new JLabel("");
        auctionPriceValue.setText(Integer.toString(a.getPrice()));
        auctionPriceValue.setLabelFor(auctionPriceLabel);
        auctionPanel.add(auctionPriceLabel);
        auctionPanel.add(auctionPriceValue);

        // Create the bid field
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

        // add all the elemnts to the panel
        newAuctionPanel.add(nameLabel);
        newAuctionPanel.add(name);

        newAuctionPanel.add(priceLabel);
        newAuctionPanel.add(price);

        newAuctionPanel.add(descriptionLabel);
        newAuctionPanel.add(description);
    }

    public void  setProperties(AuctionBean a){
        auctionPriceValue.setText(String.valueOf(a.getPrice()));
    }

    public JTextField getAuctionBid() {
        return auctionBid;
    }

    public JPanel getAuctionPanel() {
        return auctionPanel;
    }

    public JTextField getName() {
        return name;
    }

    public JPanel getNewAuctionPanel() {
        return newAuctionPanel;
    }

    public JTextField getDescription() {
        return description;
    }

    public JLabel getStatusAuction() {
        return statusAuction;
    }
}
