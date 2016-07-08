package com.zju.chen.wash_client.util;

/**
 * Created by chen on 16/7/8.
 */
public class UTF8CodeConvert {

    public static String convert(String data) {
        if (data.startsWith("\ufeff")) {
            return data.substring(1);
        } else {
            return data;
        }
    }
}
