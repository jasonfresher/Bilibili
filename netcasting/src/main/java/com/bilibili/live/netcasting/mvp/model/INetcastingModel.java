package com.bilibili.live.netcasting.mvp.model;

import com.bilibili.live.netcasting.bean.LiveAppIndexInfo;
import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by jason on 2018/9/25.
 */

public interface INetcastingModel {
    void getLiveAppIndexInfo();
    interface NetcastingDataCallBackListener{
        void onSuccess(LiveAppIndexInfo.DataBean liveAppIndexInfoDataBean);
        void onFailure(Throwable throwable);
        <T> LifecycleTransformer<T> bindToLifecycle();
    }
}
