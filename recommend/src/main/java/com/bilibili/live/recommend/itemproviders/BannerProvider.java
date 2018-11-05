package com.bilibili.live.recommend.itemproviders;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bilibili.live.base.constants.ParamsConstant;
import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.recommend.R;
import com.bilibili.live.recommend.bean.RecommendBannerInfo;
import com.bilibili.live.recommend.entity.RecommendEntity;
import com.billy.cc.core.component.CC;
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
    public void convert(BaseViewHolder helper, final RecommendBannerInfo data, int position) {
        List<RecommendBannerInfo.DataBean> contents = data.getData();
        BGABanner mContentBanner = helper.getView(R.id.banner_guide_content);
        mContentBanner.setAdapter(new BGABanner.Adapter<ImageView, RecommendBannerInfo.DataBean>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable RecommendBannerInfo.DataBean model, int position) {
                Glide.with(mContext)
                        .load(model.getImage())
                        .placeholder(com.bilibili.live.base.R.drawable.bili_default_image_tv)
                        .error(R.drawable.bili_default_image_tv)
                        .centerCrop()
                        .dontAnimate()
                        .into(itemView);
            }
        });
        if(contents.size() > 1)
            mContentBanner.setAutoPlayAble(true);
        mContentBanner.setData(contents, null);
        mContentBanner.setDelegate(new BGABanner.Delegate<ImageView, RecommendBannerInfo.DataBean>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable RecommendBannerInfo.DataBean model, int position) {
                String url = model.getValue();
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
