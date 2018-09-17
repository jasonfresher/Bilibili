package com.bilibili.live.recommend.mvp.presenter;

import com.bilibili.live.recommend.bean.RecommendBannerInfo;
import com.bilibili.live.recommend.bean.RecommendInfo;
import com.bilibili.live.recommend.mvp.model.IRecommendModel;
import com.bilibili.live.recommend.mvp.model.RecommendModelImpl;
import com.bilibili.live.recommend.mvp.view.IRecommendView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/9/17.
 */

public class RecommendPresenterImpl implements IRecommendPresenter,IRecommendModel.RecommendDataCallBackListener{

    private IRecommendView mRecommendView;
    private IRecommendModel mRecommendModel;

    public RecommendPresenterImpl(IRecommendView recommendView){
        mRecommendView = recommendView;
        mRecommendModel = new RecommendModelImpl(this);
    }


    @Override
    public void getHomeRecommendData() {
        mRecommendModel.getRecommendInfo();
    }

    @Override
    public void onSuccess(List<RecommendBannerInfo.DataBean> mBaseBanners, List<RecommendInfo.ResultBean> results) {
        if(mRecommendView != null)
            mRecommendView.loadRecommendInfo(mBaseBanners,results);
    }

    @Override
    public void onFailure(Throwable throwable) {
        mRecommendView.errorCallback(throwable);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return mRecommendView.bindToLifecycle();
    }
}
