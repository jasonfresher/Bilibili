package com.bilibili.live.recommend.itemproviders;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.bilibili.live.base.constants.ParamsConstant;
import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.recommend.R;
import com.bilibili.live.recommend.bean.RecommendInfo;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponentCallback;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

public class BodyProvider extends BaseItemProvider<RecommendInfo.ResultBean.BodyBean,BaseViewHolder> {

    private static final String TYPE_LIVE = "live";

    private static final String TYPE_BANGUMI = "bangumi_2";

    private static final String TYPE_ACTIVITY = "activity";


    @Override
    public int viewType() {
        return RecommendInfo.ResultBean.BodyBean.VIEW_TYPE_ITEM_LOADED;
    }

    @Override
    public int layout() {
        return R.layout.recommend_item_body_layout;
    }

    @Override
    public void convert(BaseViewHolder helper, final RecommendInfo.ResultBean.BodyBean bodyBean, int position) {
        switch (bodyBean.getTitleType()) {
            case TYPE_LIVE:
                //直播item
                helper.setVisible(R.id.layout_live, true);
                helper.setVisible(R.id.layout_video, false);
                helper.setVisible(R.id.layout_bangumi, false);
                helper.setText(R.id.item_live_online, String.valueOf(bodyBean.getOnline()));
                helper.setText(R.id.item_live_up, bodyBean.getUp());
                break;
            case TYPE_BANGUMI:
                // 番剧item
                helper.setVisible(R.id.layout_live, false);
                helper.setVisible(R.id.layout_video, false);
                helper.setVisible(R.id.layout_bangumi, true);
                helper.setText(R.id.item_bangumi_update,bodyBean.getDesc1());
                break;
            case TYPE_ACTIVITY:
                helper.setVisible(R.id.layout_live, false);
                helper.setVisible(R.id.layout_video, false);
                helper.setVisible(R.id.layout_bangumi, false);
                break;
            default:
                helper.setVisible(R.id.layout_live, false);
                helper.setVisible(R.id.layout_bangumi, false);
                helper.setVisible(R.id.layout_video, true);
                helper.setText(R.id.video_play_num,bodyBean.getPlay());
                helper.setText(R.id.video_review_count,bodyBean.getDanmaku());
                break;
        }
        helper.setText(R.id.video_title,bodyBean.getTitle());
        ImageView videoImg = helper.getView(R.id.video_preview);
        Glide.with(mContext)
                .load(bodyBean.getCover())
                .placeholder(com.bilibili.live.base.R.drawable.bili_default_image_tv)
                .error(R.drawable.bili_default_image_tv)
                .centerCrop()
                .dontAnimate()
                .into(videoImg);
    }

    @Override
    public void onClick(BaseViewHolder helper, RecommendInfo.ResultBean.BodyBean bodyBean, int position) {
        if(!bodyBean.getTitleType().equals("live")) {
            String param = bodyBean.getParam();
            String cover = bodyBean.getCover();
            CC.obtainBuilder(RouteInfo.VIDEODETAILS_COMPONENT_NAME)
                    .addParam(ParamsConstant.EXTRA_AV, Integer.parseInt(param))
                    .addParam(ParamsConstant.EXTRA_IMG_URL, cover)
                    .build()
                    .call();
        }else{

        }
    }
}
