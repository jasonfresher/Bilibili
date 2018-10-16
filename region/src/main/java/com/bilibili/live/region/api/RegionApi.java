package com.bilibili.live.region.api;

import com.bilibili.live.region.bean.RegionRecommendInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jason on 2018/10/16.
 */

public interface RegionApi {
    /**
     * 分区推荐
     */
    @GET("x/v2/region/show?access_key=67cbf6a1e9ad7d7f11bfbd918e50c837&actionKey=appkey&appkey=27eb53fc9058f8c3&build=3600&device=phone&mobi_app=iphone&plat=1&platform=ios&sign=959d7b8c09c65e7a66f7e58b1a2bdab9&ts=1472310694")
    Observable<RegionRecommendInfo> getRegionRecommends(@Query("rid") int rid);
}
