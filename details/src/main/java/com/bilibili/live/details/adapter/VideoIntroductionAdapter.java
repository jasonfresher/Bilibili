package com.bilibili.live.details.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bilibili.live.details.R;
import com.bilibili.live.details.bean.VideoDetailsInfo;
import com.bilibili.live.details.utils.NumberUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by jason on 2018/11/1.
 */

public class VideoIntroductionAdapter extends BaseQuickAdapter<VideoDetailsInfo.DataBean.RelatesBean,BaseViewHolder> {


    public VideoIntroductionAdapter(int layoutResId, @Nullable List<VideoDetailsInfo.DataBean.RelatesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoDetailsInfo.DataBean.RelatesBean item) {
        ImageView mVideoPic = helper.getView(R.id.item_img);
        Glide.with(mContext)
                .load(item.getPic())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(mVideoPic);
        helper.setText(R.id.item_title,item.getTitle());
        helper.setText(R.id.item_play, NumberUtil.converString(item.getStat().getView()));
        helper.setText(R.id.item_review, NumberUtil.converString(item.getStat().getDanmaku()));
        helper.setText(R.id.item_user_name, item.getOwner().getName());
    }
}
