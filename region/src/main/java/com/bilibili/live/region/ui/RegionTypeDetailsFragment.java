package com.bilibili.live.region.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bilibili.live.base.RxLazyFragment;
import com.bilibili.live.base.constants.ParamsConstant;
import com.bilibili.live.base.mvp.BasePresenter;
import com.bilibili.live.base.widget.CircleProgressView;
import com.bilibili.live.region.R;
import com.bilibili.live.region.R2;
import com.bilibili.live.region.adapter.RegionDetailsAdapter;
import com.bilibili.live.region.bean.RegionDetailsInfo;
import com.bilibili.live.region.entity.RegionEntity;
import com.bilibili.live.region.mvp.presenter.RegionDetailsPresenter;
import com.bilibili.live.region.mvp.view.IRegionDetailsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by jason on 2018/10/17.
 */

public class RegionTypeDetailsFragment extends RxLazyFragment<IRegionDetailsView,BasePresenter<IRegionDetailsView>> implements IRegionDetailsView {

    public static RegionTypeDetailsFragment newInstance(boolean isLazyLoad, int rid) {
        Bundle args = new Bundle();
        args.putBoolean(RxLazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        args.putInt(ParamsConstant.EXTRA_RID,rid);
        RegionTypeDetailsFragment fragment = new RegionTypeDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private int rid;

    @BindView(R2.id.recycle)
    RecyclerView mRecyclerView;

    @BindView(R2.id.circle_progress)
    CircleProgressView mCircleProgressView;

    private List<RegionEntity> datas;

    private RegionDetailsAdapter multipleItemAdapter;

    private RegionDetailsPresenter presenter;

    @Override
    protected BasePresenter createPresenter() {
        presenter = new RegionDetailsPresenter();
        return presenter;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_region_details;
    }

    @Override
    protected void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            rid  = bundle.getInt(ParamsConstant.EXTRA_RID);
        }
        datas = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        multipleItemAdapter = new RegionDetailsAdapter(datas);
        mRecyclerView.setAdapter(multipleItemAdapter);
        presenter.getRegionDetailsInfo(rid);
    }

    @Override
    public void showProgress() {
        mCircleProgressView.setVisibility(View.VISIBLE);
        mCircleProgressView.spin();
    }

    @Override
    public void hideProgress() {
        mCircleProgressView.setVisibility(View.GONE);
        mCircleProgressView.stopSpinning();
    }

    @Override
    public void errorCallback(Throwable throwable) {

    }

    @Override
    public void loadRecommendInfo(List<RegionDetailsInfo.DataBean.RecommendBean> recommends) {
        datas.add(new RegionEntity("最热视频") {
            @Override
            public int getItemType() {
                return RegionEntity.VIEW_TYPE_DETAILS_RECOMMEDN_HEADER;
            }
        });

        Observable.fromIterable(recommends)
                .map(new Function<RegionDetailsInfo.DataBean.RecommendBean, RegionEntity<RegionDetailsInfo.DataBean.RecommendBean>>() {
                    @Override
                    public RegionEntity<RegionDetailsInfo.DataBean.RecommendBean> apply(RegionDetailsInfo.DataBean.RecommendBean recommendBean) throws Exception {
                        return new RegionEntity(recommendBean) {
                            @Override
                            public int getItemType() {
                                return RegionEntity.VIEW_TYPE_DETAILS_ITEM_RECOMMEND_LOADED;
                            }
                        };
                    }
                })
                .toList()
                .subscribe(new Consumer<List<RegionEntity<RegionDetailsInfo.DataBean.RecommendBean>>>() {
                    @Override
                    public void accept(List<RegionEntity<RegionDetailsInfo.DataBean.RecommendBean>> regionEntities) throws Exception {
                        datas.addAll(regionEntities);
                        multipleItemAdapter.notifyDataSetChanged();
                    }
                });

    }

    @Override
    public void loadNewXInfo(List<RegionDetailsInfo.DataBean.NewBean> newXs) {
        datas.add(new RegionEntity("最新视频") {
            @Override
            public int getItemType() {
                return RegionEntity.VIEW_TYPE_DETAILS_RECOMMEDN_HEADER;
            }
        });
        Observable.fromIterable(newXs)
                .map(new Function<RegionDetailsInfo.DataBean.NewBean, RegionEntity<RegionDetailsInfo.DataBean.NewBean>>() {
                    @Override
                    public RegionEntity<RegionDetailsInfo.DataBean.NewBean> apply(RegionDetailsInfo.DataBean.NewBean newsBean) throws Exception {
                        return new RegionEntity(newsBean) {
                            @Override
                            public int getItemType() {
                                return RegionEntity.VIEW_TYPE_DETAILS_ITEM_NEWSX_LOADED;
                            }
                        };
                    }
                })
                .toList()
                .subscribe(new Consumer<List<RegionEntity<RegionDetailsInfo.DataBean.NewBean>>>() {
                    @Override
                    public void accept(List<RegionEntity<RegionDetailsInfo.DataBean.NewBean>> regionEntities) throws Exception {
                        datas.addAll(regionEntities);
                        multipleItemAdapter.notifyDataSetChanged();
                    }
                });

    }
}
