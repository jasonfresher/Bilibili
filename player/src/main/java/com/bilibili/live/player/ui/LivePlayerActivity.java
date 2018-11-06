package com.bilibili.live.player.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilibili.live.base.RxBaseActivity;
import com.bilibili.live.base.constants.ParamsConstant;
import com.bilibili.live.base.constants.VideoPlayURL;
import com.bilibili.live.base.mvp.BasePresenter;
import com.bilibili.live.player.R;
import com.bilibili.live.player.R2;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Random;

import butterknife.BindView;

/**
 * Created by jason on 2018/11/6.
 */

public class LivePlayerActivity extends RxBaseActivity {

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    @BindView(R2.id.user_pic)
    ImageView mUserPic;

    @BindView(R2.id.user_name)
    TextView mUserName;

    @BindView(R2.id.live_num)
    TextView mLiveNum;

    private int cid;
    private String title;
    private int online;
    private String face;
    private String name;
    private int mid;
    private boolean hardDecode;
    private String playUrl;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live_player_layout;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent !=null){
            playUrl = intent.getStringExtra(ParamsConstant.EXTRA_PLAYER_URL);
            cid = intent.getIntExtra(ParamsConstant.EXTRA_CID, 0);
            title = intent.getStringExtra(ParamsConstant.EXTRA_TITLE);
            online = intent.getIntExtra(ParamsConstant.EXTRA_ONLINE, 0);
            face = intent.getStringExtra(ParamsConstant.EXTRA_FACE);
            name = intent.getStringExtra(ParamsConstant.EXTRA_NAME);
            mid = intent.getIntExtra(ParamsConstant.EXTRA_MID, 0);
            hardDecode = intent.getBooleanExtra(ParamsConstant.EXTRA_PLAYER_HARDDECODE,false);
        }
        Random random=new Random();
        playUrl = VideoPlayURL.playUrl[random.nextInt(VideoPlayURL.playUrl.length-1)];
        VideoPlayerFragment playerFragment = VideoPlayerFragment.newInstance(true, playUrl, title, hardDecode);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.live_preview, playerFragment);
        ft.commit();
        mUserName.setText(name);
        mLiveNum.setText(String.valueOf(online));
        Glide.with(this)
                .load(face)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(mUserPic);
    }

    @Override
    protected void initToolBar() {
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if(supportActionBar != null){
            supportActionBar.setDisplayHomeAsUpEnabled(true);
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
