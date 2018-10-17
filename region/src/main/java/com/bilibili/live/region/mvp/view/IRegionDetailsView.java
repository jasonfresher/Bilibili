package com.bilibili.live.region.mvp.view;

import com.bilibili.live.region.bean.RegionDetailsInfo;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/10/17.
 */

public interface IRegionDetailsView {
    void showProgress();
    void hideProgress();
    void errorCallback(Throwable throwable);
    void loadRecommendInfo(List<RegionDetailsInfo.DataBean.RecommendBean> recommends);
    void loadNewXInfo(List<RegionDetailsInfo.DataBean.NewBean> newXs);
    <T> LifecycleTransformer<T> bindToLifecycle();
}
