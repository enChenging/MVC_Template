package com.release.mvc.base;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.release.mvc.utils.CrashHandler;
import com.release.mvc.utils.SPUtil;

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
public class BaseApplication extends MultiDexApplication {
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
        SPUtil.getInstance(this);
        CrashHandler.getInstance().init(getApplicationContext());
    }
}
