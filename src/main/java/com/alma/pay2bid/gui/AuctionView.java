package com.alma.pay2bid.gui;

import com.alma.pay2bid.bean.AuctionBean;

import javax.swing.*;
import java.awt.*;

/**
 * The widget used to display an auction
 * @author Alexis Giraudet
 * @author Arnaud Grall
 * @author Thomas Minier
 */
public class AuctionView {

    /**
     * Properties for the main panel
     */
    private JPanel auctionPanel;
    private JLabel auctionPriceValue;
    private JLabel auctionTimer;
    private JLabel auctionTimerLabel;
    private JLabel auctionCurrentWinnerLabel;
    private JLabel auctionCurrentWinnerValue;
    private JTextArea auctionDescriptionValue;
    private JTextField auctionBid;
    private JLabel auctionBidLabel;
    private JButton raiseButton;
    private JButton skipButton;

    public AuctionView(AuctionBean auction){
        auctionPanel = new JPanel();
        auctionPanel.setMaximumSize(new Dimension(500, 300));
        auctionPanel.setLayout(new GridLayout(7, 3, 5, 5));

        // Create the price label
        JLabel auctionPriceLabel = new JLabel(" Price : ");
        auctionPriceValue = new JLabel("");
        auctionPriceValue.setText(Integer.toString(auction.getPrice()));
        auctionPriceValue.setLabelFor(auctionPriceLabel);
        auctionPanel.add(auctionPriceLabel);
        auctionPanel.add(auctionPriceValue);

        // Create the price description
        JLabel auctionDescriptionLabel = new JLabel(" Description : ");
        auctionDescriptionValue = new JTextArea(auction.getDescription(),10,18);
        auctionDescriptionValue.setLineWrap(true);
        auctionDescriptionValue.setEditable(false);
        auctionPanel.add(auctionDescriptionLabel);
        auctionPanel.add(auctionDescriptionValue);

        // Create the currentWinner label
        auctionCurrentWinnerLabel = new JLabel(" Current Winner is : ");
        auctionCurrentWinnerValue = new JLabel("Wait the second turn !");
        auctionCurrentWinnerValue.setText("Wait the second turn !");
        auctionCurrentWinnerValue.setLabelFor(auctionCurrentWinnerLabel);
        auctionPanel.add(auctionCurrentWinnerLabel);
        auctionPanel.add(auctionCurrentWinnerValue);

        //Create the timer label
        auctionTimer = new JLabel("0");
        auctionTimerLabel = new JLabel("Remaining time : ");
        auctionTimer.setLabelFor(auctionTimerLabel);
        auctionPanel.add(auctionTimerLabel);
        auctionPanel.add(auctionTimer);

        // Create the bid field
        auctionBid = new JTextField("", JLabel.TRAILING);
        auctionBidLabel = new JLabel("New Price : ");

        auctionBidLabel.setLabelFor(auctionBid);
        auctionPanel.add(auctionBidLabel);
        auctionPanel.add(auctionBid);
        auctionPanel.setBorder(BorderFactory.createTitledBorder(auction.getName()));


    }

    public void setCurrentWinner(String name) {
        auctionCurrentWinnerValue.setText(name);
    }

    public void enable() {
        auctionBid.setVisible(true);
        auctionBidLabel.setVisible(true);
        raiseButton.setVisible(true);
        auctionTimer.setVisible(true);
        auctionTimerLabel.setVisible(true);
        skipButton.setVisible(true);
    }

    public void disable() {
        // remove the input elements
        auctionBid.setVisible(false);
        auctionBidLabel.setVisible(false);
        raiseButton.setVisible(false);
        auctionTimer.setVisible(false);
        auctionTimerLabel.setVisible(false);
        skipButton.setVisible(false);
    }

    public void setWinner(String name) {
        if (name == null){
            auctionBidLabel.setText("No winner for this auction");
        }
        else {
            auctionBidLabel.setText("Winner : " + name);
        }
        auctionBidLabel.setVisible(true);
        auctionCurrentWinnerLabel.setVisible(false);
        auctionCurrentWinnerValue.setVisible(false);
        auctionBid.setVisible(false);
        raiseButton.setVisible(false);
        auctionTimer.setVisible(false);
        auctionTimerLabel.setVisible(false);
        skipButton.setVisible(false);
    }

    public void setRaiseButton(JButton raiseButton) {
        this.raiseButton = raiseButton;
        auctionPanel.add(raiseButton);
    }

    public void setSkipButton(JButton skipButton) {
        this.skipButton = skipButton;
        auctionPanel.add(skipButton);
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

    public void setAuctionTimer(String time) {
        this.auctionTimer.setText(time);
    }

    public void setEnableBidTextField(boolean b) {
        this.auctionBid.setEnabled(b);
    }

    public void setEnableBidTextFieldVisible(boolean b) {
        this.auctionBid.setVisible(b);
        this.auctionBidLabel.setVisible(b);
    }

    public void timerError() {
        this.auctionPanel.removeAll();
        this.auctionPanel.setBorder(BorderFactory.createEmptyBorder());
        this.auctionPanel.add(new JLabel("Connection lost to the server"));
        this.auctionPanel.revalidate();
        this.auctionPanel.repaint();
    }
}
