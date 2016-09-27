package com.alma.pay2bid;

/**
 * Created by E122371M on 27/09/16.
 */
public class Auction {
    private int price;
    private String name;

    public Auction(int price, String name) {
        this.price = price;
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
