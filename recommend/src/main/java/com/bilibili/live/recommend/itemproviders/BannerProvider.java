package com.bilibili.live.recommend.itemproviders;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bilibili.live.recommend.R;
import com.bilibili.live.recommend.bean.RecommendBannerInfo;
import com.bilibili.live.recommend.entity.RecommendEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

public class BannerProvider extends BaseItemProvider<RecommendBannerInfo,BaseViewHolder> {
    @Override
    public int viewType() {
        return RecommendEntity.VIEW_TYPE_BANNER;
    }

    @Override
    public int layout() {
        return com.bilibili.live.base.R.layout.layout_banner;
    }

    @Override
    public void convert(BaseViewHolder helper,RecommendBannerInfo data, int position) {
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
        for (RecommendBannerInfo.DataBean content : data.getData()){
            imageUris.add(content.getImage());
        }
        if(imageUris.size() > 1)
            mContentBanner.setAutoPlayAble(true);
        mContentBanner.setData(imageUris, null);
    }
}
