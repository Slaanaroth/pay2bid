package com.alma.pay2bid.bean;

import java.util.UUID;

/**
 *
 */
public class AuctionBean implements IBean {
    private UUID uuid;
    private int price;
    private String name;
    private String description;

    public AuctionBean(int price, String name, String description) {
        this.price = price;
        this.name = name;
        this.description = description;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
