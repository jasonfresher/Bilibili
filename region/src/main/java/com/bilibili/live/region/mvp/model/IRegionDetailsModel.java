package com.bilibili.live.region.mvp.model;

import com.bilibili.live.region.bean.RegionDetailsInfo;
import com.bilibili.live.region.bean.RegionHomeItemBean;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/10/17.
 */

public interface IRegionDetailsModel {
    void getRegionDetailsData(int rid);
    interface RegionDetailsDataCallBackListener{
        void onRecommendInfoSuccess(List<RegionDetailsInfo.DataBean.RecommendBean> recommends);
        void onNewXInfoSuccess(List<RegionDetailsInfo.DataBean.NewBean> newXs);
        void onFailure(Throwable throwable);
        <T> LifecycleTransformer<T> bindToLifecycle();
    }
}
