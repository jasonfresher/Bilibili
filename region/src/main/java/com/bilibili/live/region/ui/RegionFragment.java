package com.bilibili.live.region.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bilibili.live.base.RxLazyFragment;
import com.bilibili.live.region.R;
import com.bilibili.live.region.R2;
import com.bilibili.live.region.adapter.RegionAdapter;
import com.bilibili.live.region.bean.RegionHomeItemBean;
import com.bilibili.live.region.bean.RegionTypesInfo;
import com.bilibili.live.region.mvp.presenter.IRegionHomePresenter;
import com.bilibili.live.region.mvp.presenter.RegionHomePresenterImpl;
import com.bilibili.live.region.mvp.view.IRegionHomeView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jason on 2018/10/15.
 */

public class RegionFragment extends RxLazyFragment implements IRegionHomeView,BaseQuickAdapter.OnItemClickListener {

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
        regionAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(regionAdapter);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position) {
            case 0:
                RegionTypesInfo.DataBean dataBean = new RegionTypesInfo.DataBean();
                dataBean.setName("直播");
                RegionTypeDetailsActivity.launch(getActivity(),dataBean,true);
                break;

            case 1:
                //番剧
                RegionTypesInfo.DataBean dataBean2 = new RegionTypesInfo.DataBean();
                dataBean2.setName("番剧");
                dataBean2.setReid(13);
                RegionTypeDetailsActivity.launch(getActivity(),dataBean2,false);
                break;

            case 2:
                //动画
                break;

            case 3:
                //音乐
                break;

            case 4:
                //舞蹈
                break;

            case 5:
                //游戏
                break;

            case 6:
                //科技
                break;

            case 7:
                //生活
                break;

            case 8:
                //鬼畜
                break;

            case 9:
                //时尚
                break;

            case 10:
                //广告
                break;

            case 11:
                //娱乐
                break;

            case 12:
                //电影
                break;

            case 13:
                //电视剧
                break;

            case 14:
                // 游戏中心
                break;

            default:
                break;
        }
    }

    @Override
    public void errorCallback(Throwable throwable) {

    }
}
