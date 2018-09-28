package com.bilibili.live.bangumi.itemprovider;

import android.view.View;
import android.widget.ImageView;

import com.bilibili.live.bangumi.R;
import com.bilibili.live.bangumi.bean.RegionRecommendInfo;
import com.bilibili.live.bangumi.entity.BangumiEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

/**
 * Created by jason on 2018/9/28.
 */

public class BangumiRecommendProvider<T> extends BaseItemProvider<BangumiEntity<T>,BaseViewHolder> {
    @Override
    public int viewType() {
        return BangumiEntity.VIEW_TYPE_ITEM_LOADED;
    }

    @Override
    public int layout() {
        return R.layout.bangumi_recommend_card_item_layout;
    }

    @Override
    public void convert(BaseViewHolder helper, BangumiEntity<T> data, int position) {
        T t = data.content;
        ImageView mImage = helper.getView(R.id.item_img);
        if(t instanceof RegionRecommendInfo.DataBean.NewBean){
            RegionRecommendInfo.DataBean.NewBean newBean = (RegionRecommendInfo.DataBean.NewBean) t;
            fillContent(helper, mImage ,newBean.getCover(),newBean.getTitle(),
                    String.valueOf(newBean.getPlay()),String.valueOf(newBean.getDanmaku()));
        }else if(t instanceof  RegionRecommendInfo.DataBean.RecommendBean){
            RegionRecommendInfo.DataBean.RecommendBean recommendBean = (RegionRecommendInfo.DataBean.RecommendBean) t;
            fillContent(helper, mImage ,recommendBean.getCover(),recommendBean.getTitle(),
                    String.valueOf(recommendBean.getPlay()),String.valueOf(recommendBean.getDanmaku()));
        }else if(t instanceof  RegionRecommendInfo.DataBean.DynamicBean){
            RegionRecommendInfo.DataBean.DynamicBean dynamicBean = (RegionRecommendInfo.DataBean.DynamicBean) t;
            fillContent(helper, mImage ,dynamicBean.getCover(),dynamicBean.getTitle(),
                    String.valueOf(dynamicBean.getPlay()),String.valueOf(dynamicBean.getDanmaku()));
        }

        helper.setOnClickListener(R.id.card_view, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void fillContent(BaseViewHolder helper, ImageView mImage,String imageUrl,String title,String playNum,String danmaku) {
        Glide.with(mContext)
                .load(imageUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(mImage);
        helper.setText(R.id.item_title,title);
        helper.setText(R.id.item_play,playNum);
        helper.setText(R.id.item_review,danmaku);
    }

}
