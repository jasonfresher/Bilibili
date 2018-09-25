package com.bilibili.live.netcasting.mvp.view;

import com.bilibili.live.netcasting.bean.LiveAppIndexInfo;
import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by jason on 2018/9/25.
 */

public interface INetcastingView {
    void showProgress();
    void hideProgress();
    void errorCallback(Throwable throwable);
    void loadNetcastingInfo(LiveAppIndexInfo.DataBean liveDataBean);
    <T> LifecycleTransformer<T> bindToLifecycle();
}
