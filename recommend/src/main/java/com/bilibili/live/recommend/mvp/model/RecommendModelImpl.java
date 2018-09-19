package com.bilibili.live.recommend.mvp.model;

import com.bilibili.live.base.api.ApiConstants;
import com.bilibili.live.base.helper.RetrofitHelper;
import com.bilibili.live.recommend.api.RecommendApi;
import com.bilibili.live.recommend.bean.RecommendBannerInfo;
import com.bilibili.live.recommend.bean.RecommendInfo;
import com.bilibili.live.recommend.entity.RecommendMultiItem;

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
    private List<RecommendBannerInfo.DataBean> bannerInfos = new ArrayList<>();
    private RecommendApi recommendApi;
    private RecommendMultiItem<List<RecommendBannerInfo.DataBean>> recommendMultiItem;

    public RecommendModelImpl(RecommendDataCallBackListener callBackListener){
        this.mCallBackListener = callBackListener;
        recommendApi = RetrofitHelper.createApi(RecommendApi.class, ApiConstants.APP_BASE_URL);
    }

    @Override
    public void getRecommendBannerInfos() {
        recommendApi.getRecommendedBannerInfo()
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<RecommendBannerInfo, ObservableSource<RecommendInfo>>() {
                    @Override
                    public ObservableSource<RecommendInfo> apply(RecommendBannerInfo recommendBannerInfo) throws Exception {
                        int code = recommendBannerInfo.getCode();
                        if(code == 0){
                            bannerInfos = recommendBannerInfo.getData();
                        }
                        recommendMultiItem =
                                new RecommendMultiItem<>(RecommendMultiItem.VIEW_TYPE_BANNER,4,bannerInfos);
                        return recommendApi.getRecommendedInfo();
                    }
                })
                .compose(mCallBackListener.<RecommendInfo>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RecommendInfo>() {
                    @Override
                    public void accept(RecommendInfo recommendInfo) throws Exception {
                        if (mCallBackListener != null) {
                            mCallBackListener.onBannerInfoSuccess(recommendMultiItem);
                            if(recommendInfo != null && recommendInfo.getCode() == 0) {
                                mCallBackListener.onContentInfoSuccess(recommendInfo.getResult());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mCallBackListener != null)
                            mCallBackListener.onFailure(throwable);
                    }
                });
    }

    @Override
    public void getRecommendContentInfos() {
        recommendApi.getRecommendedInfo()
                .subscribeOn(Schedulers.io())
                .map(new Function<RecommendInfo, List<RecommendInfo.ResultBean>>() {
                    @Override
                    public List<RecommendInfo.ResultBean> apply(RecommendInfo recommendInfo) throws Exception {
                        List<RecommendInfo.ResultBean> resultBeans = new ArrayList<>();
                        if(recommendInfo.getCode() == 0)
                            resultBeans = recommendInfo.getResult();
                        return resultBeans;
                    }
                })
                .compose(mCallBackListener.<List<RecommendInfo.ResultBean>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<RecommendInfo.ResultBean>>() {
                    @Override
                    public void accept(List<RecommendInfo.ResultBean> resultBeans) throws Exception {
                        if (mCallBackListener != null)
                            mCallBackListener.onContentInfoSuccess(resultBeans);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mCallBackListener != null)
                            mCallBackListener.onFailure(throwable);
                    }
                });
    }
}
