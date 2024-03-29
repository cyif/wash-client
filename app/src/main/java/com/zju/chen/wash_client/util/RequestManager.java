package com.zju.chen.wash_client.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by chen on 16/7/8.
 */
public class RequestManager {

    private static RequestManager ourInstance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;


    public static RequestManager createInstance(Context context) {
        if (context != null) {
            if (ourInstance == null) {
                ourInstance = new RequestManager(context);
            } else {
                throw new IllegalArgumentException("Context must be set");
            }
        }
        return ourInstance;
    }

    public static RequestManager getInstance() {
        return ourInstance;
    }

    private RequestManager(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(
                requestQueue,
                new ImageLoader.ImageCache() {
                    private LruCache<String, Bitmap> cache
                            = new LruCache<>(20);
                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                }
        );
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }


    public ImageLoader getImageLoader() {
        return imageLoader;
    }

}
