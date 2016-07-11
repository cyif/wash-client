package com.zju.chen.wash_client.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zju.chen.wash_client.model.Room;
import com.zju.chen.wash_client.model.WashMachine;
import com.zju.chen.wash_client.util.JacksonUtil;
import com.zju.chen.wash_client.util.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by chen on 16/7/9.
 */
public class WashMachineController {

    private List<WashMachine> machineList = new ArrayList<WashMachine>();
    private String url;
    private String httpUrl;
    private String params;

    public List<WashMachine> getMachineList() {
        return machineList;
    }

    public void setMachineList(List<WashMachine> machineList) {
        this.machineList = machineList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void getWashMachineByRoom(final Handler handler, int room) {

        params = "/user/status/one?room=";
        httpUrl = url + params + room;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(httpUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");

                            VolleyLog.d("@@@@@@@@@@@@%s", jsonArray.toString());
                            /*DateFormat format=new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
                            format.setTimeZone(TimeZone.getTimeZone("GMT"));*/

                            DateFormat format=new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
                            format.setTimeZone(TimeZone.getTimeZone("GMT"));
                            try {
                                VolleyLog.d("DATE!!!!!     %s", format.parse("Mon, 03 Jun 2013 07:01:29 GMT").toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            machineList = JacksonUtil.parseJson(jsonArray.toString(), new TypeReference<List<WashMachine>>() {
                            }, format);

                            machineList.add(0, new WashMachine(-1, 0, -1, null, null));
                            Log.d("MachineList.size()", machineList.size() + "");
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
}
