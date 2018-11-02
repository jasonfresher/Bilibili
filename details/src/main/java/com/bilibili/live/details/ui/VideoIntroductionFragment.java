package com.bilibili.live.details.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bilibili.live.base.RxLazyFragment;
import com.bilibili.live.base.mvp.BasePresenter;
import com.bilibili.live.details.R;
import com.bilibili.live.details.R2;
import com.bilibili.live.details.adapter.VideoIntroductionAdapter;
import com.bilibili.live.details.bean.VideoDetailsInfo;
import com.bilibili.live.details.mvp.presenter.DetailsPresenter;
import com.bilibili.live.details.mvp.view.IDetailsView;
import com.bilibili.live.details.utils.NumberUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jason on 2018/11/1.
 */

public class VideoIntroductionFragment extends RxLazyFragment<IDetailsView,BasePresenter<IDetailsView>> implements IDetailsView {

//    @BindView(R2.id.tv_title)
    TextView mTitleText;

//    @BindView(R2.id.tv_play_time)
    TextView mPlayTimeText;

//    @BindView(R2.id.tv_review_count)
    TextView mReviewCountText;

//    @BindView(R2.id.tv_description)
    TextView mDescText;

//    @BindView(R2.id.share_num)
    TextView mShareNum;

//    @BindView(R2.id.coin_num)
    TextView mCoinNum;

//    @BindView(R2.id.fav_num)
    TextView mFavNum;

//    @BindView(R2.id.user_avatar)
    ImageView mUserAvatar;

//    @BindView(R2.id.user_name)
    TextView mUserName;

//    @BindView(R2.id.tags_layout)
    TagFlowLayout mTagFlowLayout;

//    @BindView(R2.id.layout_video_related)
    RelativeLayout mVideoRelatedLayout;

    @BindView(R2.id.recycle)
    RecyclerView mRecyclerView;




    private int aid;
    private DetailsPresenter detailsPresenter;
    private View headerView;


    public static VideoIntroductionFragment newInstance(int aid, boolean isLazyLoad) {
        Bundle args = new Bundle();
        args.putBoolean(RxLazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        args.putInt("extra_av",aid);
        VideoIntroductionFragment fragment = new VideoIntroductionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_details_introduction_layout;
    }

    @Override
    protected BasePresenter createPresenter() {
        detailsPresenter = new DetailsPresenter();
        return detailsPresenter;
    }

    @Override
    protected void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            aid  = bundle.getInt("extra_av");
        }
        headerView = LayoutInflater.from(getContext()).inflate(R.layout.details_video_header_layout, null, false);
        mTitleText = headerView.findViewById(R.id.tv_title);
        mPlayTimeText = headerView.findViewById(R.id.tv_play_time);
        mReviewCountText = headerView.findViewById(R.id.tv_review_count);
        mDescText = headerView.findViewById(R.id.tv_description);
        mShareNum = headerView.findViewById(R.id.share_num);
        mCoinNum = headerView.findViewById(R.id.coin_num);
        mFavNum = headerView.findViewById(R.id.fav_num);
        mUserAvatar = headerView.findViewById(R.id.user_avatar);
        mUserName = headerView.findViewById(R.id.user_name);
        mTagFlowLayout = headerView.findViewById(R.id.tags_layout);
        mVideoRelatedLayout = headerView.findViewById(R.id.layout_video_related);
        headerView.findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"11111",Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        detailsPresenter.getDetailsData(aid);
    }

    @Override
    public void loadDetailsInfo(VideoDetailsInfo.DataBean info) {
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
        List<VideoDetailsInfo.DataBean.RelatesBean> relates = info.getRelates();
        VideoIntroductionAdapter adapter = new VideoIntroductionAdapter(R.layout.item_video_strip_layout, relates);
        adapter.addHeaderView(headerView);
        mRecyclerView.setAdapter(adapter);
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
