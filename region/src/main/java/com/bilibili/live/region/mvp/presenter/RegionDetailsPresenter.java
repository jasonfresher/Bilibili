package com.bilibili.live.region.mvp.presenter;

import com.bilibili.live.region.bean.RegionDetailsInfo;
import com.bilibili.live.region.mvp.model.IRegionDetailsModel;
import com.bilibili.live.region.mvp.model.RegionDetailsModelImpl;
import com.bilibili.live.region.mvp.view.IRegionDetailsView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/10/17.
 */

public class RegionDetailsPresenter implements IRegionDetailsPresenter,IRegionDetailsModel.RegionDetailsDataCallBackListener {

    private IRegionDetailsView mView;

    private IRegionDetailsModel mModel;

    public RegionDetailsPresenter(IRegionDetailsView mView) {
        this.mView = mView;
        this.mModel = new RegionDetailsModelImpl(this);
    }

    @Override
    public void getRegionDetailsInfo(int rid) {
        mView.showProgress();
        mModel.getRegionDetailsData(rid);
    }

    @Override
    public void onRecommendInfoSuccess(List<RegionDetailsInfo.DataBean.RecommendBean> recommends) {
        mView.loadRecommendInfo(recommends);
    }

    @Override
    public void onNewXInfoSuccess(List<RegionDetailsInfo.DataBean.NewBean> newXs) {
        mView.hideProgress();
        mView.loadNewXInfo(newXs);
    }

    @Override
    public void onFailure(Throwable throwable) {
        mView.hideProgress();
        mView.errorCallback(throwable);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return mView.bindToLifecycle();
    }
}
