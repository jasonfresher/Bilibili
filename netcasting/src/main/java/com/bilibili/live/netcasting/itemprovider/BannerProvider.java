package com.bilibili.live.netcasting.itemprovider;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bilibili.live.base.constants.ParamsConstant;
import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.netcasting.R;
import com.bilibili.live.netcasting.bean.LiveAppIndexInfo;
import com.bilibili.live.netcasting.entity.NetcastingEntity;
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
        mContentBanner.setAdapter(new BGABanner.Adapter<ImageView, LiveAppIndexInfo.DataBean.BannerBean>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable LiveAppIndexInfo.DataBean.BannerBean model, int position) {
                Glide.with(mContext)
                        .load(model.getImg())
                        .placeholder(com.bilibili.live.base.R.drawable.bili_default_image_tv)
                        .error(R.drawable.bili_default_image_tv)
                        .centerCrop()
                        .dontAnimate()
                        .into(itemView);
            }
        });
        if(bannerBeans.size() > 1)
            mContentBanner.setAutoPlayAble(true);
        mContentBanner.setData(bannerBeans, null);
        mContentBanner.setDelegate(new BGABanner.Delegate<ImageView,LiveAppIndexInfo.DataBean.BannerBean>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable LiveAppIndexInfo.DataBean.BannerBean model, int position) {
                String url = model.getLink();
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
