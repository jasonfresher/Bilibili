package com.bilibili.live.details.mvp.presenter;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by jason on 2018/10/31.
 */

public interface IDetailsPresenter {
    void getDetailsData(int aid);
    <T> LifecycleTransformer<T> bindToLifecycle();
}
