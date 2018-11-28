package com.bilibili.live.discovery.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bilibili.live.base.RxLazyFragment;
import com.bilibili.live.discovery.R;
import com.bilibili.live.discovery.R2;
import com.bilibili.live.discovery.bean.HotSearchTag;
import com.bilibili.live.discovery.mvp.presenter.DiscoveryPresenter;
import com.bilibili.live.discovery.mvp.view.IDiscoveryView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jason on 2018/11/28.
 */

public class DiscoveryFragment extends RxLazyFragment<IDiscoveryView,DiscoveryPresenter> implements IDiscoveryView{

    @BindView(R2.id.tags_layout)
    protected TagFlowLayout mTagFlowLayout;

    @BindView(R2.id.hide_tags_layout)
    protected TagFlowLayout mHideTagFlowLayout;

    @BindView(R2.id.hide_scroll_view)
    protected NestedScrollView mNestedScrollView;

    @BindView(R2.id.tv_more)
    protected TextView mMoreText;

    private DiscoveryPresenter presenter;
    private boolean flag;

    public static DiscoveryFragment newInstance(boolean isLazyLoad) {
        Bundle args = new Bundle();
        args.putBoolean(RxLazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        DiscoveryFragment fragment = new DiscoveryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected DiscoveryPresenter createPresenter() {
        presenter = new DiscoveryPresenter();
        return presenter;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void init() {
        presenter.getHotSearchTagData();
    }

    @Override
    public void loadHotSearchTags(List<HotSearchTag.ListBean> hotSearchTags) {
        List<HotSearchTag.ListBean> hideHotsearchTags = hotSearchTags.subList(0, 8);
        mTagFlowLayout.setAdapter(
                new TagAdapter<HotSearchTag.ListBean>(hideHotsearchTags) {
                    @Override
                    public View getView(FlowLayout parent, int position, HotSearchTag.ListBean listBean) {
                        TextView mTags = (TextView) LayoutInflater.from(getActivity())
                                .inflate(R.layout.layout_tags_item, parent, false);
                        mTags.setText(listBean.getKeyword());
                        return mTags;
                    }
                }
        );
        mHideTagFlowLayout.setAdapter(
                new TagAdapter<HotSearchTag.ListBean>(hotSearchTags) {
                    @Override
                    public View getView(FlowLayout parent, int position, HotSearchTag.ListBean listBean) {
                        TextView mTags = (TextView) LayoutInflater.from(getActivity())
                                .inflate(R.layout.layout_tags_item, parent, false);
                        mTags.setText(listBean.getKeyword());
                        return mTags;
                    }
                }
        );
    }

    @OnClick(R2.id.more_layout)
    public void moreLayout(){
        if(flag){
            mTagFlowLayout.setVisibility(View.VISIBLE);
            mNestedScrollView.setVisibility(View.GONE);
            mMoreText.setText("查看更多");
            Drawable downDrawable = getResources().getDrawable(R.drawable.ic_arrow_down_gray_round);
            downDrawable.setBounds(0, 0, downDrawable.getMinimumWidth(), downDrawable.getMinimumHeight());
            mMoreText.setCompoundDrawables(downDrawable, null, null, null);
        }else{
            mTagFlowLayout.setVisibility(View.GONE);
            mNestedScrollView.setVisibility(View.VISIBLE);
            mMoreText.setText("收起");
            Drawable upDrawable = getResources().getDrawable(R.drawable.ic_arrow_up_gray_round);
            upDrawable.setBounds(0, 0, upDrawable.getMinimumWidth(), upDrawable.getMinimumHeight());
            mMoreText.setCompoundDrawables(upDrawable, null, null, null);
        }
        flag = !flag;
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
