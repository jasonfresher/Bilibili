package com.bilibili.live.recommend.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.bilibili.live.base.RxLazyFragment;
import com.bilibili.live.recommend.R;
import com.bilibili.live.recommend.bean.RecommendBannerInfo;
import com.bilibili.live.recommend.bean.RecommendInfo;
import com.bilibili.live.recommend.mvp.presenter.IRecommendPresenter;
import com.bilibili.live.recommend.mvp.presenter.RecommendPresenterImpl;
import com.bilibili.live.recommend.mvp.view.IRecommendView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jason on 2018/9/14.
 */

public class RecommendFragment extends RxLazyFragment implements IRecommendView {

    private IRecommendPresenter recommendPresenter;

    @BindView(R.id.refreshLayout)
    protected SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;

    @Override
    public int getLayoutResId() {
        return R.layout.recommend_fragment_layout;
    }


    @Override
    public void loadRecommendInfo(List<RecommendBannerInfo.DataBean> mBaseBanners, List<RecommendInfo.ResultBean> results) {
        System.out.println("@@@@@@@@========>" + mBaseBanners.get(0).getImage());
        System.out.println("@@@@@@@@========>" + results.get(0).getBody().get(0).getCover());
    }




    @Override
    public void finishCreateView(Bundle state) {
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void loadData() {
        recommendPresenter = new RecommendPresenterImpl(this);
        recommendPresenter.getHomeRecommendData();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        loadData();
        isPrepared = false;
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
