package com.release.mvc.http;


import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.release.mvc.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OkHttpHelper implements IRequestManager {

    public static final String TAG = "OkHttpHelper";

    private static OkHttpHelper mOkHttpHelper;

    private static OkHttpClient client;
    private Handler mHandler;

    private OkHttpHelper(Context context) {
        File sdcache = context.getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;//设置缓存大小
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));//设置缓存的路径

        builder.sslSocketFactory(createSSLSocketFactory());
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        client = builder.build();
        mHandler = new Handler();

    }

    public static OkHttpHelper getInstance(Context context) {

        if (mOkHttpHelper == null) {
            synchronized (OkHttpHelper.class) {
                if (mOkHttpHelper == null) {
                    mOkHttpHelper = new OkHttpHelper(context);
                }
            }
        }
        return mOkHttpHelper;
    }

    @Override
    public void get(String url, ICallBack callBack) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailCallback(callBack, e.toString());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                LogUtils.i(TAG, "onResponse: " + Thread.currentThread());
                boolean isSuccessful = response.isSuccessful();
                sendSuccessCallback(callBack, isSuccessful, response);
            }
        });
    }

    @Override
    public void get(String url, Map<String, String> heads, ICallBack callBack) {

        if (TextUtils.isEmpty(url)) {
            return;
        }

        Headers headers = appendHeaders(heads);
        Request request = new Request.Builder()
                .headers(headers)
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailCallback(callBack, e.toString());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                boolean isSuccessful = response.isSuccessful();
                sendSuccessCallback(callBack, isSuccessful, response);
            }
        });
    }

    @Override
    public void post(String url, ICallBack callBack) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailCallback(callBack, e.toString());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                boolean isSuccessful = response.isSuccessful();
                sendSuccessCallback(callBack, isSuccessful, response);
            }
        });
    }

    @Override
    public void post(String url, Map<String, String> heads, Map<String, String> params, ICallBack callBack) {

        if (TextUtils.isEmpty(url)) {
            return;
        }

        Headers headers = appendHeaders(heads);
        RequestBody requestBody = appendBody(params);

        Request request = new Request.Builder()
                .headers(headers)
                .post(requestBody)
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailCallback(callBack, e.toString());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                boolean isSuccessful = response.isSuccessful();
                sendSuccessCallback(callBack, isSuccessful, response);
            }
        });
    }

    private Headers appendHeaders(Map<String, String> headers) {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty())
            return headerBuilder.build();

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        return headerBuilder.build();
    }

    private RequestBody appendBody(Map<String, String> params) {
        FormBody.Builder body = new FormBody.Builder();
        if (params == null || params.isEmpty()) {
            return body.build();
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            body.add(entry.getKey(), entry.getValue().toString());
        }
        return body.build();
    }


    private void sendSuccessCallback(final ICallBack callback, final boolean isSuccess, final Response response) {

        try {
            String responseString = response.body().string();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (isSuccess == true) {
                        callback.onSuccess(responseString);
                    } else
                        callback.onFailure(response.message().toString());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendFailCallback(final ICallBack callback, String throwable) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(throwable);
            }
        });
    }

    public SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }

    /**
     * 用于信任所有证书
     */
    class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
