package com.bilibili.live.base.application;

import android.app.Application;
import android.content.Context;

import com.bilibili.live.base.BuildConfig;
import com.billy.cc.core.component.CC;

public class BaseApplication extends Application {

    public static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        if (BuildConfig.DEBUG) {
            CC.enableVerboseLog(true);
            CC.enableDebug(true);
            CC.enableRemoteCC(true);
        }
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }
}
