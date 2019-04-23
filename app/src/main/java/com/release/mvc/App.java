package com.release.mvc;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.release.mvc.http.HttpProxy;
import com.release.mvc.http.OkHttpHelper;
import com.release.mvc.utils.CrashHandler;
import com.release.mvc.utils.SPUtil;

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
public class App extends MultiDexApplication {
    public static Context mContext;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        init();
    }

    private void init() {
        SPUtil.getInstance(this);
        CrashHandler.getInstance().init(getApplicationContext());

        //可以随意切换网络框架
//        HttpProxy.init(VolleyHelper.getInstance(this));
        HttpProxy.init(OkHttpHelper.getInstance(this));
    }
}
