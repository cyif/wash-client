package com.zju.chen.wash_client.net;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zju.chen.wash_client.model.Room;
import com.zju.chen.wash_client.util.CustomApplication;
import com.zju.chen.wash_client.util.JacksonUtil;
import com.zju.chen.wash_client.util.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 16/7/9.
 */
public class RoomController {

    private String url;
    private String params;
    private String httpUrl;

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    private List<Room> roomList = new ArrayList<Room>();

    public void getRooms(final Handler handler) {
        params = "/user/status/all";
        httpUrl = url + params;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(httpUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");

                            VolleyLog.d("%s", jsonArray.toString());
                            roomList = JacksonUtil.parseJson(jsonArray.toString(), new TypeReference<List<Room>>() {
                            });

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
}
