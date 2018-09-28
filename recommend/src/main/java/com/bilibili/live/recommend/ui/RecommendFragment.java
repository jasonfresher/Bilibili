package com.bilibili.live.recommend.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bilibili.live.base.RxLazyFragment;
import com.bilibili.live.base.widget.CustomEmptyView;
import com.bilibili.live.recommend.R;
import com.bilibili.live.recommend.R2;
import com.bilibili.live.recommend.adapter.RecommendRvAdapter;
import com.bilibili.live.recommend.bean.RecommendInfo;
import com.bilibili.live.recommend.entity.RecommendEntity;
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

    @BindView(R2.id.empty_layout)
    protected CustomEmptyView mEmptyView;

    @BindView(R2.id.refreshLayout)
    protected SmartRefreshLayout mRefreshLayout;

    @BindView(R2.id.recyclerView)
    protected RecyclerView mRecyclerView;

    private IRecommendPresenter recommendPresenter;

    private List<RecommendEntity> data;

    private RecommendRvAdapter multipleItemAdapter;

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
        mRefreshLayout.setHeaderHeight(66);
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setHeaderMaxDragRate(2f);
        mRefreshLayout.setEnableNestedScroll(true);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                data.clear();
                multipleItemAdapter.notifyDataSetChanged();
                recommendPresenter.getRecommendBannerData();
            }
        });
        mRefreshLayout.autoRefresh();
        multipleItemAdapter = new RecommendRvAdapter(data);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        mRecyclerView.setLayoutManager(manager);
        multipleItemAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                int type = data.get(position).getItemType();
                if (type == RecommendEntity.VIEW_TYPE_BANNER) {
                    return RecommendEntity.BANNER_SPAN_SIZE;
                } else if (type == RecommendEntity.VIEW_TYPE_HEADER) {
                    return RecommendEntity.HEADER_SPAN_SIZE;
                } else if (type == RecommendEntity.VIEW_TYPE_FOOTER) {
                    return RecommendEntity.FOOTER_SPAN_SIZE;
                } else if(type == RecommendEntity.VIEW_TYPE_SPECIAL_LOADED){
                    return RecommendEntity.SPECIAL_LOADED_SPAN_SIZE;
                } else {
                    return RecommendEntity.ITEM_LOADED_SPAN_SIZE;
                }
            }
        });
        mRecyclerView.setAdapter(multipleItemAdapter);
    }

    @Override
    public void loadRecommendBannerInfo(RecommendEntity recommendBannerEntity) {
//        mRefreshLayout.finishRefresh(true);
        data.add(recommendBannerEntity);
        multipleItemAdapter.notifyDataSetChanged();
        recommendPresenter.getRecommendContentData();
    }

    @Override
    public void loadRecommendContentInfo(List<RecommendInfo.ResultBean> results) {
        mRefreshLayout.finishRefresh(true);
        for (RecommendInfo.ResultBean recommendInfo : results){
            if(recommendInfo.getType().equals("activity")){
                data.add(recommendInfo);
            }else{
                RecommendInfo.ResultBean.HeadBean head = recommendInfo.getHead();
                head.setTitleType(recommendInfo.getType());
                data.add(head);
                List<RecommendInfo.ResultBean.BodyBean> bodys = recommendInfo.getBody();
                for (RecommendInfo.ResultBean.BodyBean bodyBean : bodys){
                    bodyBean.setTitleType(recommendInfo.getType());
                    data.add(bodyBean);
                }
            }

            if(!recommendInfo.getType().equals("activity")){
                RecommendInfo.ResultBean.FootBean footBean = new RecommendInfo.ResultBean.FootBean();
                footBean.setTitleType(recommendInfo.getType());
                data.add(footBean);
            }
        }
        multipleItemAdapter.notifyDataSetChanged();
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
        }else{
            mRefreshLayout.finishRefresh(true);
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
