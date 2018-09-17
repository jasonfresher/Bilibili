package com.bilibili.live.recommend.mvp.presenter;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by jason on 2018/9/17.
 */

public interface IRecommendPresenter {
    void getHomeRecommendData();
    <T> LifecycleTransformer<T> bindToLifecycle();
}
