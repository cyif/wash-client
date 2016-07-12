package com.zju.chen.wash_client.model;

/**
 * Created by ab on 2016/7/12.
 */
public class type {
    private String name;
    private int price;

    public void setName(String str){
        name=str;
    }
    public void setPrice(int pr){
        price=pr;
    }
    public String getName(){return name;}
    public int getPrice(){return price;}
}
