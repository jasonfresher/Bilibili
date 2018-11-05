package com.bilibili.live.region.itemprovider;

import android.widget.ImageView;

import com.bilibili.live.base.constants.ParamsConstant;
import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.region.R;
import com.bilibili.live.region.bean.RegionDetailsInfo;
import com.bilibili.live.region.entity.RegionEntity;
import com.billy.cc.core.component.CC;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

/**
 * Created by jason on 2018/10/17.
 */

public class DetailsNewsXProvider extends BaseItemProvider<RegionEntity<RegionDetailsInfo.DataBean.NewBean>,BaseViewHolder> {
    @Override
    public int viewType() {
        return RegionEntity.VIEW_TYPE_DETAILS_ITEM_NEWSX_LOADED;
    }

    @Override
    public int layout() {
        return R.layout.item_video_strip;
    }

    @Override
    public void convert(BaseViewHolder helper, RegionEntity<RegionDetailsInfo.DataBean.NewBean> data, int position) {
        RegionDetailsInfo.DataBean.NewBean content = data.content;
        ImageView imageView = helper.getView(R.id.item_img);
        Glide.with(mContext)
                .load(content.getCover())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(imageView);
        helper.setText(R.id.item_title,content.getTitle());
        helper.setText(R.id.item_user_name,content.getName());
        helper.setText(R.id.item_play,String.valueOf(content.getPlay()));
        helper.setText(R.id.item_review,String.valueOf(content.getDanmaku()));
    }

    @Override
    public void onClick(BaseViewHolder helper, RegionEntity<RegionDetailsInfo.DataBean.NewBean> data, int position) {
        RegionDetailsInfo.DataBean.NewBean content = data.content;
        String param = content.getParam();
        String cover = content.getCover();
        CC.obtainBuilder(RouteInfo.VIDEODETAILS_COMPONENT_NAME)
                .addParam(ParamsConstant.EXTRA_AV, Integer.parseInt(param))
                .addParam(ParamsConstant.EXTRA_IMG_URL, cover)
                .build()
                .call();
    }
}
