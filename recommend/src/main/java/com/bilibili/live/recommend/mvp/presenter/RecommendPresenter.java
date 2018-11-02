package com.bilibili.live.recommend.mvp.presenter;

import com.bilibili.live.base.mvp.BasePresenter;
import com.bilibili.live.recommend.bean.RecommendInfo;
import com.bilibili.live.recommend.entity.RecommendEntity;
import com.bilibili.live.recommend.mvp.model.IRecommendModel;
import com.bilibili.live.recommend.mvp.model.RecommendModel;
import com.bilibili.live.recommend.mvp.view.IRecommendView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/9/17.
 */

public class RecommendPresenter extends BasePresenter<IRecommendView> implements IRecommendModel.RecommendDataCallBackListener{

    private IRecommendModel mRecommendModel;

    public RecommendPresenter(){
        mRecommendModel = new RecommendModel(this);
    }

    public void getRecommendBannerData() {
        mRecommendModel.getRecommendBannerInfos();
    }

    public void getRecommendContentData() {
        mRecommendModel.getRecommendContentInfos();
    }

    @Override
    public void onBannerInfoSuccess(RecommendEntity recommendBannerInfo) {
        if(getView() != null)
            getView().loadRecommendBannerInfo(recommendBannerInfo);
    }

    @Override
    public void onContentInfoSuccess(List<RecommendInfo.ResultBean> resultBeans) {
        if(getView() != null)
            getView().loadRecommendContentInfo(resultBeans);
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
