package com.bilibili.live.netcasting.mvp.presenter;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by jason on 2018/9/25.
 */

public interface INetcastingPresenter {
    void getLiveAppIndexInfoData();
    <T> LifecycleTransformer<T> bindToLifecycle();
}
