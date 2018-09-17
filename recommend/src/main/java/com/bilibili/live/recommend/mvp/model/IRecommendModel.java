package com.bilibili.live.recommend.mvp.model;

import com.bilibili.live.recommend.bean.RecommendBannerInfo;
import com.bilibili.live.recommend.bean.RecommendInfo;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/9/14.
 */

public interface IRecommendModel {
    public void getRecommendInfo();

    public interface RecommendDataCallBackListener{
        void onSuccess(List<RecommendBannerInfo.DataBean> mBaseBanners, List<RecommendInfo.ResultBean> results);
        void onFailure(Throwable throwable);
        <T> LifecycleTransformer<T> bindToLifecycle();
    }
}
