package com.bilibili.live.region.mvp.model;

import com.bilibili.live.region.bean.RegionHomeItemBean;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/10/15.
 */

public interface IRegionHomeModel {
    void getRegionHomeInfos();
    interface RegionHomeDataCallBackListener{
        void onContentInfoSuccess(List<RegionHomeItemBean> resultBeans);
        void onFailure(Throwable throwable);
        <T> LifecycleTransformer<T> bindToLifecycle();
    }
}
