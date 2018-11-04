package com.alma.pay2bid.gui.listeners;

import com.alma.pay2bid.client.Client;
import com.alma.pay2bid.client.IClient;
import com.alma.pay2bid.gui.AuctionView;
import com.alma.pay2bid.server.IServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

/**
 * An ActionListener called to raise the bid of an item
 * @author Alexis Giraudet
 * @author Arnaud Grall
 * @author Thomas Minier
 */
public class SkipTurnButtonListener implements ActionListener {
    private IClient client;
    private IServer server;
    private AuctionView auctionView;
    private JLabel statusLabel;

    public SkipTurnButtonListener(IClient client, IServer server, AuctionView gui, JLabel statusLabel) {
        this.client = client;
        this.server = server;
        auctionView = gui;
        this.statusLabel = statusLabel;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        if("skipTurn".equals(command))  {
            try {
                statusLabel.setText("Turn skipped");
                auctionView.disable();
                server.timeElapsed(this.client);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
