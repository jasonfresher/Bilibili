package com.bilibili.live.region.mvp.presenter;

import com.bilibili.live.region.bean.RegionRecommendInfo;
import com.bilibili.live.region.mvp.model.IRegionRecommendModel;
import com.bilibili.live.region.mvp.model.RegionRecommendModelImpl;
import com.bilibili.live.region.mvp.view.IRegionRecommendView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/10/16.
 */

public class RegionRecommendPresenter implements IRegionRecommendPresenter,IRegionRecommendModel.RegionRecommendDataCallBackListener {

    private IRegionRecommendView mView;

    private IRegionRecommendModel model;

    public RegionRecommendPresenter(IRegionRecommendView view) {
        this.mView = view;
        model = new RegionRecommendModelImpl(this);

    }

    @Override
    public void getRegionRecommendsInfo(int rid) {
        model.getRegionRecommendsData(rid);
    }

    @Override
    public void onBannerInfoSuccess(List<RegionRecommendInfo.DataBean.BannerBean.TopBean> bannerTops) {
        mView.loadBannerInfo(bannerTops);
    }

    @Override
    public void onRecommendInfoSuccess(List<RegionRecommendInfo.DataBean.RecommendBean> recommends) {
        mView.loadRecommendInfo(recommends);
    }

    @Override
    public void onNewXInfoSuccess(List<RegionRecommendInfo.DataBean.NewBean> newXs) {
        mView.loadNewXInfo(newXs);
    }

    @Override
    public void ondynamicInfoSuccess(List<RegionRecommendInfo.DataBean.DynamicBean> dynamics) {
        mView.loadDynamicInfo(dynamics);
    }

    @Override
    public void onFailure(Throwable throwable) {
        mView.errorCallback(throwable);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return mView.bindToLifecycle();
    }
}
