package com.zju.chen.wash_client.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chen on 16/7/12.
 */
public class Code implements Serializable{

    private static final long serialVersionUID = 1L;


    private String account;
    private int washId;
    private List<Type> types;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getWashId() {
        return washId;
    }

    public void setWashId(int washId) {
        this.washId = washId;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }
}
