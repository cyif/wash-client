package com.zju.chen.wash_client.util;

import android.app.Application;

/**
 * Created by chen on 16/7/8.
 */
public class CustomApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        RequestManager.createInstance(getApplicationContext());
    }
}
