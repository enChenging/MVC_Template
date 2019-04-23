package com.release.mvc.http;

/**
 * @author Mr.release
 * @create 2019/3/29
 * @Describe
 */
public interface  ICallBack {


    void onFailure(String throwable);

    void onSuccess(String response);
}
