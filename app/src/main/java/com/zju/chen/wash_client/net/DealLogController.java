package com.zju.chen.wash_client.net;

import android.os.Handler;
import android.os.Message;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zju.chen.wash_client.model.DealLog;
import com.zju.chen.wash_client.model.Room;
import com.zju.chen.wash_client.util.JacksonUtil;
import com.zju.chen.wash_client.util.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 16/7/10.
 */
public class DealLogController {

    private String url;
    private String params;
    private String httpUrl;

    public List<DealLog> dealLogList = new ArrayList<DealLog>();

    public void getDealLog(final Handler handler, String tel) {
        params = "/user/store/record/tel/" + tel;
        httpUrl = url + params;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(httpUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");

                            VolleyLog.d("JSONARRAY ------- %s", jsonArray.toString());
                            dealLogList = JacksonUtil.parseJson(jsonArray.toString(), new TypeReference<List<DealLog>>() {
                            }, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

                            Message msg = new Message();
                            msg.what = 1;
                            handler.sendMessage(msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getStackTrace();
                    }
                });

        RequestManager.getInstance().getRequestQueue().add(jsonObjectRequest);
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

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public List<DealLog> getDealLogList() {
        return dealLogList;
    }

    public void setDealLogList(List<DealLog> dealLogList) {
        this.dealLogList = dealLogList;
    }
}
