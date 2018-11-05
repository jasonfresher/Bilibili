package com.bilibili.live.details.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilibili.live.details.R;
import com.bilibili.live.details.R2;
import com.bilibili.live.details.bean.VideoDetailsInfo;
import com.bilibili.live.details.utils.NumberUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jason on 2018/11/5.
 */

public class DetailsVideoHeader extends LinearLayout {

    @BindView(R2.id.tv_title)
    TextView mTitleText;

    @BindView(R2.id.tv_play_time)
    TextView mPlayTimeText;

    @BindView(R2.id.tv_review_count)
    TextView mReviewCountText;

    @BindView(R2.id.tv_description)
    TextView mDescText;

    @BindView(R2.id.share_num)
    TextView mShareNum;

    @BindView(R2.id.coin_num)
    TextView mCoinNum;

    @BindView(R2.id.fav_num)
    TextView mFavNum;

    @BindView(R2.id.user_avatar)
    ImageView mUserAvatar;

    @BindView(R2.id.user_name)
    TextView mUserName;

    @BindView(R2.id.tags_layout)
    TagFlowLayout mTagFlowLayout;

    @BindView(R2.id.layout_video_related)
    RelativeLayout mVideoRelatedLayout;

    public DetailsVideoHeader(@NonNull Context context) {
        super(context);
        init();
    }

    public DetailsVideoHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.details_video_header_layout, this);
        ButterKnife.bind(headerView);
    }

    public void initContent(VideoDetailsInfo.DataBean info){
        mTitleText.setText(info.getTitle());
        mPlayTimeText.setText(NumberUtil.converString(info.getStat().getView()));
        mReviewCountText.setText(NumberUtil.converString(info.getStat().getDanmaku()));
        mDescText.setText(info.getDesc());

        mShareNum.setText(NumberUtil.converString(info.getStat().getShare()));
        mFavNum.setText(NumberUtil.converString(info.getStat().getFavorite()));
        mCoinNum.setText(NumberUtil.converString(info.getStat().getCoin()));

        Glide.with(getContext())
                .load(info.getOwner().getFace())
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.ico_user_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mUserAvatar);
        mUserName.setText(info.getOwner().getName());
    }


}
