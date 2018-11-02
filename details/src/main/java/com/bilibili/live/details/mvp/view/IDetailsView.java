package com.bilibili.live.details.mvp.view;

import com.bilibili.live.details.bean.VideoDetailsInfo;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/10/31.
 */

public interface IDetailsView {
    void showProgress();
    void hideProgress();
    void errorCallback(Throwable throwable);
    void loadDetailsInfo(VideoDetailsInfo.DataBean info);
    <T> LifecycleTransformer<T> bindToLifecycle();
}
