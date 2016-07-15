package com.zju.chen.wash_client.net;

import android.os.Handler;
import android.os.Message;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.zju.chen.wash_client.util.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chen on 16/7/14.
 */
public class LoginController {

    private String account;
    private String verifyCode;
    private String url;
    private String params;

    public void sendVerifyCode(final Handler handler) {
        String httpUrl = url + params + account;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(httpUrl,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Message message = new Message();
                            message.what = response.getInt("code");
                            verifyCode = response.getString("vericode");
                            handler.sendMessage(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Message message = new Message();
                        message.what = -2;
                        handler.sendMessage(message);
                        error.printStackTrace();
                    }
                });

        RequestManager.getInstance().getRequestQueue().add(jsonObjectRequest);
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getVerifyCode() {
        return this.verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
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
