package com.bilibili.live.discovery.mvp.presenter;

import com.bilibili.live.base.mvp.BasePresenter;
import com.bilibili.live.discovery.bean.HotSearchTag;
import com.bilibili.live.discovery.mvp.model.DiscoveryModel;
import com.bilibili.live.discovery.mvp.model.IDiscoveryModel;
import com.bilibili.live.discovery.mvp.view.IDiscoveryView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.List;

/**
 * Created by jason on 2018/11/28.
 */

public class DiscoveryPresenter extends BasePresenter<IDiscoveryView> implements IDiscoveryModel.DiscoveryDataCallBackListener {

    private IDiscoveryModel mDiscoveryModel;

    public DiscoveryPresenter() {
        mDiscoveryModel = new DiscoveryModel(this);
    }

    public void getHotSearchTagData(){
        mDiscoveryModel.getHotSearchTags();
    }


    @Override
    public void onHotSearchTagSuccess(List<HotSearchTag.ListBean> hotSearchTags) {
        if(getView() != null)
            getView().loadHotSearchTags(hotSearchTags);
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
