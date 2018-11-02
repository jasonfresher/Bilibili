package com.bilibili.live.region.mvp.presenter;

import com.bilibili.live.base.mvp.BasePresenter;
import com.bilibili.live.region.bean.RegionHomeItemBean;
import com.bilibili.live.region.mvp.model.IRegionHomeModel;
import com.bilibili.live.region.mvp.model.RegionHomeModel;
import com.bilibili.live.region.mvp.view.IRegionHomeView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/10/15.
 */

public class RegionHomePresenter extends BasePresenter<IRegionHomeView> implements IRegionHomeModel.RegionHomeDataCallBackListener {


    private IRegionHomeModel mRegionHomeModel;

    public RegionHomePresenter() {
        mRegionHomeModel = new RegionHomeModel(this);
    }

    public void getRegionHomeData() {
        mRegionHomeModel.getRegionHomeInfos();
    }

    @Override
    public void onContentInfoSuccess(List<RegionHomeItemBean> resultBeans) {
        if(getView() != null)
            getView().loadRegionHomeInfo(resultBeans);
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
