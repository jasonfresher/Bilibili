package com.bilibili.live.region.mvp.presenter;

import com.bilibili.live.base.mvp.BasePresenter;
import com.bilibili.live.region.bean.RegionDetailsInfo;
import com.bilibili.live.region.mvp.model.IRegionDetailsModel;
import com.bilibili.live.region.mvp.model.RegionDetailsModel;
import com.bilibili.live.region.mvp.view.IRegionDetailsView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/10/17.
 */

public class RegionDetailsPresenter extends BasePresenter<IRegionDetailsView> implements IRegionDetailsModel.RegionDetailsDataCallBackListener {


    private IRegionDetailsModel mModel;

    public RegionDetailsPresenter() {
        this.mModel = new RegionDetailsModel(this);
    }

    public void getRegionDetailsInfo(int rid) {
        if(getView() != null) {
            getView().showProgress();
            mModel.getRegionDetailsData(rid);
        }
    }

    @Override
    public void onRecommendInfoSuccess(List<RegionDetailsInfo.DataBean.RecommendBean> recommends) {
        if(getView() != null)
            getView().loadRecommendInfo(recommends);
    }

    @Override
    public void onNewXInfoSuccess(List<RegionDetailsInfo.DataBean.NewBean> newXs) {
        if(getView() != null) {
            getView().hideProgress();
            getView().loadNewXInfo(newXs);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        if(getView() != null) {
            getView().hideProgress();
            getView().errorCallback(throwable);
        }
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        if(getView() != null)
            return getView().bindToLifecycle();
        return null;
    }
}
