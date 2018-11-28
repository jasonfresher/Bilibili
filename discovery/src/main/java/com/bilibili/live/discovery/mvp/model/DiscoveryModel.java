package com.bilibili.live.discovery.mvp.model;

import com.bilibili.live.base.api.ApiConstants;
import com.bilibili.live.base.helper.RetrofitHelper;
import com.bilibili.live.discovery.api.DiscoveryApi;
import com.bilibili.live.discovery.bean.HotSearchTag;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jason on 2018/11/28.
 */

public class DiscoveryModel implements IDiscoveryModel {

    private DiscoveryApi discoveryApi;
    private DiscoveryDataCallBackListener mCallBackListener;

    public DiscoveryModel(DiscoveryDataCallBackListener callBackListener) {
        mCallBackListener = callBackListener;
        discoveryApi = RetrofitHelper.createApi(DiscoveryApi.class, ApiConstants.SEARCH_BASE_URL);
    }

    @Override
    public void getHotSearchTags() {
        discoveryApi.getHotSearchTags()
                .subscribeOn(Schedulers.io())
                .compose(mCallBackListener.<HotSearchTag>bindToLifecycle())
                .map(new Function<HotSearchTag, List<HotSearchTag.ListBean>>() {
                    @Override
                    public List<HotSearchTag.ListBean> apply(HotSearchTag hotSearchTag) throws Exception {
                        return hotSearchTag.getList();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<HotSearchTag.ListBean>>() {
                    @Override
                    public void accept(List<HotSearchTag.ListBean> listBeans) throws Exception {
                        if (mCallBackListener != null)
                            mCallBackListener.onHotSearchTagSuccess(listBeans);
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
