package com.alma.pay2bid.gui;

import com.alma.pay2bid.Auction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Folkvir(Grall Arnaud)) on 28/09/16.
 */
public class ClientGui {
    private JFrame mainFrame;

    private JMenuBar menuBar;

    private JLabel headerLabel;
    private JLabel statusLabel;

    private JPanel controlPanel;
    private JPanel mainPanel;
    private JPanel auctionPanel;

    private JButton newAction;
    private JButton raiseBid;

    private ArrayList<Auction> auctionList;

    private HashMap<String,JPanel> auctionListPanel;
    /**
     * Constructor
     */
    public ClientGui(){
        auctionList = new ArrayList<Auction>();
        auctionListPanel = new HashMap<String,JPanel>();
        createGui();
    }

    private void createGui(){
        mainFrame = new JFrame("Pay2Bid - Auction");
        mainFrame.setSize(500,500);
        mainFrame.setLayout(new BorderLayout());


        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        menu.add(new JMenuItem("New Auction"));
        menuBar.add(menu);

        headerLabel = new JLabel("",JLabel.CENTER );
        statusLabel = new JLabel("",JLabel.CENTER);
        statusLabel.setBackground(Color.red);

        statusLabel.setSize(400,0);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        auctionPanel = new JPanel();
        auctionPanel.setLayout(new BoxLayout(auctionPanel, BoxLayout.Y_AXIS));


        mainPanel.add(auctionPanel);
        mainPanel.add(controlPanel);

        mainFrame.add(headerLabel,BorderLayout.PAGE_START);
        mainFrame.add(mainPanel,BorderLayout.CENTER);
        mainFrame.add(statusLabel,BorderLayout.PAGE_END);

        mainFrame.setJMenuBar(menuBar);

        mainFrame.setVisible(true);
    }

    private void prepareView(){
        headerLabel.setText("Current Auction");

        newAction = new JButton("New Action");
        raiseBid = new JButton("Raise the Bid");

        newAction.setActionCommand("newAuction");
        raiseBid.setActionCommand("raiseBid");

        newAction.addActionListener(new ButtonClickListener());
        raiseBid.addActionListener(new ButtonClickListener());

        //controlPanel.setBackground(Color.red);

        mainFrame.setVisible(true);
        mainFrame.pack();
    }

    public void addAuction(Auction a){
        System.out.println("Add new auction to auctionPanel");
        auctionList.add(a);
        JPanel p  = new JPanel();
        p.setLayout(new GridLayout(4,3,5,5));
        //p.setBackground(Color.cyan);

        //CREATE THE PRICE LABEL
        JLabel priceLabel = new JLabel(" Price : ");
        JLabel auctionPriceLabel = new JLabel("");
        auctionPriceLabel.setText(Integer.toString(a.getPrice()));
        auctionPriceLabel.setLabelFor(priceLabel);
        p.add(priceLabel);
        p.add(auctionPriceLabel);

        //CREATE THE BID FIELD
        JTextField raiseBid = new JTextField("",JLabel.TRAILING);
        JLabel raiseLabel = new JLabel("Bid : ");
        raiseLabel.setLabelFor(raiseBid);
        p.add(raiseLabel);
        p.add(raiseBid);
        p.setBorder(BorderFactory.createTitledBorder(a.getName()));


        JButton raiseBidbutton = new JButton("Raise the bid");
                raiseBidbutton.setActionCommand("raiseBid");
        p.add(raiseBidbutton,4);

        auctionListPanel.put(a.getName(),p);

        auctionPanel.add(auctionListPanel.get(a.getName()));
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if( command.equals( "newAuction" ))  {
                statusLabel.setText("New Auction sent.");
            }
            else if( command.equals( "raiseBid" ) )  {
                statusLabel.setText("New bid sent.");
            }
        }
    }

    public static void main(String[] args){
        Auction a = new Auction(10,"Noix de coco x10", "");

        ClientGui c = new ClientGui();

        c.addAuction(a);


    }
}
