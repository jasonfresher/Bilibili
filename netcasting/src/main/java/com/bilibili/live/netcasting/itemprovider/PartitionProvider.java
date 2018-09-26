package com.bilibili.live.netcasting.itemprovider;

import android.widget.ImageView;

import com.bilibili.live.netcasting.R;
import com.bilibili.live.netcasting.bean.LiveAppIndexInfo;
import com.bilibili.live.netcasting.entity.NetcastingEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

/**
 * Created by jason on 2018/9/26.
 */

public class PartitionProvider extends BaseItemProvider<NetcastingEntity<LiveAppIndexInfo.DataBean.PartitionsBean.LivesBean>,BaseViewHolder> {
    @Override
    public int viewType() {
        return NetcastingEntity.VIEW_TYPE_ITEM_LOADED;
    }

    @Override
    public int layout() {
        return R.layout.item_live_partition;
    }

    @Override
    public void convert(BaseViewHolder helper, NetcastingEntity<LiveAppIndexInfo.DataBean.PartitionsBean.LivesBean> data, int position) {
        LiveAppIndexInfo.DataBean.PartitionsBean.LivesBean livesBean = data.content;
        ImageView liveCover = helper.getView(R.id.item_live_cover);
        ImageView liveUserCover = helper.getView(R.id.item_live_user_cover);
        Glide.with(mContext)
                .load(livesBean.getCover().getSrc())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(liveCover);
        Glide.with(mContext)
                .load(livesBean.getOwner().getFace())
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.ico_user_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(liveUserCover);
        helper.setText(R.id.item_live_title,livesBean.getTitle());
        helper.setText(R.id.item_live_user,livesBean.getOwner().getName());
        helper.setText(R.id.item_live_count,String.valueOf(livesBean.getOnline()));
    }

}
