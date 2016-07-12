package com.zju.chen.wash_client.model;

import java.io.Serializable;

/**
 * Created by chen on 16/7/12.
 */
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}