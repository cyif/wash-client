package com.zju.chen.wash_client.model;

import java.util.Date;

/**
 * Created by chen on 16/7/8.
 */
public class WashMachine {

    private int id;
    private int location;
    private int status;
    private Date beginTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }


}