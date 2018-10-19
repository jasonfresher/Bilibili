package com.bilibili.live.region.itemprovider;

import android.widget.ImageView;

import com.bilibili.live.region.R;
import com.bilibili.live.region.bean.RegionRecommendInfo;
import com.bilibili.live.region.entity.RegionEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

/**
 * Created by jason on 2018/10/19.
 */

public class RecommendItemProvider<K> extends BaseItemProvider<RegionEntity<K>,BaseViewHolder> {
    @Override
    public int viewType() {
        return RegionEntity.VIEW_TYPE_ITEM_RECOMMEND_LOADED;
    }

    @Override
    public int layout() {
        return R.layout.layout_region_recommend_card_item;
    }

    @Override
    public void convert(BaseViewHolder helper, RegionEntity<K> data, int position) {
        K content = data.content;
        ImageView imageView = helper.getView(R.id.item_img);
        if(content != null && content instanceof RegionRecommendInfo.DataBean.RecommendBean){
            RegionRecommendInfo.DataBean.RecommendBean recommendBean = (RegionRecommendInfo.DataBean.RecommendBean) content;
            fillContent(helper, imageView, recommendBean.getCover(), recommendBean.getTitle(),
                    String.valueOf(recommendBean.getPlay()),String.valueOf(recommendBean.getDanmaku()));
        }else if(content != null && content instanceof RegionRecommendInfo.DataBean.NewBean){
            RegionRecommendInfo.DataBean.NewBean newBean = (RegionRecommendInfo.DataBean.NewBean) content;
            fillContent(helper, imageView, newBean.getCover(), newBean.getTitle(),
                    String.valueOf(newBean.getPlay()),String.valueOf(newBean.getDanmaku()));
        }else if(content != null && content instanceof RegionRecommendInfo.DataBean.DynamicBean){
            RegionRecommendInfo.DataBean.DynamicBean dynamicBean = (RegionRecommendInfo.DataBean.DynamicBean) content;
            fillContent(helper, imageView, dynamicBean.getCover(), dynamicBean.getTitle(),
                    String.valueOf(dynamicBean.getPlay()),String.valueOf(dynamicBean.getDanmaku()));
        }
    }

    private void fillContent(BaseViewHolder helper, ImageView imageView, String cover, String title, String play,String danmaku) {
        Glide.with(mContext)
                .load(cover)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(imageView);
        helper.setText(R.id.item_title,title);
        helper.setText(R.id.item_play,String.valueOf(play));
        helper.setText(R.id.item_review,String.valueOf(danmaku));
    }

}
