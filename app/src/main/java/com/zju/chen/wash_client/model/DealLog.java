package com.zju.chen.wash_client.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by chen on 16/7/8.
 */

@JsonAutoDetect
public class DealLog implements Serializable{

    private static final long serialVersionUID = 1L;

    @JsonProperty("recordId")
    private int recordId;
    @JsonProperty("washId")
    private int washId;
    @JsonProperty("money")
    private long money;
    @JsonProperty("dealTime")
    private Date dealTime;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getWashId() {
        return washId;
    }

    public void setWashId(int washId) {
        this.washId = washId;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }
}
