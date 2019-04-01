package com.release.mvc.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
public class VolleySingleton {
    private static VolleySingleton volleySingleton;
    private RequestQueue mRequestQueue;
    private Context mContext;
    public VolleySingleton(Context context) {
        this.mContext = context;
        mRequestQueue = getRequestQueue();
    }
    public static synchronized VolleySingleton getVolleySingleton(Context context){
        if(volleySingleton == null){
            volleySingleton = new VolleySingleton(context);
        }
        return volleySingleton;
    }
    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }

}
