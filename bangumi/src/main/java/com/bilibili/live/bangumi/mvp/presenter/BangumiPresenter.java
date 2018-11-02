package com.bilibili.live.bangumi.mvp.presenter;

import com.bilibili.live.bangumi.bean.RegionRecommendInfo;
import com.bilibili.live.bangumi.mvp.model.IBangumiModel;
import com.bilibili.live.bangumi.mvp.model.BangumiModel;
import com.bilibili.live.bangumi.mvp.view.IBangumiView;
import com.bilibili.live.base.mvp.BasePresenter;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/9/27.
 */

public class BangumiPresenter extends BasePresenter<IBangumiView> implements IBangumiModel.BangumiDataCallBackListener {


    private IBangumiModel mBangumiModel;

    public BangumiPresenter(){
        mBangumiModel = new BangumiModel(this);
    }

    public void getBangumiInfosData() {
        mBangumiModel.getBangumiInfos();
    }

    @Override
    public void onBangumiRecommendBannerSuccess(List<RegionRecommendInfo.DataBean.BannerBean.TopBean> bangumiBanners) {
        if(bangumiBanners != null && bangumiBanners.size() > 0 && getView() != null)
            getView().onBangumiRecommendBannerSuccess(bangumiBanners);
    }

    @Override
    public void onBangumiRecommendSuccess(List<RegionRecommendInfo.DataBean.RecommendBean> bangumiRecommends) {
        if(bangumiRecommends != null && bangumiRecommends.size() > 0 && getView() != null)
            getView().onBangumiRecommendSuccess(bangumiRecommends);
    }

    @Override
    public void onBangumiNewsSuccess(List<RegionRecommendInfo.DataBean.NewBean> bangumiNews) {
        if(bangumiNews != null && bangumiNews.size() > 0 && getView() != null)
            getView().onBangumiNewsSuccess(bangumiNews);
    }

    @Override
    public void onBangumiDynamicSuccess(List<RegionRecommendInfo.DataBean.DynamicBean> bangumiDynamics) {
        if(bangumiDynamics != null && bangumiDynamics.size() > 0 && getView() != null)
            getView().onBangumiDynamicSuccess(bangumiDynamics);
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
