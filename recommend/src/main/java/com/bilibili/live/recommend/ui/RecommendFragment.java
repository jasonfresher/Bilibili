package com.bilibili.live.recommend.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bilibili.live.base.RxLazyFragment;
import com.bilibili.live.base.widget.CustomEmptyView;
import com.bilibili.live.recommend.R;
import com.bilibili.live.recommend.bean.RecommendBannerInfo;
import com.bilibili.live.recommend.bean.RecommendInfo;
import com.bilibili.live.recommend.mvp.presenter.IRecommendPresenter;
import com.bilibili.live.recommend.mvp.presenter.RecommendPresenterImpl;
import com.bilibili.live.recommend.mvp.view.IRecommendView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jason on 2018/9/14.
 */

public class RecommendFragment extends RxLazyFragment implements IRecommendView {

    private IRecommendPresenter recommendPresenter;

    @BindView(R.id.empty_layout)
    protected CustomEmptyView mEmptyView;

    @BindView(R.id.refreshLayout)
    protected SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;

    public static RecommendFragment newInstance(boolean isLazyLoad) {
        Bundle args = new Bundle();
        args.putBoolean(RxLazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        RecommendFragment fragment = new RecommendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.recommend_fragment_layout;
    }

    @Override
    protected void onResumeLazy() {

    }

    @Override
    protected void init() {
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                recommendPresenter.getHomeRecommendData();
            }
        });
        mRefreshLayout.setHeaderMaxDragRate(1.5f);
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {

            }
        });
        mRefreshLayout.autoRefresh();
        recommendPresenter = new RecommendPresenterImpl(this);
    }

    @Override
    public void loadRecommendInfo(List<RecommendBannerInfo.DataBean> mBaseBanners, List<RecommendInfo.ResultBean> mResultBeans) {
        hideEmptyView();
        if(mBaseBanners != null && mBaseBanners.size() > 0){
            mRefreshLayout.finishRefresh(true);
        }else if(mResultBeans != null && mResultBeans.size() > 0){
//            mRefreshLayout.finishRefresh(true);
        }else{
            initEmptyLayout();
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
        initEmptyLayout();
    }

    public void initEmptyLayout() {
        mRefreshLayout.finishRefresh();
        mRecyclerView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
        mEmptyView.setEmptyImage(R.drawable.img_tips_error_load_error);
        mEmptyView.setEmptyText("加载失败~(≧▽≦)~啦啦啦.");
    }


    public void hideEmptyView() {
        mEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
