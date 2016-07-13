package com.zju.chen.wash_client.net;

import android.os.Handler;
import android.os.Message;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.zju.chen.wash_client.model.Deal;
import com.zju.chen.wash_client.util.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chen on 16/7/12.
 */
public class PayController {

    private Deal deal;
    private String url;
    private String params;
    private String httpUrl;

    public void postDeal(final Handler handler) {
        params = "/user/user/wash/pay/";

        httpUrl = url + params + deal.getFrom() + "/" + deal.getTo() + "/" + deal.getMoney();
        final Message msg = new Message();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, url, (JSONObject) null,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            msg.what = response.getInt("code");
                            handler.sendMessage(msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        msg.what = 1;
                        handler.sendMessage(msg);
                        error.printStackTrace();
                    }
                });

        RequestManager.getInstance().getRequestQueue().add(jsonObjectRequest);
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
