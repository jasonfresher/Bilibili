package com.bilibili.live.netcasting.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.bilibili.live.base.RxLazyFragment;
import com.bilibili.live.base.widget.CustomEmptyView;
import com.bilibili.live.netcasting.R;
import com.bilibili.live.netcasting.R2;
import com.bilibili.live.netcasting.bean.LiveAppIndexInfo;
import com.bilibili.live.netcasting.mvp.presenter.INetcastingPresenter;
import com.bilibili.live.netcasting.mvp.presenter.NetcastingPresenter;
import com.bilibili.live.netcasting.mvp.view.INetcastingView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by jason on 2018/9/25.
 */

public class NetcastingFragment extends RxLazyFragment implements INetcastingView {

    @BindView(R2.id.empty_layout)
    protected CustomEmptyView mEmptyView;

    @BindView(R2.id.refreshLayout)
    protected SmartRefreshLayout mRefreshLayout;

    @BindView(R2.id.recyclerView)
    protected RecyclerView mRecyclerView;

    private INetcastingPresenter presenter;

    public static NetcastingFragment newInstance(boolean isLazyLoad) {
        Bundle args = new Bundle();
        args.putBoolean(RxLazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        NetcastingFragment fragment = new NetcastingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.netcasting_fragment_layout;
    }

    @Override
    protected void init() {
        presenter = new NetcastingPresenter(this);

        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setHeaderMaxDragRate(2f);
        mRefreshLayout.setEnableNestedScroll(true);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                presenter.getLiveAppIndexInfoData();
            }
        });
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void loadNetcastingInfo(LiveAppIndexInfo.DataBean liveDataBean) {
        List<LiveAppIndexInfo.DataBean.BannerBean> banner = liveDataBean.getBanner();
        Observable.fromIterable(banner)
                .subscribe(new Consumer<LiveAppIndexInfo.DataBean.BannerBean>() {
                    @Override
                    public void accept(LiveAppIndexInfo.DataBean.BannerBean bannerBean) throws Exception {
                        
                    }
                });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void errorCallback(Throwable throwable) {

    }
}
