package com.bilibili.live.bangumi.mvp.model;

import com.bilibili.live.bangumi.bean.RegionRecommendInfo;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/9/27.
 */

public interface BangumiModel {
    void getBangumiInfos();

    interface BangumiDataCallBackListener{
        void onBangumiRecommendBannerSuccess(List<RegionRecommendInfo.DataBean.BannerBean.TopBean> bangumiBanners);
        void onBangumiRecommendSuccess(List<RegionRecommendInfo.DataBean.RecommendBean> bangumiRecommends);
        void onBangumiNewsSuccess(List<RegionRecommendInfo.DataBean.NewBean> bangumiNews);
        void onBangumiDynamicSuccess(List<RegionRecommendInfo.DataBean.DynamicBean> bangumiDynamics);
        void onFailure(Throwable throwable);
        <T> LifecycleTransformer<T> bindToLifecycle();
    }
}
