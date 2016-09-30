package com.alma.pay2bid;

import java.io.Serializable;

/**
 * Created by E122371M on 27/09/16.
 */
public class Auction implements Serializable {
    private int price;
    private String name;
    private String description;

    public Auction(int price, String name, String description) {
        this.price = price;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
