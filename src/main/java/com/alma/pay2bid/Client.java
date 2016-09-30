package com.alma.pay2bid;

import com.alma.pay2bid.common.IClient;
import com.alma.pay2bid.common.IServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * @author Thomas Minier
 * @date 27/09/16
 */
public class Client extends UnicastRemoteObject implements IClient {

    class TimerManager extends TimerTask {
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

    public Client(IServer server, String name) throws RemoteException {
        LOGGER.info("");

        this.server = server;
        this.name = name;
    }

    /**
     * @param auction
     * @throws RemoteException
     */
    public void newAuction(Auction auction) throws RemoteException {
        /*if(auction == null) {
            throw new Exception();
        }*/

        LOGGER.info("");

        currentAuction = auction;

        timer = new Timer();
        timer.schedule(new TimerManager(), TIME_TO_RAISE_BID);
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

        LOGGER.info("");

        server.placeAuction(auction);
    }

    /**
     * @param buyer
     * @throws RemoteException
     */
    public void bidSold(IClient buyer) throws RemoteException {
        /*if(currentAuction == null) {
            throw new Exception();
        }*/

        LOGGER.info((buyer == null ? "nobody" : buyer.getName()) + " won " + currentAuction.getName());

        currentAuction = null;

        timer.cancel();
        timer = null;
    }

    /**
     * @param price
     * @throws RemoteException
     */
    public void newPrice(int price) throws RemoteException {
        /*if(currentAuction == null || timer == null) {
            throw new Exception();
        }*/

        LOGGER.info("");

        currentAuction.setPrice(price);

        timer = new Timer();
        timer.schedule(new TimerManager(), TIME_TO_RAISE_BID);
    }

    public String getName() throws RemoteException {
        return name;
    }
}
