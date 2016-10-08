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
public class RaiseBidButtonListener implements ActionListener {
    private ClientGui gui;
    private AuctionGui auction;
    private IClient client;
    private IServer server;

    public RaiseBidButtonListener(ClientGui gui, AuctionGui auction, IClient client, IServer server) {
        this.gui = gui;
        this.auction = auction;
        this.client = client;
        this.server = server;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        if("raiseBid".equals(command))  {
            gui.getStatusLabel().setText("New bid sent.");
            try {
                server.raiseBid(client, Integer.valueOf(auction.getAuctionBid().getText()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
