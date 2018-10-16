package com.bilibili.live.region.mvp.model;

import com.bilibili.live.region.bean.RegionRecommendInfo;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/10/16.
 */

public interface IRegionRecommendModel {
    void getRegionRecommendsData(int rid);

    interface RegionRecommendDataCallBackListener{
        void onBannerInfoSuccess(List<RegionRecommendInfo.DataBean.BannerBean.TopBean> bannerTops);
        void onRecommendInfoSuccess(List<RegionRecommendInfo.DataBean.RecommendBean> recommends);
        void onNewXInfoSuccess(List<RegionRecommendInfo.DataBean.NewBean> newXs);
        void ondynamicInfoSuccess(List<RegionRecommendInfo.DataBean.DynamicBean> dynamics);
        void onFailure(Throwable throwable);
        <T> LifecycleTransformer<T> bindToLifecycle();
    }
}
