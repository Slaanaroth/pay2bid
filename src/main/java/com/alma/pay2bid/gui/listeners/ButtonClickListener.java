package com.alma.pay2bid.gui.listeners;

import com.alma.pay2bid.client.IClient;
import com.alma.pay2bid.gui.AuctionGui;
import com.alma.pay2bid.gui.ClientGui;
import com.alma.pay2bid.server.IServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

/**
 * ACTION LISTENER FOR BUTTON
 */
public class ButtonClickListener implements ActionListener {
    private ClientGui gui;
    private AuctionGui auction;
    private IClient client;
    private IServer server;

    public ButtonClickListener(ClientGui gui, AuctionGui auction, IClient client, IServer server) {
        this.gui = gui;
        this.auction = auction;
        this.client = client;
        this.server = server;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if("raiseBid".equals(command))  {
            gui.getStatusLabel().setText("New bid sent.");
            try {
                server.raiseBid(client, Integer.valueOf(auction.auctionBid.getText()));
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        }
    }
}
