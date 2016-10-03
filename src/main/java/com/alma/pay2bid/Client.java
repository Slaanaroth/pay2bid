package com.alma.pay2bid;

import com.alma.pay2bid.common.IClient;
import com.alma.pay2bid.common.IServer;
import com.alma.pay2bid.gui.observers.BidSoldObserver;
import com.alma.pay2bid.gui.observers.NewAuctionObserver;
import com.alma.pay2bid.gui.observers.NewPriceObserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * @author Thomas Minier
 * @date 27/09/16
 */
public class Client extends UnicastRemoteObject implements IClient {

    private class TimerManager extends TimerTask {
        @Override
        public void run() {
            try {
                server.timeElapsed(Client.this);
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
    private IServer server;
    //TODO: use ExecutorService instead of Timer ?
    private Timer timer;
    private Auction currentAuction;
    private String name;
    private ClientState state;

    // collections of observers used to connect the client to the GUI
    private List<BidSoldObserver> bidSoldObservers = new ArrayList<BidSoldObserver>();
    private List<NewAuctionObserver> newAuctionObservers = new ArrayList<NewAuctionObserver>();
    private List<NewPriceObserver> newPriceObservers = new ArrayList<NewPriceObserver>();

    public Client(IServer server, String name) throws RemoteException {
        this.server = server;
        this.name = name;
        state = ClientState.WAITING;
    }

    /**
     * @param auction
     * @throws RemoteException
     */
    @Override
    public void newAuction(Auction auction) throws RemoteException {
        /*if(auction == null) {
            throw new Exception();
        }*/

        LOGGER.info("New auction received from the server");

        currentAuction = auction;

        timer = new Timer();
        timer.schedule(new TimerManager(), TIME_TO_RAISE_BID);

        state = ClientState.WAITING;

        // notify the observers of the new auction
        for (NewAuctionObserver observer : newAuctionObservers) {
            observer.execute(auction);
        }
    }

    /**
     * @param auction
     * @throws RemoteException
     */
    @Deprecated
    public void submit(Auction auction) throws RemoteException {
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
        for (BidSoldObserver observer : bidSoldObservers) {
            observer.execute(buyer);
        }
    }

    /**
     * @param price
     * @throws RemoteException
     */
    @Override
    public void newPrice(int price) throws RemoteException {
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
        timer.schedule(new TimerManager(), TIME_TO_RAISE_BID);

        state = ClientState.WAITING;

        // notify the observers of the new price for the current auction
        for (NewPriceObserver observer : newPriceObservers) {
            observer.execute(price);
        }
    }

    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public String getName() throws RemoteException {
        return name;
    }

    /**
     *
     * @param observer
     * @throws RemoteException
     */
    @Override
    public void registerBidSoldObserver(BidSoldObserver observer) throws RemoteException {
        bidSoldObservers.add(observer);
    }

    /**
     *
     * @param observer
     * @throws RemoteException
     */
    @Override
    public void registerNewAuctionObserver(NewAuctionObserver observer) throws RemoteException {
        newAuctionObservers.add(observer);
    }

    /**
     *
     * @param observer
     * @throws RemoteException
     */
    @Override
    public void registerNewPriceObserver(NewPriceObserver observer) throws RemoteException {
        newPriceObservers.add(observer);
    }
}
