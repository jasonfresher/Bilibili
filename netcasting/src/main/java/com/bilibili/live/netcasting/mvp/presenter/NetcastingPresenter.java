package com.bilibili.live.netcasting.mvp.presenter;

import com.bilibili.live.netcasting.bean.LiveAppIndexInfo;
import com.bilibili.live.netcasting.mvp.model.INetcastingModel;
import com.bilibili.live.netcasting.mvp.model.NetcastingModelImpl;
import com.bilibili.live.netcasting.mvp.view.INetcastingView;
import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by jason on 2018/9/25.
 */

public class NetcastingPresenter implements INetcastingPresenter,INetcastingModel.NetcastingDataCallBackListener {

    private INetcastingView mView;

    private INetcastingModel mModel;

    public NetcastingPresenter(INetcastingView view){
        mView = view;
        mModel = new NetcastingModelImpl(this);
    }

    @Override
    public void getLiveAppIndexInfoData() {
        mModel.getLiveAppIndexInfo();
    }

    @Override
    public void onSuccess(LiveAppIndexInfo.DataBean liveAppIndexInfoDataBean) {
        mView.loadNetcastingInfo(liveAppIndexInfoDataBean);
    }

    @Override
    public void onFailure(Throwable throwable) {
        mView.errorCallback(throwable);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return mView.bindToLifecycle();
    }
}
