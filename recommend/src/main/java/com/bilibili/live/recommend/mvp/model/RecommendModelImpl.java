package com.bilibili.live.recommend.mvp.model;

import com.bilibili.live.recommend.bean.BannerEntity;
import com.bilibili.live.recommend.bean.RecommendBannerInfo;
import com.bilibili.live.recommend.bean.RecommendInfo;

import java.util.List;

import rx.Observable;

/**
 * Created by jason on 2018/9/14.
 */

public class RecommendModelImpl implements IRecommendModel {

    private RecommendDataCallBackListener mCallBackListener;


    public RecommendModelImpl(RecommendDataCallBackListener callBackListener){
        this.mCallBackListener = callBackListener;
    }

    @Override
    public void getRecommendBannerInfo() {

    }


    public interface RecommendDataCallBackListener{
        void onSuccess(List<BannerEntity> mBaseBanners,List<RecommendInfo.ResultBean> results);
        void onFailure(Throwable throwable);
        <T> Observable.Transformer<T, T> bindToLifecycle();
    }
}
