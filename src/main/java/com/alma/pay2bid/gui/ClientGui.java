package com.alma.pay2bid.gui;

import com.alma.pay2bid.bean.AuctionBean;
import com.alma.pay2bid.client.Client;
import com.alma.pay2bid.client.IClient;
import com.alma.pay2bid.client.observer.IBidSoldObserver;
import com.alma.pay2bid.client.observer.INewAuctionObserver;
import com.alma.pay2bid.client.observer.INewPriceObserver;
import com.alma.pay2bid.client.observer.ITimerObserver;
import com.alma.pay2bid.gui.listeners.RaiseBidButtonListener;
import com.alma.pay2bid.gui.listeners.AuctionInputListener;
import com.alma.pay2bid.server.IServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

import static java.lang.System.exit;

/**
 * Created by Folkvir(Grall Arnaud)) on 28/09/16.
 */
public class ClientGui {

    private static final Logger LOGGER = Logger.getLogger(ClientGui.class.getCanonicalName());
    private Client client;
    private IServer server;
    private HashMap<UUID, AuctionView> auctionList;

    /**
     * Main frame & elements
     */
    private JFrame mainFrame;
    private JLabel statusLabel;
    private JPanel mainPanel;
    private JPanel auctionPanel;

    /**
     * Constructor
     * @param client
     */
    public ClientGui(Client client, IServer server) throws RemoteException, InterruptedException {
        this.client = client;
        this.server = server;
        auctionList = new HashMap<UUID, AuctionView>();
        server.register(this.client);

        client.addNewAuctionObserver(new INewAuctionObserver() {
            @Override
            public void updateNewAuction(AuctionBean auction) {
                LOGGER.info("A new auction needs to be added to the GUI");
                addAuctionPanel(auction);
            }
        });


        // paint the GUI
        createGui();
    }


    /**
     * Initialize the GUI & populate it with the base elements
     */
    private void createGui() {
        // Create the Main JFrame
        mainFrame = new JFrame("Pay2Bid - Auction");
        Dimension dimension = new Dimension(500, 500);
        mainFrame.setSize(500, 500);
        mainFrame.setMaximumSize(dimension);
        mainFrame.setLayout(new BorderLayout());

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent){
                try {
                    server.disconnect(client);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                exit(0);
            }
        });

        // Create the Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem newAuction = new JMenuItem("New Auction");
        newAuction.setActionCommand("newAuction");
        newAuction.addActionListener(new AuctionInputListener(this));
        menu.add(newAuction);

        menuBar.add(menu);
        mainFrame.setJMenuBar(menuBar);

        // Create the Frame Header
        JLabel headerLabel = new JLabel("", JLabel.CENTER);
        headerLabel.setText("Current Auction");

        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setBackground(Color.red);
        statusLabel.setSize(400,0);

        mainFrame.add(headerLabel, BorderLayout.PAGE_START);

        // Create the Main panel which will contains the GUI's elements
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        auctionPanel = new JPanel();
        auctionPanel.setLayout(new BoxLayout(auctionPanel, BoxLayout.Y_AXIS));
        mainPanel.add(auctionPanel);
        mainPanel.setMaximumSize(dimension);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        mainFrame.add(scrollPane, BorderLayout.CENTER);

        mainFrame.add(statusLabel, BorderLayout.PAGE_END);
    }

    /**
     * Show the client GUI
     */
    public void show(){
        mainFrame.setVisible(true);
    }

    /**
     * Add a new panel to the main panel which display a new Auction
     * @param auctionBean
     */
    private void addAuctionPanel(AuctionBean auctionBean){
        if(!auctionList.containsKey(auctionBean.getUUID())) {
            LOGGER.info("Add new auction to auctionPanel");

            final AuctionView auction = new AuctionView(auctionBean);

            JButton raiseBidButton = new JButton("Raise the bid");
            raiseBidButton.setActionCommand("raiseBid");
            raiseBidButton.addActionListener(new RaiseBidButtonListener(client, client.getServer(), auction, statusLabel));
            auction.setRaiseButton(raiseBidButton);

            //Now add the observer to receive all price updates
            client.addNewPriceObserver(new INewPriceObserver() {
                @Override
                public void updateNewPrice(UUID auctionID, Integer price) {
                    setAuctionPrice(auctionID, price);
                    auction.enable();
                }
            });

            // Add a observer to receive the notification when the bid is sold
            client.addBidSoldObserver(new IBidSoldObserver() {
                @Override
                public void updateBidSold(IClient client) {
                    try {
                        auction.setWinner(client.getName());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });

            client.addTimerObserver(new ITimerObserver() {
                @Override
                public void updateTimer(String time) {
                    auction.setAuctionTimer(time);
                }
            });

            auctionPanel.add(auction.getAuctionPanel());
            auctionList.put(auctionBean.getUUID(), auction);

            mainPanel.revalidate();
            mainPanel.repaint();

        } else {
            LOGGER.warning("Trying to add a duplicated auction to the list - Auction : " + auctionBean.toString());
        }
    }

    /**
     * Set an new price for a given AuctionBean
     */
    private void setAuctionPrice(UUID auctionID, int newPrice){
        LOGGER.info("auctionPrice set !");
        AuctionView auction = auctionList.get(auctionID);
        //UPDATE AUCTION IN OUR LIST
        auction.setPrice(newPrice);

        //RELOAD THE MAIN PANEL
        auction.getAuctionPanel().revalidate();
        auction.getAuctionPanel().repaint();

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Create the menu to add a new Auction
     */
    public void newAuctionView() {
        AuctionInput input = new AuctionInput(client);
        input.showFrame();
    }

    /**
     * Main loop which run the client
     * @param args
     */
    public static void main(String[] args) throws RemoteException, InterruptedException, NotBoundException {
        //TODO: parameterize host/port
        IServer server = (IServer) LocateRegistry.getRegistry("localhost", 1099).lookup("com.alma.pay2bid.server.Server");
        Client client = new Client(server, "Arnaud");
        Client client2 = new Client(server, "Thomas");

        ClientGui c = new ClientGui(client, server);
        ClientGui c2 = new ClientGui(client2, server);

        c.show();
        c2.show();
    }
}
