package com.bilibili.live.discovery.mvp.view;

import com.bilibili.live.discovery.bean.HotSearchTag;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/11/28.
 */

public interface IDiscoveryView {
    void showProgress();
    void hideProgress();
    void errorCallback(Throwable throwable);
    void loadHotSearchTags(List<HotSearchTag.ListBean> hotSearchTags);
    <T> LifecycleTransformer<T> bindToLifecycle();
}
