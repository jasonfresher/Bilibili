package com.bilibili.live.details.mvp.model;

import com.bilibili.live.details.bean.VideoDetailsInfo;
import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by jason on 2018/10/31.
 */

public interface IDetailsModel {
    void getDetailsInfos(int aid);
    interface DetailsDataCallBackListener{
        void onDetailsInfoSuccess(VideoDetailsInfo.DataBean videoDetailsInfo);
        void onFailure(Throwable throwable);
        <T> LifecycleTransformer<T> bindToLifecycle();
    }
}
