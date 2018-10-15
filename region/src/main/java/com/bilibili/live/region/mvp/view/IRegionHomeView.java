package com.bilibili.live.region.mvp.view;

import com.bilibili.live.region.bean.RegionHomeItemBean;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/10/15.
 */

public interface IRegionHomeView {
    void showProgress();
    void hideProgress();
    void errorCallback(Throwable throwable);
    void loadRegionHomeInfo(List<RegionHomeItemBean> regionHomeItemBeans);
    <T> LifecycleTransformer<T> bindToLifecycle();
}
