package com.bilibili.live.recommend.mvp.view;

import com.bilibili.live.recommend.bean.RecommendBannerInfo;
import com.bilibili.live.recommend.bean.RecommendInfo;
import com.bilibili.live.recommend.entity.RecommendMultiItem;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/9/17.
 */

public interface IRecommendView {
    void showProgress();
    void hideProgress();
    void errorCallback(Throwable throwable);
    void loadRecommendBannerInfo(RecommendMultiItem recommendMultiItem);
    void loadRecommendContentInfo(List<RecommendInfo.ResultBean> results);
    <T> LifecycleTransformer<T> bindToLifecycle();
}
