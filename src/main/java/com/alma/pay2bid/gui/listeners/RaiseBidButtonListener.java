package com.alma.pay2bid.gui.listeners;

import com.alma.pay2bid.client.IClient;
import com.alma.pay2bid.gui.AuctionGui;
import com.alma.pay2bid.gui.ClientGui;
import com.alma.pay2bid.server.IServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

/**
 * ACTION LISTENER FOR BUTTON
 */
public class RaiseBidButtonListener implements ActionListener {
    private IClient client;
    private IServer server;
    private JTextField bidField;
    private JLabel statusLabel;

    public RaiseBidButtonListener(IClient client, IServer server, JTextField bidField, JLabel statusLabel) {
        this.client = client;
        this.server = server;
        this.bidField = bidField;
        this.statusLabel = statusLabel;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        if("raiseBid".equals(command))  {
            statusLabel.setText("New bid sent.");
            try {
                server.raiseBid(client, Integer.valueOf(bidField.getText()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
