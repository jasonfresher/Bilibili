package com.bilibili.live.recommend.mvp.model;

import com.bilibili.live.base.api.ApiConstants;
import com.bilibili.live.base.helper.RetrofitHelper;
import com.bilibili.live.recommend.api.RecommendApi;
import com.bilibili.live.recommend.bean.RecommendBannerInfo;
import com.bilibili.live.recommend.bean.RecommendInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by jason on 2018/9/14.
 */

public class RecommendModelImpl implements IRecommendModel {

    private RecommendDataCallBackListener mCallBackListener;
    private List<RecommendBannerInfo.DataBean> bannerInfos;
    private RecommendApi recommendApi;

    public RecommendModelImpl(RecommendDataCallBackListener callBackListener){
        this.mCallBackListener = callBackListener;
        recommendApi = RetrofitHelper.createApi(RecommendApi.class, ApiConstants.APP_BASE_URL);
    }

    @Override
    public void getRecommendInfo() {

        Observable<RecommendInfo> recommendedInfo = recommendApi.getRecommendedInfo();
        System.out.println(recommendedInfo.toString());


        recommendApi.getRecommendedBannerInfo()
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<RecommendBannerInfo, ObservableSource<RecommendInfo>>() {
                    @Override
                    public ObservableSource<RecommendInfo> apply(RecommendBannerInfo recommendBannerInfo) throws Exception {
                        int code = recommendBannerInfo.getCode();
                        if(code == 0){
                            bannerInfos = recommendBannerInfo.getData();
                        }
                        return recommendApi.getRecommendedInfo();
                    }
                })
                .compose(mCallBackListener.<RecommendInfo>bindToLifecycle())
                .map(new Function<RecommendInfo, List<RecommendInfo.ResultBean>>() {
                    @Override
                    public List<RecommendInfo.ResultBean> apply(RecommendInfo recommendInfo) throws Exception {
                        List<RecommendInfo.ResultBean> resultBeans = new ArrayList<>();
                        if(recommendInfo.getCode() == 0)
                            resultBeans = recommendInfo.getResult();
                        return resultBeans;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<RecommendInfo.ResultBean>>() {
                    @Override
                    public void accept(List<RecommendInfo.ResultBean> resultBeans) throws Exception {
                        if (mCallBackListener != null)
                            mCallBackListener.onSuccess(bannerInfos, resultBeans);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mCallBackListener.onFailure(throwable);
                    }
                });
    }


//    public interface RecommendDataCallBackListener{
//        void onSuccess(List<RecommendBannerInfo.DataBean> mBaseBanners,List<RecommendInfo.ResultBean> results);
//        void onFailure(Throwable throwable);
//        <T> LifecycleTransformer<T> bindToLifecycle();
//    }
}
