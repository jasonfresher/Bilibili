package com.bilibili.live.region.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bilibili.live.base.RxLazyFragment;
import com.bilibili.live.region.R;
import com.bilibili.live.region.R2;
import com.bilibili.live.region.adapter.RegionAdapter;
import com.bilibili.live.region.bean.RegionHomeItemBean;
import com.bilibili.live.region.mvp.presenter.IRegionHomePresenter;
import com.bilibili.live.region.mvp.presenter.RegionHomePresenterImpl;
import com.bilibili.live.region.mvp.view.IRegionHomeView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jason on 2018/10/15.
 */

public class RegionFragment extends RxLazyFragment implements IRegionHomeView {

    @BindView(R2.id.recyclerview)
    protected RecyclerView mRecyclerView;

    private GridLayoutManager mLayoutManager;

    public static RegionFragment newInstance(boolean isLazyLoad) {
        Bundle args = new Bundle();
        args.putBoolean(RxLazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        RegionFragment fragment = new RegionFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    protected int getLayoutResId() {
        return R.layout.region_home_layout;
    }

    @Override
    protected void init() {
        IRegionHomePresenter homePresenter = new RegionHomePresenterImpl(this);
        homePresenter.getRegionHomeData();
        mLayoutManager = new GridLayoutManager(getActivity(),3);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void loadRegionHomeInfo(List<RegionHomeItemBean> regionHomeItemBeans) {
        RegionAdapter regionAdapter = new RegionAdapter(R.layout.item_home_region,regionHomeItemBeans);
        mRecyclerView.setAdapter(regionAdapter);
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
