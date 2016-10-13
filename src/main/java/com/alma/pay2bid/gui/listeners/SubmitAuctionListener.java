package com.alma.pay2bid.gui.listeners;

import com.alma.pay2bid.bean.AuctionBean;
import com.alma.pay2bid.client.IClient;
import com.alma.pay2bid.gui.AuctionView;
import com.alma.pay2bid.gui.AuctionInput;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Folkvir on 02/10/2016.
 */
public class SubmitAuctionListener implements ActionListener{
    private AuctionView auction;
    private IClient client;
    private AuctionInput input;

    public SubmitAuctionListener(IClient c, AuctionInput input) {
        this.client = c;
        this.input = input;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("actionPerformed into SubmitAuctionListener");
        try {
            // send the new auction to the server through the client
            AuctionBean a = new AuctionBean(Integer.parseInt(input.getAuctionPrice()), input.getAuctionName(), input.getDescription());
            client.submit(a);

            // close the menu & refresh the status label
            input.hideFrame();
            input.getStatusLabel().setText("New auction sent...");
        } catch(Exception e) {
            input.getStatusLabel().setText("Price must be an Integer");
        }
    }
}
