package com.bilibili.live.details.mvp.model;

import com.bilibili.live.base.api.ApiConstants;
import com.bilibili.live.base.helper.RetrofitHelper;
import com.bilibili.live.details.api.DetailsApi;
import com.bilibili.live.details.bean.VideoDetailsInfo;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jason on 2018/10/31.
 */

public class DetailsModel implements IDetailsModel {

    private DetailsApi mDetailsApi;

    private DetailsDataCallBackListener mCallBackListener;

    public DetailsModel(DetailsDataCallBackListener detailsDataCallBackListener ){
        mDetailsApi = RetrofitHelper.createApi(DetailsApi.class, ApiConstants.APP_BASE_URL);
        mCallBackListener = detailsDataCallBackListener;
    }

    @Override
    public void getDetailsInfos(int aid) {
        mDetailsApi.getVideoDetails(aid)
                .subscribeOn(Schedulers.io())
                .map(new Function<VideoDetailsInfo, VideoDetailsInfo.DataBean>() {
                    @Override
                    public VideoDetailsInfo.DataBean apply(VideoDetailsInfo videoDetailsInfo) throws Exception {
                        if(videoDetailsInfo.getCode() == 0) {
                             return videoDetailsInfo.getData();
                        }
                        return null;
                    }
                })
                .compose(mCallBackListener.<VideoDetailsInfo.DataBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VideoDetailsInfo.DataBean>() {
                    @Override
                    public void accept(VideoDetailsInfo.DataBean dataBean) throws Exception {
                        if(mCallBackListener != null)
                            mCallBackListener.onDetailsInfoSuccess(dataBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if(mCallBackListener != null)
                            mCallBackListener.onFailure(throwable);
                    }
                });

    }
}
