package com.bilibili.live.home.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bilibili.live.base.RxLazyFragment;
import com.bilibili.live.base.constants.ConstantUtil;
import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.base.mvp.BasePresenter;
import com.bilibili.live.base.utils.PreferenceUtil;
import com.bilibili.live.home.R;
import com.bilibili.live.home.R2;
import com.bilibili.live.home.adapter.HomePagerAdapter;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import skin.support.SkinCompatManager;

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

    @BindView(R2.id.iv_head_switch_mode)
    protected ImageView skinSwitch;

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
        return R.layout.home_activity_layout;
    }


    @Override
    protected void init() {
        boolean flag = PreferenceUtil.getBoolean(ConstantUtil.SWITCH_MODE_KEY, false);
        if (flag) {
            skinSwitch.setImageResource(R.drawable.home_ic_switch_daily);
        } else {
            skinSwitch.setImageResource(R.drawable.home_ic_switch_night);
        }
        skinSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchNightMode();
            }
        });

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
                        mViewPager.setOffscreenPageLimit(fragments.size());
                        mViewPager.setAdapter(mHomeAdapter);
                        mTabLayout.setViewPager(mViewPager);
                        mViewPager.setCurrentItem(1);
                    }
                });
    }

    /**
     * 日夜间模式切换
     */
    private void switchNightMode() {
        boolean isNight = PreferenceUtil.getBoolean(ConstantUtil.SWITCH_MODE_KEY, false);
        if (isNight) {
            // 日间模式
            SkinCompatManager.getInstance().restoreDefaultTheme();
            skinSwitch.setImageResource(R.drawable.home_ic_switch_night);
            PreferenceUtil.putBoolean(ConstantUtil.SWITCH_MODE_KEY, false);
        } else {
            // 夜间模式
            SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载
            skinSwitch.setImageResource(R.drawable.home_ic_switch_daily);
            PreferenceUtil.putBoolean(ConstantUtil.SWITCH_MODE_KEY, true);
        }
    }

}
