package com.zju.chen.wash_client.model;

import java.io.Serializable;

/**
 * Created by chen on 16/7/8.
 */
public class Deal implements Serializable{

    private static final long serialVersionUID = 1L;


    private int id;
    private int from;
    private String to;
    private double money;
    public void setFrom(int from){this.from=from;}
    public void setTo(String to){this.to=to;}
    public void setMoney(double money){this.money=money;}
    public String getTo(){return to;}
    public double getMoney(){return money;}
}
