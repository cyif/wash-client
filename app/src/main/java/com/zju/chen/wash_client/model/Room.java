package com.zju.chen.wash_client.model;

import java.io.Serializable;

/**
 * Created by chen on 16/7/8.
 */
public class Room implements Serializable{

    private static final long serialVersionUID = 1L;

    private int room;
    private int num;

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
