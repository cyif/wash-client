package com.zju.chen.wash_client.view.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.model.Account;
import com.zju.chen.wash_client.util.UTF8CodeConvert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    String json = "{\"code\": 1, \"result\": [{\"address\": \"address2\",\"name\":\"haha2\",\"id\":2,\"email\":\"email2\"},"+
            "{\"address\":\"address\",\"name\":\"haha\",\"id\":1,\"email\":\"email\"}]}";

    private int num = 0;
    private String url;
    private String id;
    private String result;
    private String apiKey = "e820886bec31ec0f95e177273434581f";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OnClickListener lis = new OnClickListener() {
            @Override
            public void onClick(View v) {

                getTestJson();
            }
        };

        OnClickListener lis2 = new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendImgRequest();
            }
        };


        Button getButton = (Button)findViewById(R.id.getButton);
        getButton.setOnClickListener(lis);
        Button imgButton = (Button)findViewById(R.id.imgButton);
        imgButton.setOnClickListener(lis2);

    }

    private void getTestJson() {

        url = "http://115.28.61.157:5000/user/status/all";
        final TextView textView = (TextView)findViewById(R.id.textView);
        String httpUrl = url;
        StringRequest req = new StringRequest(Method.GET, httpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            VolleyLog.d("%s", response);
                            VolleyLog.d("%s", json);
                            JSONObject jsonObject = new JSONObject(json);
                            VolleyLog.v("Response:%n %s", jsonObject);
                            textView.setText(jsonObject.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        VolleyLog.e("Error : ", error.toString());
                    }
                });

        Volley.newRequestQueue(this).add(req);
    }


    private void sendImgRequest() {

        final TextView textView = (TextView)findViewById(R.id.textView);
        url = "http://apis.baidu.com/txapi/mvtp/meinv";
        String num = "num=1";
        String httpUrl = url + '?' + num;
        JsonObjectRequest req = new JsonObjectRequest(httpUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        result = response.toString();
                        VolleyLog.v("Response:%n %s", result);
                        //textView.setText(result);
                        try {
                            JSONArray jsonArray = response.getJSONArray("newslist");
                            VolleyLog.v("JsonArray : %s", jsonArray.toString());
                            VolleyLog.v("JsonArray[0] : %s", jsonArray.getJSONObject(0).toString());
                            url = jsonArray.getJSONObject(0).getString("picUrl");
                            VolleyLog.d("IMGURL : %s", url);
                            getImg(url);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        VolleyLog.e("Error : ", error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String ,String> headers = new HashMap<String ,String>();
                headers.put("apikey", apiKey);

                return headers;
            }
        };

        Volley.newRequestQueue(this).add(req);
    }

    private void getImg(String imgUrl) {

        final ImageView imageView = (ImageView)findViewById(R.id.imageView);

        ImageRequest imageRequest = new ImageRequest(imgUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(this).add(imageRequest);
    }
}
