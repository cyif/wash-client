package com.zju.chen.wash_client.util;

import android.app.Application;
import android.os.Debug;
import android.util.Log;

/**
 * Created by chen on 16/7/8.
 */
public class CustomApplication extends Application{

    private static final String URL = "http://115.28.61.157:5000";

    private String url;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("!!!!!!!", "--------------------Application--------------------");
        RequestManager.createInstance(getApplicationContext());
        setUrl(URL);
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
