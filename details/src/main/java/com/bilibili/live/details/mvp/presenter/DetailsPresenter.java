package com.bilibili.live.details.mvp.presenter;

import com.bilibili.live.base.mvp.BasePresenter;
import com.bilibili.live.details.bean.VideoDetailsInfo;
import com.bilibili.live.details.mvp.model.DetailsModel;
import com.bilibili.live.details.mvp.model.IDetailsModel;
import com.bilibili.live.details.mvp.view.IDetailsView;
import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by jason on 2018/10/31.
 */

public class DetailsPresenter extends BasePresenter<IDetailsView> implements IDetailsModel.DetailsDataCallBackListener {


    private IDetailsModel mDetailsModel;

    public DetailsPresenter(){
        mDetailsModel = new DetailsModel(this);
    }

    public void getDetailsData(int aid) {
        if(getView() != null)
            getView().showProgress();
        mDetailsModel.getDetailsInfos(aid);
    }

    @Override
    public void onDetailsInfoSuccess(VideoDetailsInfo.DataBean videoDetailsInfo) {
        if(getView() != null) {
            getView().hideProgress();
            getView().loadDetailsInfo(videoDetailsInfo);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        if(getView() != null) {
            getView().errorCallback(throwable);
            getView().hideProgress();
        }
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        if(getView() != null)
            return getView().bindToLifecycle();
        return null;
    }
}
