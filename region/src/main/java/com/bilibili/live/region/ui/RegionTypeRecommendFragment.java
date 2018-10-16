package com.bilibili.live.region.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bilibili.live.base.RxLazyFragment;
import com.bilibili.live.base.widget.CustomEmptyView;
import com.bilibili.live.region.R;
import com.bilibili.live.region.R2;
import com.bilibili.live.region.adapter.RegionRecommendAdapter;
import com.bilibili.live.region.bean.RegionRecommendInfo;
import com.bilibili.live.region.bean.RegionTypesInfo;
import com.bilibili.live.region.entity.RegionEntity;
import com.bilibili.live.region.mvp.presenter.RegionRecommendPresenter;
import com.bilibili.live.region.mvp.view.IRegionRecommendView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jason on 2018/10/16.
 */

public class RegionTypeRecommendFragment extends RxLazyFragment implements IRegionRecommendView {

    public static RegionTypeRecommendFragment newInstance(boolean isLazyLoad,RegionTypesInfo.DataBean dataBean) {
        Bundle args = new Bundle();
        args.putBoolean(RxLazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        args.putParcelable("DataBean",dataBean);
        RegionTypeRecommendFragment fragment = new RegionTypeRecommendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R2.id.empty_layout)
    protected CustomEmptyView mEmptyView;

    @BindView(R2.id.refreshLayout)
    protected SmartRefreshLayout mRefreshLayout;

    @BindView(R2.id.recyclerView)
    protected RecyclerView mRecyclerView;

    private RegionRecommendAdapter multipleItemAdapter;

    private List<RegionEntity> data;

    private RegionRecommendPresenter recommendPresenter;

    private RegionTypesInfo.DataBean dataBean;

    @Override
    protected int getLayoutResId() {
        return R.layout.region_recommend_layout;
    }

    @Override
    protected void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            dataBean  = bundle.getParcelable("DataBean");
        }
        data = new ArrayList<>();
        recommendPresenter = new RegionRecommendPresenter(this);
        mRefreshLayout.setHeaderHeight(66);
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setHeaderMaxDragRate(2f);
        mRefreshLayout.setEnableNestedScroll(true);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                data.clear();
                multipleItemAdapter.notifyDataSetChanged();
                recommendPresenter.getRegionRecommendsInfo(dataBean.getReid());
            }
        });
        mRefreshLayout.autoRefresh();
        multipleItemAdapter = new RegionRecommendAdapter(data);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        mRecyclerView.setLayoutManager(manager);
        multipleItemAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                int type = data.get(position).getItemType();
                if (type == RegionEntity.VIEW_TYPE_BANNER) {
                    return RegionEntity.BANNER_SPAN_SIZE;
                } else if (type == RegionEntity.VIEW_TYPE_HEADER) {
                    return RegionEntity.HEADER_SPAN_SIZE;
                } else if (type == RegionEntity.VIEW_TYPE_FOOTER) {
                    return RegionEntity.FOOTER_SPAN_SIZE;
                } else if(type == RegionEntity.VIEW_TYPE_SPECIAL_LOADED){
                    return RegionEntity.SPECIAL_LOADED_SPAN_SIZE;
                } else {
                    return RegionEntity.ITEM_LOADED_SPAN_SIZE;
                }
            }
        });
        mRecyclerView.setAdapter(multipleItemAdapter);
    }

    @Override
    public void loadBannerInfo(List<RegionRecommendInfo.DataBean.BannerBean.TopBean> bannerTops) {
        data.add(new RegionEntity(bannerTops) {
            @Override
            public int getItemType() {
                return RegionEntity.VIEW_TYPE_BANNER;
            }
        });
        multipleItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadRecommendInfo(List<RegionRecommendInfo.DataBean.RecommendBean> recommends) {
//        data.add(new RegionEntity(recommends) {
//            @Override
//            public int getItemType() {
//                return RegionEntity.VIEW_TYPE_ITEM_LOADED;
//            }
//        });
//        multipleItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadNewXInfo(List<RegionRecommendInfo.DataBean.NewBean> newXs) {
//        data.add(new RegionEntity(newXs) {
//            @Override
//            public int getItemType() {
//                return RegionEntity.VIEW_TYPE_ITEM_LOADED;
//            }
//        });
//        multipleItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadDynamicInfo(List<RegionRecommendInfo.DataBean.DynamicBean> dynamics) {
//        data.add(new RegionEntity(dynamics) {
//            @Override
//            public int getItemType() {
//                return RegionEntity.VIEW_TYPE_ITEM_LOADED;
//            }
//        });
//        multipleItemAdapter.notifyDataSetChanged();
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
}
