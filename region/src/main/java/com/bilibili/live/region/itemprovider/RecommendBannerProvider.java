package com.bilibili.live.region.itemprovider;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bilibili.live.region.R;
import com.bilibili.live.region.bean.RegionRecommendInfo;
import com.bilibili.live.region.entity.RegionEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by jason on 2018/9/26.
 */

public class RecommendBannerProvider extends BaseItemProvider<RegionEntity<List<RegionRecommendInfo.DataBean.BannerBean.TopBean>>,BaseViewHolder> {

    @Override
    public int viewType() {
        return RegionEntity.VIEW_TYPE_BANNER;
    }

    @Override
    public int layout() {
        return com.bilibili.live.base.R.layout.layout_banner;
    }

    @Override
    public void convert(BaseViewHolder helper, RegionEntity<List<RegionRecommendInfo.DataBean.BannerBean.TopBean>> data, int position) {
        List<RegionRecommendInfo.DataBean.BannerBean.TopBean> content = data.content;
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
        for (RegionRecommendInfo.DataBean.BannerBean.TopBean bannerBean : content){
            imageUris.add(bannerBean.getImage());
        }
        if(imageUris.size() > 1)
            mContentBanner.setAutoPlayAble(true);
        mContentBanner.setData(imageUris, null);
    }
}
