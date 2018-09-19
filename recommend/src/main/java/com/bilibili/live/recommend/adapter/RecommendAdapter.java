package com.bilibili.live.recommend.adapter;


import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bilibili.live.recommend.R;
import com.bilibili.live.recommend.bean.RecommendBannerInfo;
import com.bilibili.live.recommend.bean.RecommendInfo;
import com.bilibili.live.recommend.entity.RecommendMultiItem;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by jason on 2018/9/19.
 */

public class RecommendAdapter extends BaseMultiItemQuickAdapter<RecommendMultiItem,BaseViewHolder> {

    private Context mContext;

    public RecommendAdapter(Context context, List<RecommendMultiItem> data) {
        super(data);
        mContext = context;
        addItemType(RecommendMultiItem.VIEW_TYPE_BANNER, com.bilibili.live.base.R.layout.layout_banner);
        addItemType(RecommendMultiItem.VIEW_TYPE_HEADER, R.layout.recommend_item_header_layout);
        addItemType(RecommendMultiItem.VIEW_TYPE_FOOTER, R.layout.recommend_item_footer_layout);
        addItemType(RecommendMultiItem.VIEW_TYPE_ITEM_LOADED, R.layout.recommend_item_body_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendMultiItem item) {
        switch (helper.getItemViewType()) {
            case RecommendMultiItem.VIEW_TYPE_BANNER:
                BGABanner mContentBanner = helper.getView(R.id.banner_guide_content);
                mContentBanner.setAutoPlayAble(true);
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
                for (RecommendBannerInfo.DataBean content : (List<RecommendBannerInfo.DataBean>)item.getContent()){
                    imageUris.add(content.getImage());
                }
                mContentBanner.setData(imageUris, null);
                break;
            case RecommendMultiItem.VIEW_TYPE_HEADER:
                RecommendInfo.ResultBean.HeadBean headerBean = (RecommendInfo.ResultBean.HeadBean) item.getContent();
                helper.setText(R.id.item_type_tv,headerBean.getTitle());
                helper.getView(R.id.item_type_rank_btn).setVisibility(View.VISIBLE);
                helper.setImageResource(R.id.item_type_img,R.drawable.ic_watching);
                break;
            case RecommendMultiItem.VIEW_TYPE_ITEM_LOADED:
                RecommendInfo.ResultBean.BodyBean bodyBean = (RecommendInfo.ResultBean.BodyBean) item.getContent();
                helper.setText(R.id.video_title,bodyBean.getTitle());
                helper.setText(R.id.video_play_num,bodyBean.getPlay());
                helper.setText(R.id.video_review_count,bodyBean.getDanmaku());
                ImageView videoImg = helper.getView(R.id.video_preview);
                Glide.with(mContext)
                        .load(bodyBean.getCover())
                        .placeholder(com.bilibili.live.base.R.drawable.bili_default_image_tv)
                        .error(R.drawable.bili_default_image_tv)
                        .centerCrop()
                        .dontAnimate()
                        .into(videoImg);

                break;
            case RecommendMultiItem.VIEW_TYPE_FOOTER:
                break;
            default:
        }
    }
}
