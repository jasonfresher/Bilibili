package com.bilibili.live.region.mvp.view;

import com.bilibili.live.region.bean.RegionRecommendInfo;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/10/16.
 */

public interface IRegionRecommendView {
    void showProgress();
    void hideProgress();
    void errorCallback(Throwable throwable);
    void loadBannerInfo(List<RegionRecommendInfo.DataBean.BannerBean.TopBean> bannerTops);
    void loadRecommendInfo(List<RegionRecommendInfo.DataBean.RecommendBean> recommends);
    void loadNewXInfo(List<RegionRecommendInfo.DataBean.NewBean> newXs);
    void loadDynamicInfo(List<RegionRecommendInfo.DataBean.DynamicBean> dynamics);
    <T> LifecycleTransformer<T> bindToLifecycle();
}
