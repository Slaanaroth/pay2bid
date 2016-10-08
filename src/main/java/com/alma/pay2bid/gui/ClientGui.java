package com.alma.pay2bid.gui;

import com.alma.pay2bid.bean.AuctionBean;
import com.alma.pay2bid.client.Client;
import com.alma.pay2bid.client.observer.INewAuctionObserver;
import com.alma.pay2bid.client.observer.INewPriceObserver;
import com.alma.pay2bid.gui.listeners.SubmitAuctionListener;
import com.alma.pay2bid.gui.listeners.RaiseBidButtonListener;
import com.alma.pay2bid.gui.listeners.AuctionInputListener;
import com.alma.pay2bid.server.IServer;
import com.alma.pay2bid.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.logging.Logger;

import static java.lang.System.exit;

/**
 * Created by Folkvir(Grall Arnaud)) on 28/09/16.
 */
public class ClientGui {

    private static final Logger LOGGER = Logger.getLogger(ClientGui.class.getCanonicalName());
    private Client client;
    private HashMap<String, AuctionGui> auctionList;

    /**
     * Main frame & elements
     */
    private JFrame mainFrame;
    private JLabel statusLabel;
    private JPanel mainPanel;
    private JPanel auctionPanel;

    /**
     * Frame to create new auction
     */
    private JFrame auctionFrame;

    /**
     * Constructor
     * @param client
     */
    public ClientGui(Client client) throws RemoteException, InterruptedException {
        this.client = client;
        auctionList = new HashMap<String,AuctionGui>();
        // TODO : maybe find a more elegant way to do this (@Thomas)
        client.getServer().register(this.client);

        client.addNewAuctionObserver(new INewAuctionObserver() {
            @Override
            public void updateNewAuction(AuctionBean auction) {
                LOGGER.info("A new auction needs to be added to the GUI");
                addAuctionPanel(auction);
            }
        });
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

        // TODO : what to do with this chunk of code ? (@Thomas -> @Arnaud)
        //controlPanel = new JPanel();
        //controlPanel.setLayout(new FlowLayout());

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
    private void show(){
        mainFrame.setVisible(true);
    }

    /**
     * Add a new panel to the main panel which display a new Auction
     * @param auctionBean
     */
    private void addAuctionPanel(AuctionBean auctionBean){
        if(auctionList.get(auctionBean.getName()) == null) {
            LOGGER.info("Add new auction to auctionPanel");

            AuctionGui auction = new AuctionGui(auctionBean);

            JButton raiseBidButton = new JButton("Raise the bid");
            raiseBidButton.setActionCommand("raiseBid");
            auction.getAuctionPanel().add(raiseBidButton, 4);

            auctionList.put(auctionBean.getName(), auction);
            auctionPanel.add(auctionList.get(auctionBean.getName()).getAuctionPanel());
            raiseBidButton.addActionListener(new RaiseBidButtonListener(this, auctionList.get(auctionBean.getName()), client, client.getServer()));
            //Now add the observer to receive all price updates
            client.addNewPriceObserver(new INewPriceObserver() {
                @Override
                public void updateNewPrice(Integer price, AuctionBean auction) {
                    setAuctionPrice(auction);
                }
            });

            mainPanel.revalidate();
            mainPanel.repaint();
        }
    }

    /**
     * Set an new price for a given AuctionBean
     */
    private void setAuctionPrice(AuctionBean auctionBean){
        LOGGER.info("auctionPrice set !");
        //UPDATE AUCTION IN OUR LIST
        auctionList.get(auctionBean.getName()).setProperties(auctionBean);

        //RELOAD THE MAIN PANEL
        auctionList.get(auctionBean.getName()).getAuctionPanel().revalidate();
        auctionList.get(auctionBean.getName()).getAuctionPanel().repaint();

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Create the menu to add a new Auction
     */
    public void newAuctionView(){
        auctionFrame = new JFrame("Add a new auction");
        auctionFrame.setLayout(new BorderLayout());
        auctionFrame.setSize(new Dimension(500, 200));
        auctionFrame.setResizable(false);

        AuctionBean auctionBean = new AuctionBean(0, "", "");
        AuctionGui auctionGui = new AuctionGui(auctionBean);

        auctionFrame.add(auctionGui.getNewAuctionPanel(), BorderLayout.CENTER);

        JButton auctionSend = new JButton("SEND NEW AUCTION");
        auctionSend.setActionCommand("newAuction");
        auctionSend.addActionListener(new SubmitAuctionListener(auctionGui, client, auctionFrame, statusLabel));
        auctionGui.getNewAuctionPanel().add(auctionSend);

        auctionFrame.add(auctionGui.getStatusAuction(), BorderLayout.PAGE_END);

        auctionFrame.setVisible(true);
    }

    /**
     * Send a new auction to the server
     * @deprecated Prefer the use of {@link Client#submit(AuctionBean)} to this method, as it creates a new intermediate layer of code not needed
     * @param auction
     * @throws RemoteException
     */
    @Deprecated()
    public void sendAuction(AuctionGui auction) throws RemoteException {
        auctionFrame.setVisible(false);
        auctionFrame = null;

        LOGGER.info("New auction send to the server : [...]");
        LOGGER.info("Name : "+auction.getName().getText());
        LOGGER.info("Price : "+auction.price.getText());
        LOGGER.info("Description : "+auction.getDescription().getText());

        // send the new auction through the client
        AuctionBean a = new AuctionBean(Integer.parseInt(auction.price.getText()), auction.getName().getText(), auction.getDescription().getText());
        client.submit(a);
        statusLabel.setText("New auction sent...");
    }

    /**
     *
     * @return
     */
    public JFrame getAuctionFrame() {
        return auctionFrame;
    }

    /**
     *
     * @return
     */
    public JLabel getStatusLabel() {
        return statusLabel;
    }

    /**
     * Main loop which run the client
     * @param args
     */
    public static void main(String[] args) throws RemoteException, InterruptedException, NotBoundException {
        //IServer server = new Server();
        IServer server = (IServer) LocateRegistry.getRegistry("localhost", 1099).lookup("com.alma.pay2bid.server.Server"); //TODO: parameterize host/port
        Client client = new Client(server, "Arnaud");
        Client client2 = new Client(server, "Thomas");

        ClientGui c = new ClientGui(client);
        ClientGui c2 = new ClientGui(client2);

        c.show();
        c2.show();
    }
}
