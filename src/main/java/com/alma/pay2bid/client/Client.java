package com.alma.pay2bid.client;

import com.alma.pay2bid.bean.AuctionBean;
import com.alma.pay2bid.bean.ClientBean;
import com.alma.pay2bid.client.observable.IBidSoldObservable;
import com.alma.pay2bid.client.observable.INewAuctionObservable;
import com.alma.pay2bid.client.observable.INewPriceObservable;
import com.alma.pay2bid.server.IServer;
import com.alma.pay2bid.client.observer.IBidSoldObserver;
import com.alma.pay2bid.client.observer.INewAuctionObserver;
import com.alma.pay2bid.client.observer.INewPriceObserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author Thomas Minier
 * @date 27/09/16
 */
public class Client extends UnicastRemoteObject implements IClient, IBidSoldObservable, INewAuctionObservable, INewPriceObservable {

    private class TimerManager extends TimerTask {
        public String timeString;
        private long time = TIME_TO_RAISE_BID;
        private Timer timer;

        public TimerManager(String timeMessage){
            this.timeString = timeMessage;
        }

        @Override
        public void run() {
            try {
                time -=TIME_TO_REFRESH;
                timeString = Long.toString(time);
                if(time == 0) {
                    server.timeElapsed(Client.this);
                }else{

                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static final Logger LOGGER = Logger.getLogger(Client.class.getCanonicalName());
    //TODO: move all constants/configs in Config class
    private static final long TIME_TO_RAISE_BID = 30000;
    private static final long TIME_TO_REFRESH = 1000;

    private ClientBean identity;
    private IServer server;
    //TODO: use ExecutorService instead of Timer ?
    private transient Timer timer;
    private AuctionBean currentAuction;
    private String name;
    private String timeElapsed;
    private ClientState state;

    // collections of observers used to connect the client to the GUI
    private transient Collection<IBidSoldObserver> bidSoldObservers = new ArrayList<IBidSoldObserver>();
    private transient Collection<INewAuctionObserver> newAuctionObservers = new ArrayList<INewAuctionObserver>();
    private transient Collection<INewPriceObserver> newPriceObservers = new ArrayList<INewPriceObserver>();

    /**
     * To get a server reference:
     * <pre>
     *     IServer server = (IServer) LocateRegistry.getRegistry(host, port).lookup("Server");
     * </pre>
     * @param server
     * @param name
     * @throws RemoteException
     */
    public Client(IServer server, String name) throws RemoteException {
        super();

        identity = new ClientBean(UUID.randomUUID(), name, "default password", name);
        this.server = server;
        this.name = name;
        state = ClientState.WAITING;
    }

    /**
     * @param auction
     * @throws RemoteException
     */
    @Override
    public void newAuction(AuctionBean auction) throws RemoteException {
        /*if(auction == null) {
            throw new Exception();
        }*/

        LOGGER.info("New auction received from the server");

        currentAuction = auction;

        timer = new Timer();
        timer.schedule(new TimerManager(timeElapsed),0, TIME_TO_REFRESH);

        state = ClientState.WAITING;

        // notify the observers of the new auction
        for (INewAuctionObserver observer : newAuctionObservers) {
            observer.updateNewAuction(auction);
        }
    }

    /**
     * @param auction
     * @throws RemoteException
     */
    @Override
    public void submit(AuctionBean auction) throws RemoteException {
        /*if (auction == null) {
            throw new Exception();
        }*/

        LOGGER.info("New auction submitted to the server");

        server.placeAuction(auction);
    }

    /**
     * @param buyer
     * @throws RemoteException
     */
    @Override
    public void bidSold(IClient buyer) throws RemoteException {
        /*if(currentAuction == null) {
            throw new Exception();
        }*/

        LOGGER.info((buyer == null ? "nobody" : buyer.getName()) + " won " + currentAuction.getName());

        currentAuction = null;

        timer.cancel();
        timer = null;

        state = ClientState.ENDING;

        // notify the observers of the new bid
        for (IBidSoldObserver observer : bidSoldObservers) {
            observer.updateBidSold(buyer);
        }
    }

    /**
     * @param auctionID
     * @param price
     * @throws RemoteException
     */
    @Override
    public void newPrice(UUID auctionID, int price) throws RemoteException {
        /*if(currentAuction == null) {
            throw new Exception();
        }*/

        LOGGER.info("New price received for the current auction");

        currentAuction.setPrice(price);

        if(timer != null) {
            timer.cancel();
            timer = null;
        }

        timer = new Timer();
        timer.schedule(new TimerManager(timeElapsed),0, TIME_TO_REFRESH);

        state = ClientState.WAITING;

        // notify the observers of the new price for the current auction
        for (INewPriceObserver observer : newPriceObservers) {
            observer.updateNewPrice(auctionID, price);
        }
    }

    public IServer getServer(){ return server;}

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    @Override
    public ClientState getState() throws RemoteException {
        return state;
    }

    @Override
    public void setState(ClientState state) {
        this.state = state;
    }

    @Override
    public boolean addNewPriceObserver(INewPriceObserver observer) {
        return newPriceObservers.add(observer);
    }

    @Override
    public boolean removeNewPriceObserver(INewPriceObserver observer) {
        return newPriceObservers.remove(observer);
    }

    @Override
    public boolean addBidSoldObserver(IBidSoldObserver observer) {
        return bidSoldObservers.add(observer);
    }

    @Override
    public boolean removeBidSoldObserver(IBidSoldObserver observer) {
        return bidSoldObservers.remove(observer);
    }

    @Override
    public boolean addNewAuctionObserver(INewAuctionObserver observer) {
        return newAuctionObservers.add(observer);
    }

    @Override
    public boolean removeNewAuctionObserver(INewAuctionObserver observer) {
        return newAuctionObservers.remove(observer);
    }
}
