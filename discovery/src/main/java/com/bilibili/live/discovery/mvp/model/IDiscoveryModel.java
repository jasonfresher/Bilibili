package com.bilibili.live.discovery.mvp.model;

import com.bilibili.live.discovery.bean.HotSearchTag;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/11/28.
 */

public interface IDiscoveryModel {
    void getHotSearchTags();
    interface DiscoveryDataCallBackListener{
        void onHotSearchTagSuccess(List<HotSearchTag.ListBean> hotSearchTags);
        void onFailure(Throwable throwable);
        <T> LifecycleTransformer<T> bindToLifecycle();
    }
}
