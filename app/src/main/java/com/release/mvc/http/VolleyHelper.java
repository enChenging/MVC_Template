package com.release.mvc.http;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
public class VolleyHelper {

    RequestQueue requestQueue;
    private static VolleyHelper _instance;
    private Map<String, String> params;
    private Map<String, String> heads;

    private VolleyHelper(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        this.params = new HashMap<>();
        this.heads = new HashMap<>();
    }

    public static VolleyHelper getInstance(Context context) {
        if (_instance == null) {
            synchronized (VolleyHelper.class) {
                if (_instance == null) {
                    _instance = new VolleyHelper(context);
                }
            }
        }
        return _instance;
    }

    public void addParam(String key, String value) {
        this.params.put(new String(key), value);
    }


    public void addHeads(String key, String value) {
        this.heads.put(new String(key), value);
    }


    public void get(String url, ICallBack callBack) {

        if (TextUtils.isEmpty(url)) {
            return;
        }

        StringRequest request = new StringRequest(Request.Method.GET, url,
                s -> callBack.onSuccess(s),
                volleyError -> callBack.onFailure(null, volleyError)) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return heads;
            }
        };

        requestQueue.add(request);
    }


    public void post(String url, ICallBack callBack) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        StringRequest request = new StringRequest(Request.Method.POST, url,
                s -> callBack.onSuccess(s),
                volleyError -> callBack.onFailure(null, volleyError)) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return heads;
            }
        };


        requestQueue.add(request);

    }


}
