package com.release.mvc.http;

import okhttp3.Call;

/**
 * @author Mr.release
 * @create 2019/3/29
 * @Describe
 */
public interface  ICallBack {


    void onFailure(Call call, Exception e);

    void onSuccess(String response);
}
