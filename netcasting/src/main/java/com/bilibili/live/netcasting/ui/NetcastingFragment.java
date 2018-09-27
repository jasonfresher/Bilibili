package com.bilibili.live.netcasting.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bilibili.live.base.RxLazyFragment;
import com.bilibili.live.base.widget.CustomEmptyView;
import com.bilibili.live.netcasting.R;
import com.bilibili.live.netcasting.R2;
import com.bilibili.live.netcasting.adapter.NetcastingRvAdapter;
import com.bilibili.live.netcasting.bean.EntranceInfo;
import com.bilibili.live.netcasting.bean.LiveAppIndexInfo;
import com.bilibili.live.netcasting.entity.NetcastingEntity;
import com.bilibili.live.netcasting.mvp.presenter.INetcastingPresenter;
import com.bilibili.live.netcasting.mvp.presenter.NetcastingPresenter;
import com.bilibili.live.netcasting.mvp.view.INetcastingView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jason on 2018/9/25.
 */

public class NetcastingFragment extends RxLazyFragment implements INetcastingView {

    private int[] entranceIconRes = new int[] {
            R.drawable.live_home_follow_anchor,
            R.drawable.live_home_live_center,
            R.drawable.live_home_search_room,
            R.drawable.live_home_all_category
    };

    private String[] entranceTitles = new String[] {
            "关注主播", "直播中心",
            "搜索直播", "全部分类"
    };

    @BindView(R2.id.empty_layout)
    protected CustomEmptyView mEmptyView;

    @BindView(R2.id.refreshLayout)
    protected SmartRefreshLayout mRefreshLayout;

    @BindView(R2.id.recyclerView)
    protected RecyclerView mRecyclerView;

    private INetcastingPresenter presenter;

    private List<NetcastingEntity> datas;

    private NetcastingRvAdapter multipleItemAdapter;



    public static NetcastingFragment newInstance(boolean isLazyLoad) {
        Bundle args = new Bundle();
        args.putBoolean(RxLazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        NetcastingFragment fragment = new NetcastingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.netcasting_fragment_layout;
    }

    @Override
    protected void init() {
        datas = new ArrayList<>();
        presenter = new NetcastingPresenter(this);

        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setHeaderMaxDragRate(2f);
        mRefreshLayout.setEnableNestedScroll(true);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                presenter.getLiveAppIndexInfoData();
            }
        });
        mRefreshLayout.autoRefresh();

        multipleItemAdapter = new NetcastingRvAdapter(datas);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        mRecyclerView.setLayoutManager(manager);
        multipleItemAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                int type = datas.get(position).getItemType();
                if (type == NetcastingEntity.VIEW_TYPE_BANNER) {
                    return NetcastingEntity.BANNER_SPAN_SIZE;
                } else if (type == NetcastingEntity.VIEW_TYPE_HEADER) {
                    return NetcastingEntity.HEADER_SPAN_SIZE;
                } else if (type == NetcastingEntity.VIEW_TYPE_FOOTER) {
                    return NetcastingEntity.FOOTER_SPAN_SIZE;
                } else if(type == NetcastingEntity.VIEW_TYPE_SPECIAL_LOADED){
                    return NetcastingEntity.SPECIAL_LOADED_SPAN_SIZE;
                } else if(type == NetcastingEntity.VIEW_TYPE_ENTRANCE){
                    return NetcastingEntity.ENTRANCE_SPAN_SIZE;
                }else {
                    return NetcastingEntity.ITEM_LOADED_SPAN_SIZE;
                }
            }
        });
        mRecyclerView.setAdapter(multipleItemAdapter);

    }

    @Override
    public void loadNetcastingInfo(LiveAppIndexInfo.DataBean liveDataBean) {
        datas.clear();
        mRefreshLayout.finishRefresh(true);
        List<LiveAppIndexInfo.DataBean.BannerBean> banners = liveDataBean.getBanner();
        NetcastingEntity bannersEntity = new NetcastingEntity<List<LiveAppIndexInfo.DataBean.BannerBean>>(banners) {
            @Override
            public int getItemType() {
                return VIEW_TYPE_BANNER;
            }
        };
        datas.add(bannersEntity);
        List<EntranceInfo> entranceInfos = initEntrance();
        for (EntranceInfo entranceInfo : entranceInfos){
            NetcastingEntity entranceEntity = new NetcastingEntity<EntranceInfo>(entranceInfo) {
                @Override
                public int getItemType() {
                    return NetcastingEntity.VIEW_TYPE_ENTRANCE;
                }
            };
            datas.add(entranceEntity);
        }

        List<LiveAppIndexInfo.DataBean.PartitionsBean> partitions = liveDataBean.getPartitions();
        for (LiveAppIndexInfo.DataBean.PartitionsBean partitionsBean : partitions){
            LiveAppIndexInfo.DataBean.PartitionsBean.PartitionBean partition = partitionsBean.getPartition();
            NetcastingEntity partitionHeader = new NetcastingEntity<LiveAppIndexInfo.DataBean.PartitionsBean.PartitionBean>(partition) {
                @Override
                public int getItemType() {
                    return NetcastingEntity.VIEW_TYPE_HEADER;
                }
            };
            datas.add(partitionHeader);
            List<LiveAppIndexInfo.DataBean.PartitionsBean.LivesBean> lives = partitionsBean.getLives();
            for (LiveAppIndexInfo.DataBean.PartitionsBean.LivesBean livesBean : lives){
                NetcastingEntity partitionEntity = new NetcastingEntity<LiveAppIndexInfo.DataBean.PartitionsBean.LivesBean>(livesBean) {
                    @Override
                    public int getItemType() {
                        return NetcastingEntity.VIEW_TYPE_ITEM_LOADED;
                    }
                };
                datas.add(partitionEntity);
            }
        }
        multipleItemAdapter.notifyDataSetChanged();
    }

    private List<EntranceInfo> initEntrance() {
        List<EntranceInfo> entranceInfos = new ArrayList<>();
        for (int i = 0; i < entranceTitles.length; i++){
            EntranceInfo entranceInfo = new EntranceInfo(entranceTitles[i],entranceIconRes[i]);
            entranceInfos.add(entranceInfo);
        }
        return entranceInfos;
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
