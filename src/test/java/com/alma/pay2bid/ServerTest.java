package com.alma.pay2bid;

import com.alma.pay2bid.bean.AuctionBean;
import com.alma.pay2bid.client.Client;
import com.alma.pay2bid.client.IClient;
import com.alma.pay2bid.server.IServer;
import com.alma.pay2bid.server.Server;

import java.rmi.RemoteException;

/**
 * @author Thomas Minier
 * @date 28/09/16
 */
public class ServerTest {

    private IServer server;


    @org.junit.Before
    public void setUp() throws Exception {
        server = new Server();
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void sample_test() throws Exception {
        final IClient client = new Client(server, "client0");
        final AuctionBean auction = new AuctionBean(10, "Blank auction", "");

        class concurrentTask extends Thread {
            public void run() {
                try {
                    server.register(client);
                    client.submit(auction);
                    Thread.sleep(5000L);
                    server.raiseBid(client, 20);
                    server.timeElapsed(client);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        new concurrentTask().start();
        Thread.sleep(2000L);
        server.register(client);
    }

}