package com.bilibili.live.region.mvp.presenter;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by jason on 2018/10/15.
 */

public interface IRegionHomePresenter {

    void getRegionHomeData();
    <T> LifecycleTransformer<T> bindToLifecycle();
}
