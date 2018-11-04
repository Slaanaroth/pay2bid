package com.alma.pay2bid.gui.listeners;

import com.alma.pay2bid.bean.AuctionBean;
import com.alma.pay2bid.client.IClient;
import com.alma.pay2bid.gui.AuctionInput;
import com.alma.pay2bid.gui.AuctionView;

import java.rmi.ConnectException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An ActionListener that submit a new auction to the server
 * @author Alexis Giraudet
 * @author Arnaud Grall
 * @author Thomas Minier
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
        try {
            // send the new auction to the server through the client

            System.out.println("this the string:<"+input.getAuctionName()+">");

            if(!input.getAuctionName().isEmpty()) {



              AuctionBean a = new AuctionBean(Integer.parseInt(input.getAuctionPrice()), input.getAuctionName(), input.getDescription(), client.getName());
              client.submit(a);

              // close the menu & refresh the status label
              input.hideFrame();
              input.getStatusLabel().setText("New auction sent...");
            } else {
              input.getStatusLabel().setText("You must name whatever the hell it is that you sell");
            }

        } catch (ConnectException ce) {
            input.getStatusLabel().setText("Error: Server unavailable");
        } catch(Exception e) {
            input.getStatusLabel().setText("Price must be an Integer");
        }
    }
}
