package com.bilibili.live.bangumi.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bilibili.live.bangumi.R;
import com.bilibili.live.bangumi.R2;
import com.bilibili.live.bangumi.adapter.BangumiRvAdapter;
import com.bilibili.live.bangumi.bean.RegionRecommendInfo;
import com.bilibili.live.bangumi.entity.BangumiEntity;
import com.bilibili.live.bangumi.mvp.presenter.BangumiPresenter;
import com.bilibili.live.bangumi.mvp.presenter.BangumiPresenterImpl;
import com.bilibili.live.bangumi.mvp.view.BangumiView;
import com.bilibili.live.base.RxLazyFragment;
import com.bilibili.live.base.widget.CustomEmptyView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jason on 2018/9/27.
 */

public class BangumiRecommendFragment extends RxLazyFragment implements BangumiView {

    @BindView(R2.id.empty_layout)
    protected CustomEmptyView mEmptyView;

    @BindView(R2.id.refreshLayout)
    protected SmartRefreshLayout mRefreshLayout;

    @BindView(R2.id.recyclerView)
    protected RecyclerView mRecyclerView;

    private BangumiPresenter presenter;

    private List<BangumiEntity> datas = new ArrayList<>();

    private MultipleItemRvAdapter multipleItemAdapter;

    public static BangumiRecommendFragment newInstance(boolean isLazyLoad) {
        Bundle args = new Bundle();
        args.putBoolean(RxLazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        BangumiRecommendFragment fragment = new BangumiRecommendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.bangumi_recommend_fragment_layout;
    }

    @Override
    protected void init() {
        datas.clear();
        presenter = new BangumiPresenterImpl(this);

        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setHeaderMaxDragRate(2f);
        mRefreshLayout.setEnableNestedScroll(true);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                presenter.getBangumiInfosData();
            }
        });
        mRefreshLayout.autoRefresh();

        multipleItemAdapter = new BangumiRvAdapter(datas);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),4);
        mRecyclerView.setLayoutManager(layoutManager);
        multipleItemAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                int type = datas.get(position).getItemType();
                if (type == BangumiEntity.VIEW_TYPE_BANNER) {
                    return BangumiEntity.BANNER_SPAN_SIZE;
                } else if (type == BangumiEntity.VIEW_TYPE_HEADER) {
                    return BangumiEntity.HEADER_SPAN_SIZE;
                } else if (type == BangumiEntity.VIEW_TYPE_FOOTER) {
                    return BangumiEntity.FOOTER_SPAN_SIZE;
                } else if(type == BangumiEntity.VIEW_TYPE_SPECIAL_LOADED){
                    return BangumiEntity.SPECIAL_LOADED_SPAN_SIZE;
                } else {
                    return BangumiEntity.ITEM_LOADED_SPAN_SIZE;
                }
            }
        });
        mRecyclerView.setAdapter(multipleItemAdapter);
    }

    @Override
    public void onBangumiRecommendBannerSuccess(List<RegionRecommendInfo.DataBean.BannerBean.TopBean> bangumiBanners) {
        datas.clear();
        mRefreshLayout.finishRefresh(true);
        BangumiEntity bangumiEntity = new BangumiEntity(bangumiBanners) {
            @Override
            public int getItemType() {
                return BangumiEntity.VIEW_TYPE_BANNER;
            }
        };
        datas.add(bangumiEntity);
        multipleItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBangumiRecommendSuccess(List<RegionRecommendInfo.DataBean.RecommendBean> bangumiRecommends) {
        BangumiEntity bangumiEntity = new BangumiEntity(165) {
            @Override
            public int getItemType() {
                return BangumiEntity.VIEW_TYPE_HEADER;
            }
        };
        datas.add(bangumiEntity);
        multipleItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBangumiNewsSuccess(List<RegionRecommendInfo.DataBean.NewBean> bangumiNews) {

    }

    @Override
    public void onBangumiDynamicSuccess(List<RegionRecommendInfo.DataBean.DynamicBean> bangumiDynamics) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void errorCallback(Throwable throwable) {
        if(datas.size() == 0) {
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
