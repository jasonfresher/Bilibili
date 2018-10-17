package com.bilibili.live.region.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bilibili.live.base.RxBaseActivity;
import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.region.R;
import com.bilibili.live.region.R2;
import com.bilibili.live.region.adapter.RegionRecommendPagerAdapter;
import com.bilibili.live.region.bean.RegionTypesInfo;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponentCallback;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by jason on 2018/10/16.
 */

public class RegionTypeDetailsActivity extends RxBaseActivity {

    public static void launch(Activity activity, RegionTypesInfo.DataBean dataBean) {
        Intent mIntent = new Intent(activity, RegionTypeDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("extra_partition", dataBean);
        mIntent.putExtras(bundle);
        activity.startActivity(mIntent);
    }

    private RegionTypesInfo.DataBean mDataBean;

    private FragmentManager fm;

    private FragmentTransaction ft;

    @BindView(R2.id.toolbar)
    protected Toolbar mToolbar;

    @BindView(R2.id.tab_layout)
    protected SlidingTabLayout tabLayout;

    @BindView(R2.id.viewpager)
    protected ViewPager viewPager;

    private List<Fragment> fragments;

    private List<String> mTitles;

    @Override
    public int getLayoutId() {
        return R.layout.region_details_layout;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            mDataBean = mBundle.getParcelable("extra_partition");
            initViewPager(mDataBean);
        }
    }

    private void initViewPager(RegionTypesInfo.DataBean mDataBean) {
        fragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        mTitles.add("推荐");
        fragments.add(RegionTypeRecommendFragment.newInstance(true, mDataBean.getTid()));
        Observable.fromIterable(mDataBean.getChildren())
                .map(new Function<RegionTypesInfo.DataBean.ChildrenBean, String>() {
                    @Override
                    public String apply(RegionTypesInfo.DataBean.ChildrenBean childrenBean) throws Exception {
                        fragments.add(RegionTypeDetailsFragment.newInstance(true,childrenBean.getTid()));
                        return childrenBean.getName();
                    }
                }).toList()
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> titles) throws Exception {
                        mTitles.addAll(titles);
                        viewPager.setAdapter(new RegionRecommendPagerAdapter(getSupportFragmentManager(),fragments,mTitles));
                        viewPager.setOffscreenPageLimit(5);
                        tabLayout.setViewPager(viewPager);
                    }
                });
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle(String.valueOf(mDataBean.getName()));
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
