package com.zju.chen.wash_client.model;

import java.io.Serializable;

/**
 * Created by chen on 16/7/8.
 */
public class Deal implements Serializable{

    private static final long serialVersionUID = 1L;


    private int id;
    private String from;
    private String to;
    private double money;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

}
