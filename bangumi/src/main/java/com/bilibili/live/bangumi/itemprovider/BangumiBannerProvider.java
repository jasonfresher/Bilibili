package com.bilibili.live.bangumi.itemprovider;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bilibili.live.bangumi.R;
import com.bilibili.live.bangumi.bean.RegionRecommendInfo;
import com.bilibili.live.bangumi.entity.BangumiEntity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by jason on 2018/9/27.
 */

public class BangumiBannerProvider extends BaseItemProvider<BangumiEntity<List<RegionRecommendInfo.DataBean.BannerBean.TopBean>>,BaseViewHolder> {
    @Override
    public int viewType() {
        return BangumiEntity.VIEW_TYPE_BANNER;
    }

    @Override
    public int layout() {
        return com.bilibili.live.base.R.layout.layout_banner;
    }

    @Override
    public void convert(BaseViewHolder helper, BangumiEntity<List<RegionRecommendInfo.DataBean.BannerBean.TopBean>> data, int position) {
        List<RegionRecommendInfo.DataBean.BannerBean.TopBean> datas = data.content;
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
        for (RegionRecommendInfo.DataBean.BannerBean.TopBean content : datas){
            imageUris.add(content.getImage());
        }
        if(imageUris.size() > 1)
            mContentBanner.setAutoPlayAble(true);
        mContentBanner.setData(imageUris, null);
    }
}
