package com.bilibili.live.recommend.itemproviders;

import android.view.View;
import android.view.ViewGroup;

import com.bilibili.live.recommend.R;
import com.bilibili.live.recommend.bean.RecommendInfo;
import com.bilibili.live.recommend.entity.RecommendEntity;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

public class HeaderProvider extends BaseItemProvider<RecommendInfo.ResultBean.HeadBean,BaseViewHolder> {

    private static final String TYPE_LIVE = "live";

    private static final String TYPE_BANGUMI = "bangumi_2";

    private static final String GOTO_BANGUMI = "bangumi_list";

    private static final String TYPE_ACTIVITY = "activity";


    @Override
    public int viewType() {
        return RecommendEntity.VIEW_TYPE_HEADER;
    }

    @Override
    public int layout() {
        return R.layout.recommend_item_header_layout;
    }

    @Override
    public void convert(BaseViewHolder helper, RecommendInfo.ResultBean.HeadBean data, int position) {
        switch (data.getTitleType()) {
            case TYPE_LIVE:
                //直播item
                itemViewHolder.mLiveLayout.setVisibility(View.VISIBLE);
                itemViewHolder.mVideoLayout.setVisibility(View.GONE);
                itemViewHolder.mBangumiLayout.setVisibility(View.GONE);
                itemViewHolder.mLiveUp.setText(bodyBean.getUp());
                itemViewHolder.mLiveOnline.setText(String.valueOf(bodyBean.getOnline()));
                break;
            case TYPE_BANGUMI:
                // 番剧item
                itemViewHolder.mLiveLayout.setVisibility(View.GONE);
                itemViewHolder.mVideoLayout.setVisibility(View.GONE);
                itemViewHolder.mBangumiLayout.setVisibility(View.VISIBLE);
                itemViewHolder.mBangumiUpdate.setText(bodyBean.getDesc1());
                break;
            case TYPE_ACTIVITY:
                ViewGroup.LayoutParams layoutParams = itemViewHolder.mCardView.getLayoutParams();
                layoutParams.height = DisplayUtil.dp2px(mContext, 200f);
                itemViewHolder.mCardView.setLayoutParams(layoutParams);
                itemViewHolder.mLiveLayout.setVisibility(View.GONE);
                itemViewHolder.mVideoLayout.setVisibility(View.GONE);
                itemViewHolder.mBangumiLayout.setVisibility(View.GONE);
                break;
            default:
                itemViewHolder.mLiveLayout.setVisibility(View.GONE);
                itemViewHolder.mBangumiLayout.setVisibility(View.GONE);
                itemViewHolder.mVideoLayout.setVisibility(View.VISIBLE);
                itemViewHolder.mVideoPlayNum.setText(bodyBean.getPlay());
                itemViewHolder.mVideoReviewCount.setText(bodyBean.getDanmaku());
                break;

            helper.setText(R.id.item_type_tv, data.getTitle());
            helper.getView(R.id.item_type_rank_btn).setVisibility(View.VISIBLE);
            helper.setImageResource(R.id.item_type_img, R.drawable.ic_watching);
        }
    }
}
