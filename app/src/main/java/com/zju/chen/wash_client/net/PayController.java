package com.zju.chen.wash_client.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
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
    private int status;

    public void postDeal(final Handler handler) {
        params = "/user/user/wash/pay/";

        httpUrl = url + params + deal.getFrom() + "/" + deal.getTo() + "/" + (int) deal.getMoney();

        VolleyLog.d("URL!!!!!!!!!    %s", httpUrl);
        final Message msg = new Message();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.GET, httpUrl, (JSONObject) null,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            msg.what = response.getInt("code");
                            VolleyLog.d("CODE!!!!!!   %d", msg.what);
                            Bundle data = new Bundle();
                            data.putSerializable("Deal", deal);
                            data.putString("msg", response.getString("msg"));
                            data.putInt("status", status);
                            msg.setData(data);
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
                        //msg.setData(new Bundle().putString("msg", "支付错误"));
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
