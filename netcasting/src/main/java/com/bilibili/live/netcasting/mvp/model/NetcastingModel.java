package com.bilibili.live.netcasting.mvp.model;

import com.bilibili.live.base.api.ApiConstants;
import com.bilibili.live.base.helper.RetrofitHelper;
import com.bilibili.live.netcasting.api.LiveService;
import com.bilibili.live.netcasting.bean.LiveAppIndexInfo;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jason on 2018/9/25.
 */

public class NetcastingModel implements INetcastingModel {

    private LiveService liveService;

    private List<LiveAppIndexInfo.DataBean.BannerBean> banners;
    
    private List<LiveAppIndexInfo.DataBean.EntranceIconsBean> entranceIcons;

    private NetcastingDataCallBackListener mCallBackListener;

    public NetcastingModel(NetcastingDataCallBackListener callBackListener) {
        this.mCallBackListener = callBackListener;
        liveService = RetrofitHelper.createApi(LiveService.class, ApiConstants.LIVE_BASE_URL);
    }

    @Override
    public void getLiveAppIndexInfo() {
        liveService.getLiveAppIndex()
                .map(new Function<LiveAppIndexInfo, LiveAppIndexInfo.DataBean>() {
                    @Override
                    public LiveAppIndexInfo.DataBean apply(LiveAppIndexInfo liveAppIndexInfo) throws Exception {
                        int code = liveAppIndexInfo.getCode();
                        LiveAppIndexInfo.DataBean liveAppIndexInfoData = null;
                        if(code == 0){
                            liveAppIndexInfoData = liveAppIndexInfo.getData();
                        }
                        return liveAppIndexInfoData;
                    }
                }).subscribeOn(Schedulers.io())
                .compose(mCallBackListener.<LiveAppIndexInfo.DataBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LiveAppIndexInfo.DataBean>() {
                    @Override
                    public void accept(LiveAppIndexInfo.DataBean dataBean) throws Exception {
                        if(mCallBackListener != null)
                            mCallBackListener.onSuccess(dataBean);
                    }
                });

    }
}
