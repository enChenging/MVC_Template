package com.release.mvc.http;

import java.util.Map;

/**
 * @author Mr.release
 * @create 2019/4/23
 * @Describe
 */
public class HttpProxy implements IRequestManager {

    private static IRequestManager iRequestManager;
    private static HttpProxy _instance;

    private HttpProxy() {
    }

    public static HttpProxy obtain() {
        synchronized (HttpProxy.class) {
            if (_instance == null) {
                _instance = new HttpProxy();
            }
        }
        return _instance;
    }

    public static void init(IRequestManager manager) {
        iRequestManager = manager;

    }

    @Override
    public void get(String url, ICallBack callBack) {
        iRequestManager.get(url, callBack);
    }

    @Override
    public void get(String url, Map<String, String> heads, ICallBack callBack) {
        iRequestManager.get(url, heads, callBack);
    }

    @Override
    public void post(String url, ICallBack callBack) {
        iRequestManager.post(url, callBack);
    }

    @Override
    public void post(String url, Map<String, String> heads, Map<String, String> params, ICallBack callBack) {
        iRequestManager.post(url, heads, params, callBack);
    }
}
