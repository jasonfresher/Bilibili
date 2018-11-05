package com.bilibili.live.region.itemprovider;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bilibili.live.base.constants.ParamsConstant;
import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.region.R;
import com.bilibili.live.region.bean.RegionRecommendInfo;
import com.bilibili.live.region.entity.RegionEntity;
import com.billy.cc.core.component.CC;
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
        final List<RegionRecommendInfo.DataBean.BannerBean.TopBean> content = data.content;
        BGABanner mContentBanner = helper.getView(R.id.banner_guide_content);
        mContentBanner.setAdapter(new BGABanner.Adapter<ImageView, RegionRecommendInfo.DataBean.BannerBean.TopBean>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable RegionRecommendInfo.DataBean.BannerBean.TopBean model, int position) {
                Glide.with(mContext)
                        .load(model.getImage())
                        .placeholder(com.bilibili.live.base.R.drawable.bili_default_image_tv)
                        .error(R.drawable.bili_default_image_tv)
                        .centerCrop()
                        .dontAnimate()
                        .into(itemView);
            }
        });
        if(content.size() > 1)
            mContentBanner.setAutoPlayAble(true);
        mContentBanner.setData(content, null);
        mContentBanner.setDelegate(new BGABanner.Delegate<ImageView,RegionRecommendInfo.DataBean.BannerBean.TopBean>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable RegionRecommendInfo.DataBean.BannerBean.TopBean model, int position) {
                String url = model.getUri();
                String title = model.getTitle();
                CC.obtainBuilder(RouteInfo.BROWSER_COMPONENT_NAME)
                        .addParam(ParamsConstant.EXTRA_URL,url)
                        .addParam(ParamsConstant.EXTRA_TITLE,title)
                        .build()
                        .call();
            }
        });
    }
}
