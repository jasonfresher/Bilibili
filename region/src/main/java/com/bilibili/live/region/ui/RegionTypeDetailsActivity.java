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
import android.text.TextPaint;
import android.view.MenuItem;
import android.widget.TextView;

import com.bilibili.live.base.RxBaseActivity;
import com.bilibili.live.base.rx.RxBus;
import com.bilibili.live.region.R;
import com.bilibili.live.region.R2;
import com.bilibili.live.region.adapter.RegionRecommendPagerAdapter;
import com.bilibili.live.region.bean.RegionTypesInfo;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
        initRxbus();
    }

    private void initRxbus() {
        RxBus.getInstance().toObservable()
                .map(new Function<Object, Integer>() {
                    @Override
                    public Integer apply(Object o) throws Exception {
                        return (Integer) o;
                    }
                })
                .compose(this.<Integer>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        switchPager(integer);
                    }
                });
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
                        viewPager.setOffscreenPageLimit(7);
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

    private void switchPager(int position) {

        switch (position) {
            case 0:
                viewPager.setCurrentItem(1);
                break;

            case 1:
                viewPager.setCurrentItem(2);
                break;

            case 2:
                viewPager.setCurrentItem(3);
                break;

            case 3:
                viewPager.setCurrentItem(4);
                break;

            case 4:
                viewPager.setCurrentItem(5);
                break;

            case 5:
                viewPager.setCurrentItem(6);
                break;

            case 6:
                viewPager.setCurrentItem(7);
                break;
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
