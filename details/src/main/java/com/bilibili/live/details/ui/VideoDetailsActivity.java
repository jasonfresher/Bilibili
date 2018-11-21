package com.bilibili.live.details.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilibili.live.base.RxBaseActivity;
import com.bilibili.live.base.constants.ParamsConstant;
import com.bilibili.live.base.constants.RouteActionName;
import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.base.constants.VideoPlayURL;
import com.bilibili.live.base.mvp.BasePresenter;
import com.bilibili.live.details.R;
import com.bilibili.live.details.R2;
import com.bilibili.live.details.bean.VideoDetailsInfo;
import com.bilibili.live.details.event.AppBarStateChangeEvent;
import com.bilibili.live.details.mvp.presenter.DetailsPresenter;
import com.bilibili.live.details.mvp.view.IDetailsView;
import com.bilibili.live.details.utils.UrlHelper;
import com.billy.cc.core.component.CC;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * Created by jason on 2018/10/31.
 */

public class VideoDetailsActivity extends RxBaseActivity implements IDetailsView{

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    @BindView(R2.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R2.id.video_preview)
    ImageView mVideoPreview;

    @BindView(R2.id.tab_layout)
    SlidingTabLayout mSlidingTabLayout;

    @BindView(R2.id.view_pager)
    ViewPager mViewPager;

    @BindView(R2.id.fab)
    FloatingActionButton mFAB;

    @BindView(R2.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    @BindView(R2.id.tv_player)
    TextView mTvPlayer;

    @BindView(R2.id.tv_av)
    TextView mAvText;

    private int av;

    private String imgUrl;

    private DetailsPresenter detailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BasePresenter createPresenter() {
        detailsPresenter = new DetailsPresenter();
        return detailsPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.video_details_layout;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            av = intent.getIntExtra(ParamsConstant.EXTRA_AV, -1);
            imgUrl = intent.getStringExtra(ParamsConstant.EXTRA_IMG_URL);
        }
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                setViewsTranslation(verticalOffset);
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeEvent() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state, int verticalOffset) {
                if (state == State.EXPANDED) {
                    //展开状态
                    mTvPlayer.setVisibility(View.GONE);
                    mAvText.setVisibility(View.VISIBLE);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    mTvPlayer.setVisibility(View.VISIBLE);
                    mAvText.setVisibility(View.GONE);
                } else {
                    mTvPlayer.setVisibility(View.GONE);
                    mAvText.setVisibility(View.VISIBLE);
                }
            }
        });

        Glide.with(VideoDetailsActivity.this)
                .load(UrlHelper.getClearVideoPreviewUrl(imgUrl))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(mVideoPreview);
        detailsPresenter.getDetailsData(av);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mAvText.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadDetailsInfo(final VideoDetailsInfo.DataBean info) {
        mAvText.setText(info.getTitle());
        mFAB.setClickable(true);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random=new Random();
                String playUrl = VideoPlayURL.playUrl[random.nextInt(VideoPlayURL.playUrl.length-1)];
                CC.obtainBuilder(RouteInfo.PLAYER_COMPONENT_NAME)
                        .setActionName(RouteActionName.NORMAL_PLAYER)
                        .addParam(ParamsConstant.EXTRA_PLAYER_URL, playUrl)
                        .addParam(ParamsConstant.EXTRA_PLAYER_HARDDECODE,false)
                        .addParam(ParamsConstant.EXTRA_PLAYER_TITLE,info.getTitle())
                        .build().call();
            }
        });
        mCollapsingToolbarLayout.setTitle("");

        List<Fragment> fragments = new ArrayList<>();
        VideoIntroductionFragment videoIntroductionFragment = VideoIntroductionFragment.newInstance(av,true);
        fragments.add(videoIntroductionFragment);

        List<String> titles = new ArrayList<>();
        titles.add("简介");
        mViewPager.setAdapter(new DetailsPagerAdapter(getSupportFragmentManager(),fragments,titles));
        mViewPager.setOffscreenPageLimit(fragments.size());
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    private static class DetailsPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;
        private List<String> mTitles;

        public DetailsPagerAdapter(FragmentManager fm,List<Fragment> fragments,List<String> titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }

    private void setViewsTranslation(int target) {
        mFAB.setTranslationY(target);
        if (target == 0) {
            showFAB();
        } else if (target < 0) {
            hideFAB();
        }
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

    private void showFAB() {
        mFAB.animate().scaleX(1f).scaleY(1f)
                .setInterpolator(new OvershootInterpolator())
                .start();
        mFAB.setClickable(true);
    }

    private void hideFAB() {
        mFAB.animate().scaleX(0f).scaleY(0f)
                .setInterpolator(new AccelerateInterpolator())
                .start();
        mFAB.setClickable(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
