package com.bilibili.live.recommend.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bilibili.live.recommend.R;
import com.bilibili.live.recommend.bean.RecommendInfo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by jason on 2018/9/21.
 */

public class ActivityCenterRecyclerAdapter extends BaseQuickAdapter<RecommendInfo.ResultBean.BodyBean,BaseViewHolder> {


    public ActivityCenterRecyclerAdapter(int layoutResId, @Nullable List<RecommendInfo.ResultBean.BodyBean> datas) {
        super(layoutResId, datas);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendInfo.ResultBean.BodyBean data) {
        ImageView mVideoImg = helper.getView(R.id.video_preview);
        Glide.with(mContext)
                .load(data.getCover())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(mVideoImg);

        helper.setText(R.id.video_title,data.getTitle());
    }
}
