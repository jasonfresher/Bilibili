package com.bilibili.live.bangumi.mvp.model;

import android.content.Context;
import android.content.res.AssetManager;

import com.bilibili.live.bangumi.api.BangumiApi;
import com.bilibili.live.bangumi.bean.RegionRecommendInfo;
import com.bilibili.live.bangumi.bean.RegionTypesInfo;
import com.bilibili.live.base.api.ApiConstants;
import com.bilibili.live.base.application.BilibiliApp;
import com.bilibili.live.base.helper.RetrofitHelper;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jason on 2018/9/27.
 */

public class BangumiModelImpl implements BangumiModel {

    private BangumiDataCallBackListener mCallBackListener;

    private BangumiApi bangumiApi;

    private List<RegionRecommendInfo.DataBean.BannerBean.TopBean> banners = new ArrayList<>();

    private List<RegionRecommendInfo.DataBean.RecommendBean> recommends = new ArrayList<>();

    private List<RegionRecommendInfo.DataBean.NewBean> news = new ArrayList<>();

    private List<RegionRecommendInfo.DataBean.DynamicBean> dynamics = new ArrayList<>();

    private int mRid;

    private Context mContext;

    public BangumiModelImpl(BangumiDataCallBackListener callBackListener){
        this.mCallBackListener = callBackListener;
        bangumiApi = RetrofitHelper.createApi(BangumiApi.class, ApiConstants.APP_BASE_URL);
    }

    @Override
    public void getBangumiInfos() {
        Observable.just(readAssetsJson())
                .map(new Function<String, RegionTypesInfo>() {
                    @Override
                    public RegionTypesInfo apply(String s) throws Exception {
                        return new Gson().fromJson(s, RegionTypesInfo.class);
                    }
                }).flatMap(new Function<RegionTypesInfo, ObservableSource<RegionRecommendInfo>>() {

            @Override
            public ObservableSource<RegionRecommendInfo> apply(RegionTypesInfo regionTypesInfo) throws Exception {
                if (regionTypesInfo.getCode() == 0) {
                    RegionTypesInfo.DataBean dataBean = regionTypesInfo.getData().get(1);
                    return bangumiApi.getRegionRecommends(dataBean.getTid());
                }
                return null;
            }
        }).doOnNext(new Consumer<RegionRecommendInfo>() {
            @Override
            public void accept(RegionRecommendInfo regionRecommendInfo) throws Exception {
                banners.clear();
                recommends.clear();
                news.clear();
                dynamics.clear();
            }
        }).map(new Function<RegionRecommendInfo, RegionRecommendInfo.DataBean>() {
            @Override
            public RegionRecommendInfo.DataBean apply(RegionRecommendInfo regionRecommendInfo) throws Exception {
                if (regionRecommendInfo.getCode() == 0) {
                    RegionRecommendInfo.DataBean dataBean = regionRecommendInfo.getData();
                    return dataBean;
                }
                return null;
            }
        }).compose(mCallBackListener.<RegionRecommendInfo.DataBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RegionRecommendInfo.DataBean>() {
                    @Override
                    public void accept(RegionRecommendInfo.DataBean dataBean) throws Exception {
                        if (dataBean != null) {
                            banners.addAll(dataBean.getBanner().getTop());
                            recommends.addAll(dataBean.getRecommend());
                            news.addAll(dataBean.getNewX());
                            dynamics.addAll(dataBean.getDynamic());
                            if(mCallBackListener != null){
                                mCallBackListener.onBangumiRecommendBannerSuccess(banners);
                                mCallBackListener.onBangumiRecommendSuccess(recommends);
                                mCallBackListener.onBangumiNewsSuccess(news);
                                mCallBackListener.onBangumiDynamicSuccess(dynamics);
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

    /**
     * 读取assets下的json数据
     */
    private String readAssetsJson() {
        AssetManager assetManager = BilibiliApp.getInstance().getAssets();
        try {
            InputStream is = assetManager.open("region.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                stringBuilder.append(str);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
