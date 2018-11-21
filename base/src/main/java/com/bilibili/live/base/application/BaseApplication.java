package com.bilibili.live.base.application;

import android.app.Application;

import com.billy.cc.core.component.CC;


public class BaseApplication extends Application {

    public static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        CC.enableVerboseLog(true);
        CC.enableDebug(true);
        CC.enableRemoteCC(true);
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }

}
