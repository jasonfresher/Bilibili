package com.bilibili.live.region.mvp.model;

import com.bilibili.live.base.api.ApiConstants;
import com.bilibili.live.base.helper.RetrofitHelper;
import com.bilibili.live.region.api.RegionApi;
import com.bilibili.live.region.bean.RegionDetailsInfo;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jason on 2018/10/17.
 */

public class RegionDetailsModelImpl implements IRegionDetailsModel {

    private RegionApi regionApi;

    private RegionDetailsDataCallBackListener mCallBackListener;

    public RegionDetailsModelImpl(RegionDetailsDataCallBackListener callBackListener) {
        mCallBackListener = callBackListener;
        regionApi = RetrofitHelper.createApi(RegionApi.class, ApiConstants.APP_BASE_URL);
    }

    @Override
    public void getRegionDetailsData(int rid) {
        regionApi.getRegionDetails(rid)
                .map(new Function<RegionDetailsInfo, RegionDetailsInfo.DataBean>() {
                    @Override
                    public RegionDetailsInfo.DataBean apply(RegionDetailsInfo regionDetailsInfo) throws Exception {
                        if(regionDetailsInfo.getCode() == 0)
                            return regionDetailsInfo.getData();
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RegionDetailsInfo.DataBean>() {
                    @Override
                    public void accept(RegionDetailsInfo.DataBean dataBean) throws Exception {
                        List<RegionDetailsInfo.DataBean.RecommendBean> recommends = dataBean.getRecommend();
                        List<RegionDetailsInfo.DataBean.NewBean> newXs = dataBean.getNewX();
                        if(mCallBackListener != null){
                            mCallBackListener.onRecommendInfoSuccess(recommends);
                            mCallBackListener.onNewXInfoSuccess(newXs);
                        }
                    }
                });
    }

}
