package com.bilibili.live.recommend.itemproviders;

import android.content.Intent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.bilibili.live.recommend.R;
import com.bilibili.live.recommend.bean.RecommendInfo;
import com.bilibili.live.recommend.entity.RecommendEntity;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

import java.util.Random;

public class FooterProvider extends BaseItemProvider<RecommendInfo.ResultBean.FootBean,BaseViewHolder> {

    private static final String TYPE_RECOMMENDED = "recommend";

    private static final String TYPE_LIVE = "live";

    private static final String TYPE_BANGUMI = "bangumi_2";

    private static final String TYPE_ACTIVITY = "activity";

    private Random mRandom;

    public FooterProvider() {
        mRandom = new Random();
    }

    @Override
    public int viewType() {
        return RecommendEntity.VIEW_TYPE_FOOTER;
    }

    @Override
    public int layout() {
        return R.layout.recommend_item_footer_layout;
    }

    @Override
    public void convert(BaseViewHolder helper, RecommendInfo.ResultBean.FootBean data, int position) {
        helper.setText(R.id.item_dynamic,String.valueOf(mRandom.nextInt(200)) + "条新动态,点这里刷新");
        helper.getView(R.id.item_btn_refresh).clearAnimation();
        helper.setOnClickListener(R.id.item_btn_refresh, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.animate().rotation(360)
                        .setInterpolator(new LinearInterpolator())
                        .setDuration(1000)
                        .start();
            }
        });
        helper.setOnClickListener(R.id.item_recommend_refresh, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.animate().rotation(360)
                        .setInterpolator(new LinearInterpolator())
                        .setDuration(1000)
                        .start();
            }
        });
        helper.setOnClickListener(R.id.item_btn_bangumi_index, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        helper.setOnClickListener(R.id.item_btn_bangumi_timeline, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        switch (data.getTitleType()) {
            case TYPE_RECOMMENDED:
                helper.setVisible(R.id.item_btn_more,false);
                helper.setVisible( R.id.item_refresh_layout,false);
                helper.setVisible(R.id.item_bangumi_layout,false);
                helper.setVisible(R.id.item_recommend_refresh_layout,true);
                break;
            case TYPE_BANGUMI:
                helper.setVisible(R.id.item_btn_more,false);
                helper.setVisible( R.id.item_refresh_layout,false);
                helper.setVisible(R.id.item_bangumi_layout,true);
                helper.setVisible(R.id.item_recommend_refresh_layout,false);
                break;
            case TYPE_ACTIVITY:
                helper.setVisible(R.id.item_btn_more,false);
                helper.setVisible( R.id.item_refresh_layout,false);
                helper.setVisible(R.id.item_bangumi_layout,false);
                helper.setVisible(R.id.item_recommend_refresh_layout,false);
                break;
            default:
                helper.setVisible(R.id.item_btn_more,true);
                helper.setVisible( R.id.item_refresh_layout,true);
                helper.setVisible(R.id.item_bangumi_layout,false);
                helper.setVisible(R.id.item_recommend_refresh_layout,false);
                break;
        }
    }


}
