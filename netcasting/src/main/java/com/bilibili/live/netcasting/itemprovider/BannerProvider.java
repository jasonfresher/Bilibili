package com.bilibili.live.netcasting.itemprovider;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bilibili.live.netcasting.R;
import com.bilibili.live.netcasting.bean.LiveAppIndexInfo;
import com.bilibili.live.netcasting.entity.NetcastingEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by jason on 2018/9/26.
 */

public class BannerProvider extends BaseItemProvider<NetcastingEntity<List<LiveAppIndexInfo.DataBean.BannerBean>>,BaseViewHolder> {

    @Override
    public int viewType() {
        return NetcastingEntity.VIEW_TYPE_BANNER;
    }

    @Override
    public int layout() {
        return com.bilibili.live.base.R.layout.layout_banner;
    }

    @Override
    public void convert(BaseViewHolder helper, NetcastingEntity<List<LiveAppIndexInfo.DataBean.BannerBean>> data, int position) {
        List<LiveAppIndexInfo.DataBean.BannerBean> bannerBeans = data.content;
        BGABanner mContentBanner = helper.getView(R.id.banner_guide_content);
        mContentBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
                Glide.with(mContext)
                        .load(model)
                        .placeholder(com.bilibili.live.base.R.drawable.bili_default_image_tv)
                        .error(R.drawable.bili_default_image_tv)
                        .centerCrop()
                        .dontAnimate()
                        .into(itemView);
            }
        });
        List<String> imageUris = new ArrayList<>();
        for (LiveAppIndexInfo.DataBean.BannerBean bannerBean : bannerBeans){
            imageUris.add(bannerBean.getImg());
        }
        if(imageUris.size() > 1)
            mContentBanner.setAutoPlayAble(true);
        mContentBanner.setData(imageUris, null);
    }
}
