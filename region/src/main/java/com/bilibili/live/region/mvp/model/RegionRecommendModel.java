package com.bilibili.live.region.mvp.model;

import com.bilibili.live.base.api.ApiConstants;
import com.bilibili.live.base.helper.RetrofitHelper;
import com.bilibili.live.region.api.RegionApi;
import com.bilibili.live.region.bean.RegionRecommendInfo;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jason on 2018/10/16.
 */

public class RegionRecommendModel implements IRegionRecommendModel {

    private RegionApi regionApi;

    private RegionRecommendDataCallBackListener mListener;

    public RegionRecommendModel(RegionRecommendDataCallBackListener listener) {
        mListener = listener;
        regionApi = RetrofitHelper.createApi(RegionApi.class, ApiConstants.APP_BASE_URL);
    }

    @Override
    public void getRegionRecommendsData(int rid) {
        regionApi.getRegionRecommends(rid)
                .subscribeOn(Schedulers.io())
                .map(new Function<RegionRecommendInfo, RegionRecommendInfo.DataBean>() {
                    @Override
                    public RegionRecommendInfo.DataBean apply(RegionRecommendInfo regionRecommendInfo) throws Exception {
                        if(regionRecommendInfo != null && regionRecommendInfo.getCode() == 0)
                            return regionRecommendInfo.getData();
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RegionRecommendInfo.DataBean>() {
                    @Override
                    public void accept(RegionRecommendInfo.DataBean dataBean) throws Exception {
                        List<RegionRecommendInfo.DataBean.BannerBean.TopBean> bannerTops = dataBean.getBanner().getTop();
                        List<RegionRecommendInfo.DataBean.RecommendBean> recommends = dataBean.getRecommend();
                        List<RegionRecommendInfo.DataBean.NewBean> newXs = dataBean.getNewX();
                        List<RegionRecommendInfo.DataBean.DynamicBean> dynamics = dataBean.getDynamic();
                        if(mListener != null){
                            mListener.onBannerInfoSuccess(bannerTops);
                            mListener.onRecommendInfoSuccess(recommends);
                            mListener.onNewXInfoSuccess(newXs);
                            mListener.ondynamicInfoSuccess(dynamics);
                        }
                    }
                });
    }
}
