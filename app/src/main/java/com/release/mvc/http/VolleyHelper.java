package com.release.mvc.http;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
public class VolleyHelper implements IRequestManager {

    private static final String TAG = VolleyHelper.class.getSimpleName();
    RequestQueue requestQueue;
    private static VolleyHelper _instance;

    private VolleyHelper(Context context) {
        requestQueue = Volley.newRequestQueue(context);
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

    @Override
    public void get(String url, ICallBack callBack) {

        if (TextUtils.isEmpty(url)) {
            return;
        }

        StringRequest request = new StringRequest(Request.Method.GET, url,
                s -> callBack.onSuccess(s),
                volleyError -> callBack.onFailure(volleyError.toString())) {
        };
        requestQueue.add(request);
    }

    @Override
    public void get(String url, Map<String, String> heads, ICallBack callBack) {

        if (TextUtils.isEmpty(url)) {
            return;
        }

        StringRequest request = new StringRequest(Request.Method.GET, url,
                s -> callBack.onSuccess(s),
                volleyError -> callBack.onFailure(volleyError.toString())) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return heads;
            }
        };
        requestQueue.add(request);
    }

    @Override
    public void post(String url, ICallBack callBack) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        StringRequest request = new StringRequest(Request.Method.POST, url,
                s -> callBack.onSuccess(s),
                volleyError -> callBack.onFailure(volleyError.toString())) {
        };
        requestQueue.add(request);
    }

    @Override
    public void post(String url, Map<String, String> heads, Map<String, String> params, ICallBack callBack) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        StringRequest request = new StringRequest(Request.Method.POST, url,
                s -> callBack.onSuccess(s),
                volleyError -> callBack.onFailure(volleyError.toString())) {
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
