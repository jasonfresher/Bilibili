package com.bilibili.live.home.application;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bilibili.live.base.application.BaseApplication;
import com.bilibili.live.home.BuildConfig;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.tinker.entry.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;

import skin.support.SkinCompatManager;
import skin.support.app.SkinCardViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

/**
 * <p/>
 * 哔哩哔哩动画App
 */
public class BilibiliApp extends BaseApplication {

    private static final String TAG = "BilibiliApp";

    @Override
    public void onCreate() {
        super.onCreate();
        initTinkerPatch();
        if (BuildConfig.DEBUG)
            LeakCanary.install(this);
        SkinCompatManager.withoutActivity(this)                         // 基础控件换肤初始化
                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
                .setSkinStatusBarColorEnable(true)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(true)                   // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();
    }

    private void initTinkerPatch() {
        // 我们可以从这里获得Tinker加载过程的信息
        if (BuildConfig.TINKER_ENABLE) {
            ApplicationLike tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
            // 初始化TinkerPatch SDK
            TinkerPatch.init(
                    tinkerApplicationLike
//                new TinkerPatch.Builder(tinkerApplicationLike)
//                    .requestLoader(new OkHttp3Loader())
//                    .build()
            )
                    .reflectPatchLibrary()
                    .setPatchRollbackOnScreenOff(true)
                    .setPatchRestartOnSrceenOff(true)
                    .setFetchPatchIntervalByHours(3)
            ;
            // 获取当前的补丁版本
            Log.d(TAG, "Current patch version is " + TinkerPatch.with().getPatchVersion());

            // fetchPatchUpdateAndPollWithInterval 与 fetchPatchUpdate(false)
            // 不同的是，会通过handler的方式去轮询
            //TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
            new FetchPatchHandler().fetchPatchWithInterval(1);
        }
    }

    public class FetchPatchHandler extends Handler {
        public static final long HOUR_INTERVAL = 3600 * 1000;
        private long checkInterval;

        /**
         * 通过handler, 达到按照时间间隔轮训的效果
         *
         * @param hour
         */
        public void fetchPatchWithInterval(int hour) {
            //设置TinkerPatch的时间间隔
            TinkerPatch.with().setFetchPatchIntervalByHours(hour);
            checkInterval = hour * HOUR_INTERVAL;
            //立刻尝试去访问,检查是否有更新
            sendEmptyMessage(0);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //这里使用false即可
            TinkerPatch.with().fetchPatchUpdate(false);
            //每隔一段时间都去访问后台, 增加10分钟的buffer时间
            sendEmptyMessageDelayed(0, checkInterval + 10 * 60 * 1000);
        }
    }
}
