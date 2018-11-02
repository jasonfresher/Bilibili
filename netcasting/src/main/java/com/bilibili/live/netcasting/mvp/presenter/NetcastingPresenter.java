package com.bilibili.live.netcasting.mvp.presenter;

import com.bilibili.live.base.mvp.BasePresenter;
import com.bilibili.live.netcasting.bean.LiveAppIndexInfo;
import com.bilibili.live.netcasting.mvp.model.INetcastingModel;
import com.bilibili.live.netcasting.mvp.model.NetcastingModel;
import com.bilibili.live.netcasting.mvp.view.INetcastingView;
import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by jason on 2018/9/25.
 */

public class NetcastingPresenter extends BasePresenter<INetcastingView> implements INetcastingModel.NetcastingDataCallBackListener {


    private INetcastingModel mModel;

    public NetcastingPresenter(){
        mModel = new NetcastingModel(this);
    }

    public void getLiveAppIndexInfoData() {
        mModel.getLiveAppIndexInfo();
    }

    @Override
    public void onSuccess(LiveAppIndexInfo.DataBean liveAppIndexInfoDataBean) {
        if(getView() != null)
            getView().loadNetcastingInfo(liveAppIndexInfoDataBean);
    }

    @Override
    public void onFailure(Throwable throwable) {
        if(getView() != null)
            getView().errorCallback(throwable);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        if(getView() != null)
            return getView().bindToLifecycle();
        return null;
    }
}
