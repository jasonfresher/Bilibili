package com.bilibili.live.region.mvp.presenter;

import com.bilibili.live.base.mvp.BasePresenter;
import com.bilibili.live.region.bean.RegionRecommendInfo;
import com.bilibili.live.region.mvp.model.IRegionRecommendModel;
import com.bilibili.live.region.mvp.model.RegionRecommendModel;
import com.bilibili.live.region.mvp.view.IRegionRecommendView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/10/16.
 */

public class RegionRecommendPresenter extends BasePresenter<IRegionRecommendView> implements IRegionRecommendModel.RegionRecommendDataCallBackListener {


    private IRegionRecommendModel model;

    public RegionRecommendPresenter() {
        model = new RegionRecommendModel(this);

    }

    public void getRegionRecommendsInfo(int rid) {
        model.getRegionRecommendsData(rid);
    }

    @Override
    public void onBannerInfoSuccess(List<RegionRecommendInfo.DataBean.BannerBean.TopBean> bannerTops) {
        if(getView() != null)
            getView().loadBannerInfo(bannerTops);
    }

    @Override
    public void onRecommendInfoSuccess(List<RegionRecommendInfo.DataBean.RecommendBean> recommends) {
        if(getView() != null)
            getView().loadRecommendInfo(recommends);
    }

    @Override
    public void onNewXInfoSuccess(List<RegionRecommendInfo.DataBean.NewBean> newXs) {
        if(getView() != null)
            getView().loadNewXInfo(newXs);
    }

    @Override
    public void ondynamicInfoSuccess(List<RegionRecommendInfo.DataBean.DynamicBean> dynamics) {
        if(getView() != null)
            getView().loadDynamicInfo(dynamics);
    }

    @Override
    public void onFailure(Throwable throwable) {
        if(getView() != null)
            getView().errorCallback(throwable);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        if(getView() != null)
            return getView().bindToLifecycle();
        return null;
    }
}
