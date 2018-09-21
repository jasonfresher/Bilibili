package com.bilibili.live.recommend.itemproviders;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bilibili.live.recommend.R;
import com.bilibili.live.recommend.adapter.ActivityCenterRecyclerAdapter;
import com.bilibili.live.recommend.bean.RecommendInfo;
import com.bilibili.live.recommend.entity.RecommendEntity;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

import java.util.List;

/**
 * Created by jason on 2018/9/21.
 */

public class SpecialProvider extends BaseItemProvider<List<RecommendInfo.ResultBean.BodyBean>,BaseViewHolder> {
    @Override
    public int viewType() {
        return RecommendEntity.VIEW_TYPE_SPECIAL_LOADED;
    }

    @Override
    public int layout() {
        return R.layout.layout_home_recommend_activitycenter;
    }

    @Override
    public void convert(BaseViewHolder helper, List<RecommendInfo.ResultBean.BodyBean> datas, int position) {
        RecyclerView mRecyclerView = helper.getView(R.id.recycle);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(new ActivityCenterRecyclerAdapter(R.layout.recommend_item_body_layout,datas));
    }
}
