package com.zju.chen.wash_client.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by chen on 16/7/8.
 */
public class WashMachine implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    private int location;
    private int status;
    private Date beginTime;
    private Date endTime;

    public WashMachine(){}
    public WashMachine(int id, int location, int status, Date beginTime, Date endTime) {
        this.id = id;
        this.location = location;
        this.status = status;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

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



}
