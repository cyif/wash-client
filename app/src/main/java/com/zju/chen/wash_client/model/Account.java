package com.zju.chen.wash_client.model;

import java.io.Serializable;

/**
 * Created by chen on 16/7/8.
 */
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;


    private String accountName;
    private long money;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

}
