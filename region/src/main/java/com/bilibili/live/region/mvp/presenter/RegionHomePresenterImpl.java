package com.bilibili.live.region.mvp.presenter;

import com.bilibili.live.region.bean.RegionHomeItemBean;
import com.bilibili.live.region.mvp.model.IRegionHomeModel;
import com.bilibili.live.region.mvp.model.RegionHomeModelImpl;
import com.bilibili.live.region.mvp.view.IRegionHomeView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/10/15.
 */

public class RegionHomePresenterImpl implements IRegionHomePresenter,IRegionHomeModel.RegionHomeDataCallBackListener {

    private IRegionHomeView mRegionHomeView;

    private IRegionHomeModel mRegionHomeModel;

    public RegionHomePresenterImpl(IRegionHomeView regionHomeView) {
        this.mRegionHomeView = regionHomeView;
        mRegionHomeModel = new RegionHomeModelImpl(this);
    }

    @Override
    public void getRegionHomeData() {
        mRegionHomeModel.getRegionHomeInfos();
    }

    @Override
    public void onContentInfoSuccess(List<RegionHomeItemBean> resultBeans) {
        mRegionHomeView.loadRegionHomeInfo(resultBeans);
    }

    @Override
    public void onFailure(Throwable throwable) {
        mRegionHomeView.errorCallback(throwable);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return mRegionHomeView.bindToLifecycle();
    }
}
