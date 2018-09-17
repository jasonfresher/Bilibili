package com.bilibili.live.recommend.api;

import com.bilibili.live.recommend.bean.RecommendBannerInfo;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by jason on 2018/9/14.
 */

public interface RecommendApi{
    @GET("x/banner?plat=4&build=411007&channel=bilih5")
    Observable<RecommendBannerInfo> getRecommendedBannerInfo();
}
