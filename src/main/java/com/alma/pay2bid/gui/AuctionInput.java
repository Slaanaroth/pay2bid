package com.alma.pay2bid.gui;

import com.alma.pay2bid.client.IClient;
import com.alma.pay2bid.gui.listeners.SubmitAuctionListener;

import javax.swing.*;
import java.awt.*;

/**
 * @author Thomas Minier
 * @date 13/10/16
 */
public class AuctionInput extends JPanel {

    private JFrame auctionFrame;
    private JLabel nameLabel;
    private JTextField  name;
    private JLabel priceLabel;
    public JTextField  price;
    private JLabel descriptionLabel;
    private JTextField  description;
    private JLabel statusLabel;
    private final JButton auctionSend;

    public AuctionInput(IClient client) {
        setLayout(new GridLayout(4,3,5,5));

        // Frame used to displayed the input
        auctionFrame = new JFrame("Add a new auction");
        auctionFrame.setLayout(new BorderLayout());
        auctionFrame.setSize(new Dimension(500, 200));
        auctionFrame.setResizable(false);

        // Field "Name"
        nameLabel = new JLabel("Name : ");
        name = new JTextField();
        nameLabel.setLabelFor(name);

        // Field "Price"
        priceLabel = new JLabel("Price : ");
        price = new JTextField();
        priceLabel.setLabelFor(price);

        // Field "Description"
        descriptionLabel = new JLabel("Description : ");
        description = new JTextField();
        descriptionLabel.setLabelFor(description);

        // Info label at the bottom of the frame
        statusLabel = new JLabel("", JLabel.CENTER);

        // Validation button
        auctionSend = new JButton("SEND NEW AUCTION");
        auctionSend.setActionCommand("newAuction");
        auctionSend.addActionListener(new SubmitAuctionListener(client, this));

        // add all the elements to the panel
        add(nameLabel);
        add(name);

        add(priceLabel);
        add(price);

        add(descriptionLabel);
        add(description);

        add(auctionSend);

        // add the panel to the input frame
        auctionFrame.add(this, BorderLayout.CENTER);
        auctionFrame.add(statusLabel, BorderLayout.PAGE_END);
    }

    public void showFrame() {
        auctionFrame.setVisible(true);
    }

    public void hideFrame() {
        auctionFrame.setVisible(false);
    }

    public String getAuctionName() {
        return name.getText();
    }

    public String getAuctionPrice() {
        return price.getText();
    }

    public String getDescription() {
        return description.getText();
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }
}
