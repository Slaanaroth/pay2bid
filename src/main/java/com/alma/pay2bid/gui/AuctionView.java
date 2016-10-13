package com.alma.pay2bid.gui;

import com.alma.pay2bid.bean.AuctionBean;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

/**
 * Created by Folkvir on 02/10/2016.
 */
public class AuctionView {
    AuctionBean auction;

    /**
     * Properties for the main panel
     */
    private JPanel auctionPanel;
    private JLabel auctionPriceLabel;
    private JLabel auctionPriceValue;
    private JLabel auctionTimer;
    private JLabel auctionTimerLabel;
    private JTextField auctionBid;
    private JLabel auctionBidLabel;
    private JButton raiseButton;

    public AuctionView(AuctionBean a){
        auction = a;
        auctionPanel = new JPanel();
        auctionPanel.setMaximumSize(new Dimension(500, 150));
        auctionPanel.setLayout(new GridLayout(4, 3, 5, 5));

        // Create the price label
        auctionPriceLabel = new JLabel(" Price : ");
        auctionPriceValue = new JLabel("");
        auctionPriceValue.setText(Integer.toString(a.getPrice()));
        auctionPriceValue.setLabelFor(auctionPriceLabel);
        auctionPanel.add(auctionPriceLabel);
        auctionPanel.add(auctionPriceValue);

        // Create the bid field
        auctionBid = new JTextField("", JLabel.TRAILING);
        auctionBidLabel = new JLabel("Bid : ");

        auctionBidLabel.setLabelFor(auctionBid);
        auctionPanel.add(auctionBidLabel);
        auctionPanel.add(auctionBid);
        auctionPanel.setBorder(BorderFactory.createTitledBorder(a.getName()));

        //Create the timer label
        auctionTimer = new JLabel("0");
        auctionTimerLabel = new JLabel("Remaining time : ");
        auctionTimer.setLabelFor(auctionTimerLabel);
        auctionPanel.add(auctionTimerLabel);
        auctionPanel.add(auctionTimer);
    }

    public void enable() {
        auctionBid.setVisible(true);
        auctionBidLabel.setVisible(true);
        raiseButton.setVisible(true);
    }

    public void disable() {
        // remove the input elements
        auctionBid.setVisible(false);
        auctionBidLabel.setVisible(false);
        raiseButton.setVisible(false);
    }

    public void setRaiseButton(JButton raiseButton) {
        this.raiseButton = raiseButton;
        auctionPanel.add(raiseButton, 6);
    }

    public void setPrice(int newPrice){
        auctionPriceValue.setText(String.valueOf(newPrice));
    }

    public JTextField getAuctionBid() {
        return auctionBid;
    }

    public JPanel getAuctionPanel() {
        return auctionPanel;
    }
}
