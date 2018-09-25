package com.bilibili.live.recommend.mvp.model;

import com.bilibili.live.recommend.bean.RecommendInfo;
import com.bilibili.live.recommend.entity.RecommendEntity;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/9/14.
 */

public interface IRecommendModel {
    void getRecommendBannerInfos();

    void getRecommendContentInfos();

    interface RecommendDataCallBackListener{
        void onBannerInfoSuccess(RecommendEntity recommendBannerEntity);
        void onContentInfoSuccess(List<RecommendInfo.ResultBean> resultBeans);
        void onFailure(Throwable throwable);
        <T> LifecycleTransformer<T> bindToLifecycle();
    }
}
