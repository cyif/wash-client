package com.zju.chen.wash_client.util;

import android.app.Application;
import android.os.Debug;
import android.util.Log;

import com.zju.chen.wash_client.model.WashMachine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 16/7/8.
 */
public class CustomApplication extends Application{

    private static final String URL = "http://115.28.61.157:5000";

    private static final String URL2 = "http://115.28.61.157:8080";

    private static final String TESTTEL = "18868130765";

    private String url;
    private String url2;
    private String tel;
    private String accountName;
    private List<WashMachine> myWashMachineList;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("!!!!!!!", "--------------------Application--------------------");
        RequestManager.createInstance(getApplicationContext());
        setUrl(URL);
        setUrl2(URL2);
        setTel(TESTTEL);
        myWashMachineList = new ArrayList<WashMachine>();
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
