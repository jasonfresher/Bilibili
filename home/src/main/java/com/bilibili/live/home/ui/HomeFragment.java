package com.bilibili.live.home.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.bilibili.live.base.RxLazyFragment;
import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.base.mvp.BasePresenter;
import com.bilibili.live.home.R;
import com.bilibili.live.home.R2;
import com.bilibili.live.home.adapter.HomePagerAdapter;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jason on 2018/9/28.
 */

public class HomeFragment extends RxLazyFragment {

    @BindView(R2.id.toolbar)
    protected Toolbar mToolbar;

    @BindView(R2.id.viewpager)
    protected ViewPager mViewPager;

    @BindView(R2.id.tab_layout)
    protected SlidingTabLayout mTabLayout;

    private String[] fragmentRes = {
            RouteInfo.NETCASTING_COMPONENT_NAME,
            RouteInfo.RECOMMEND_COMPONENT_NAME,
            RouteInfo.BANGUMI_COMPONENT_NAME,
            RouteInfo.REGION_COMPONENT_NAME
    };

    public static HomeFragment newInstance(boolean isLazyLoad) {
        Bundle args = new Bundle();
        args.putBoolean(RxLazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.home_fragment_layout;
    }

    @Override
    protected void init() {
        Observable.fromArray(fragmentRes)
                .map(new Function<String, Fragment>() {
                    @Override
                    public Fragment apply(String components) throws Exception {
                        CCResult result = CC.obtainBuilder(components).build().call();
                        Fragment fragment = result.getDataItem(components);
                        return fragment;
                    }
                }).toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Fragment>>() {
                    @Override
                    public void accept(List<Fragment> fragments) throws Exception {
                        HomePagerAdapter mHomeAdapter = new HomePagerAdapter(getChildFragmentManager(),
                                getApplicationContext(),fragments);
                        mViewPager.setOffscreenPageLimit(5);
                        mViewPager.setAdapter(mHomeAdapter);
                        mTabLayout.setViewPager(mViewPager);
                        mViewPager.setCurrentItem(1);
                    }
                });
    }
}
