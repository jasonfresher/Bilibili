package com.bilibili.live.bangumi.mvp.presenter;

import com.bilibili.live.bangumi.bean.RegionRecommendInfo;
import com.bilibili.live.bangumi.mvp.model.BangumiModel;
import com.bilibili.live.bangumi.mvp.model.BangumiModelImpl;
import com.bilibili.live.bangumi.mvp.view.BangumiView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/9/27.
 */

public class BangumiPresenterImpl implements BangumiPresenter,BangumiModel.BangumiDataCallBackListener {

    private BangumiView mBangumiView;

    private BangumiModel mBangumiModel;

    public BangumiPresenterImpl(BangumiView bangumiView){
        this.mBangumiView = bangumiView;
        mBangumiModel = new BangumiModelImpl(this);
    }

    @Override
    public void getBangumiInfosData() {
        mBangumiModel.getBangumiInfos();
    }

    @Override
    public void onBangumiRecommendBannerSuccess(List<RegionRecommendInfo.DataBean.BannerBean.TopBean> bangumiBanners) {
        if(bangumiBanners != null && bangumiBanners.size() > 0 && mBangumiView != null)
            mBangumiView.onBangumiRecommendBannerSuccess(bangumiBanners);
    }

    @Override
    public void onBangumiRecommendSuccess(List<RegionRecommendInfo.DataBean.RecommendBean> bangumiRecommends) {
        if(bangumiRecommends != null && bangumiRecommends.size() > 0 && mBangumiView != null)
            mBangumiView.onBangumiRecommendSuccess(bangumiRecommends);
    }

    @Override
    public void onBangumiNewsSuccess(List<RegionRecommendInfo.DataBean.NewBean> bangumiNews) {
        if(bangumiNews != null && bangumiNews.size() > 0 && mBangumiView != null)
            mBangumiView.onBangumiNewsSuccess(bangumiNews);
    }

    @Override
    public void onBangumiDynamicSuccess(List<RegionRecommendInfo.DataBean.DynamicBean> bangumiDynamics) {
        if(bangumiDynamics != null && bangumiDynamics.size() > 0 && mBangumiView != null)
            mBangumiView.onBangumiDynamicSuccess(bangumiDynamics);
    }

    @Override
    public void onFailure(Throwable throwable) {
        if(mBangumiView != null)
            mBangumiView.errorCallback(throwable);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return mBangumiView.bindToLifecycle();
    }
}
