package com.bilibili.live.netcasting.itemprovider;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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

public class PartitionHeadProvider extends BaseItemProvider<NetcastingEntity<LiveAppIndexInfo.DataBean.PartitionsBean.PartitionBean>,BaseViewHolder> {
    @Override
    public int viewType() {
        return NetcastingEntity.VIEW_TYPE_HEADER;
    }

    @Override
    public int layout() {
        return R.layout.item_live_partition_title;
    }

    @Override
    public void convert(BaseViewHolder helper, NetcastingEntity<LiveAppIndexInfo.DataBean.PartitionsBean.PartitionBean> data, int position) {
        LiveAppIndexInfo.DataBean.PartitionsBean.PartitionBean partitionHeadBean = data.content;
        ImageView partitionIcon = helper.getView(R.id.item_live_partition_icon);
        Glide.with(mContext)
                .load(partitionHeadBean.getSub_icon().getSrc())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(partitionIcon);
        helper.setText(R.id.item_live_partition_title,partitionHeadBean.getName());
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(
                "当前" + partitionHeadBean.getCount() + "个直播");
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(
                mContext.getResources().getColor(R.color.pink_text_color));
        stringBuilder.setSpan(foregroundColorSpan, 2,
                stringBuilder.length() - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        helper.setText(R.id.item_live_partition_count,stringBuilder);
    }
}
