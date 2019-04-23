package com.release.mvc.http;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Mr.release
 * @create 2019/4/23
 * @Describe
 */
public abstract class HttpCallback<Result> implements ICallBack {

    @Override
    public void onSuccess(String response) {

        Class<?> cls = analysisClazzInfo(this);

        Result result = (Result) JSON.parseObject(response, cls);
        onSuccess(result);
    }

    public abstract void onSuccess(Result result);

    public static Class<?> analysisClazzInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }
}
