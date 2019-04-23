package com.release.mvc.http;

import java.util.Map;

/**
 * @author Mr.release
 * @create 2019/4/23
 * @Describe
 */
public interface IRequestManager {

    void get(String url, ICallBack callBack);

    void get(String url, Map<String, String> heads, ICallBack callBack);

    void post(String url, ICallBack callBack);

    void post(String url, Map<String, String> heads, Map<String, String> params, ICallBack callBack);
}
