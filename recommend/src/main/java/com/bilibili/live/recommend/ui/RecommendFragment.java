package com.bilibili.live.recommend.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bilibili.live.base.RxLazyFragment;
import com.bilibili.live.base.widget.CustomEmptyView;
import com.bilibili.live.recommend.R;
import com.bilibili.live.recommend.adapter.RecommendAdapter;
import com.bilibili.live.recommend.bean.RecommendInfo;
import com.bilibili.live.recommend.entity.RecommendMultiItem;
import com.bilibili.live.recommend.mvp.presenter.IRecommendPresenter;
import com.bilibili.live.recommend.mvp.presenter.RecommendPresenterImpl;
import com.bilibili.live.recommend.mvp.view.IRecommendView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jason on 2018/9/14.
 */

public class RecommendFragment extends RxLazyFragment implements IRecommendView {

    @BindView(R.id.empty_layout)
    protected CustomEmptyView mEmptyView;

    @BindView(R.id.refreshLayout)
    protected SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;

    private IRecommendPresenter recommendPresenter;

    private List<RecommendMultiItem> data;

    private RecommendAdapter multipleItemAdapter;

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
    protected void init() {
        data = new ArrayList<>();
        recommendPresenter = new RecommendPresenterImpl(this);
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setHeaderMaxDragRate(2f);
        mRefreshLayout.setEnableNestedScroll(true);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                data.clear();
                recommendPresenter.getRecommendBannerData();
            }
        });
        mRefreshLayout.autoRefresh();
        multipleItemAdapter = new RecommendAdapter(getActivity(), data);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        mRecyclerView.setLayoutManager(manager);
        multipleItemAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return data.get(position).getSpanSize();
            }
        });
        mRecyclerView.setAdapter(multipleItemAdapter);
    }

    @Override
    public void loadRecommendBannerInfo(RecommendMultiItem recommendMultiItem) {
        mRefreshLayout.finishRefresh(true);
        data.add(recommendMultiItem);
        multipleItemAdapter.notifyDataSetChanged();
        recommendPresenter.getRecommendContentData();
    }

    @Override
    public void loadRecommendContentInfo(List<RecommendInfo.ResultBean> results) {
//        initEmptyLayout();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void errorCallback(Throwable throwable) {
        if(data.size() == 0) {
            initEmptyLayout();
        }
    }

    public void initEmptyLayout() {
        mRefreshLayout.finishRefresh(false);
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
