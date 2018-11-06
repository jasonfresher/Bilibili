package com.bilibili.live.details.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bilibili.live.base.RxLazyFragment;
import com.bilibili.live.base.constants.ParamsConstant;
import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.base.mvp.BasePresenter;
import com.bilibili.live.details.R;
import com.bilibili.live.details.R2;
import com.bilibili.live.details.adapter.VideoIntroductionAdapter;
import com.bilibili.live.details.bean.VideoDetailsInfo;
import com.bilibili.live.details.mvp.presenter.DetailsPresenter;
import com.bilibili.live.details.mvp.view.IDetailsView;
import com.bilibili.live.details.widget.DetailsVideoHeader;
import com.billy.cc.core.component.CC;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jason on 2018/11/1.
 */

public class VideoIntroductionFragment extends RxLazyFragment<IDetailsView,BasePresenter<IDetailsView>> implements IDetailsView {

    @BindView(R2.id.recycle)
    RecyclerView mRecyclerView;

    private int aid;
    private DetailsPresenter detailsPresenter;


    public static VideoIntroductionFragment newInstance(int aid, boolean isLazyLoad) {
        Bundle args = new Bundle();
        args.putBoolean(RxLazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        args.putInt(ParamsConstant.EXTRA_AV,aid);
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
            aid  = bundle.getInt(ParamsConstant.EXTRA_AV);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        detailsPresenter.getDetailsData(aid);
    }

    @Override
    public void loadDetailsInfo(VideoDetailsInfo.DataBean info) {
        final List<VideoDetailsInfo.DataBean.RelatesBean> relates = info.getRelates();
        VideoIntroductionAdapter adapter = new VideoIntroductionAdapter(R.layout.item_video_strip_layout, relates);
        DetailsVideoHeader headerView = new DetailsVideoHeader(getContext());
        headerView.initContent(info);
        adapter.addHeaderView(headerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                VideoDetailsInfo.DataBean.RelatesBean info = relates.get(position);
                CC.obtainBuilder(RouteInfo.VIDEODETAILS_COMPONENT_NAME)
                        .addParam(ParamsConstant.EXTRA_AV,info.getAid())
                        .addParam(ParamsConstant.EXTRA_IMG_URL,info.getPic())
                        .build()
                        .call();
            }
        });
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
