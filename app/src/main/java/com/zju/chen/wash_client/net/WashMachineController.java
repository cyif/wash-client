package com.zju.chen.wash_client.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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

        Log.d("SHOW!!!!!!!!!", httpUrl);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(httpUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");

                            Message msg = new Message();
                            msg.what = response.getInt("code");
                            VolleyLog.d("@@@@@@@@@@@@%s", jsonArray.toString());
                            /*DateFormat format=new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
                            format.setTimeZone(TimeZone.getTimeZone("GMT"));*/

                            DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            //format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                            //VolleyLog.d("DATE!!!!!     %s", format.format("Mon, 03 Jun 2013 07:01:29 GMT").toString());

                            VolleyLog.d("DATE!!!!!     %s", jsonArray.toString());

                            machineList = JacksonUtil.parseJson(jsonArray.toString(), new TypeReference<List<WashMachine>>() {
                            }, format);

                            handler.sendMessage(msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Message msg = new Message();
                        msg.what = -1;
                        handler.sendMessage(msg);
                        Log.d("SHOW!!!!!!!", error.getMessage());
                        error.getStackTrace();
                    }
                });

        RequestManager.getInstance().getRequestQueue().add(jsonObjectRequest);

    }

    public void getWashMachineByAccount(final Handler handler, String account) {

        params = "/user/status/one/account/";
        httpUrl = url + params + account;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(httpUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");

                            VolleyLog.d("@@@@@@@@@@@@%s", jsonArray.toString());
                            /*DateFormat format=new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
                            format.setTimeZone(TimeZone.getTimeZone("GMT"));*/

                            DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            //format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                            //VolleyLog.d("DATE!!!!!     %s", format.format("Mon, 03 Jun 2013 07:01:29 GMT").toString());

                            VolleyLog.d("DATE!!!!!     %s", jsonArray.toString());

                            machineList = JacksonUtil.parseJson(jsonArray.toString(), new TypeReference<List<WashMachine>>() {
                            }, format);
                            Log.d("MachineList!!!!!!", machineList.toArray().toString());

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

    public void startWashMachine(final Handler handler, int id,
                                 final String account, final int type, final double money) {

        params = "/user/begin/" + id;
        httpUrl = url + params;

        VolleyLog.d("URL!!!!!!!!!!!   %s", httpUrl);
        VolleyLog.d("PARA!!!!!!!!! %s", account + type + money);
        StringRequest stringRequest = new StringRequest(Method.POST, httpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //VolleyLog.d("StartWashMachine-----------------");

                            /*Message msg = new Message();
                            msg.what = response.getInt("code");
                            handler.sendMessage(msg);*/
                            VolleyLog.d("StartWashMachine!!!!!       %s", response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                        error.printStackTrace();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("account_name", account);
                        map.put("wash_money", money + "");
                        map.put("wash_type", type + "");
                        return map;
                    }
        };

        RequestManager.getInstance().getRequestQueue().add(stringRequest);
    }
}
