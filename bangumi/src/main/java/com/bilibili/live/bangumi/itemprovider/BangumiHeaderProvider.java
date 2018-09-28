package com.bilibili.live.bangumi.itemprovider;

import android.text.TextUtils;
import android.widget.TextView;

import com.bilibili.live.bangumi.R;
import com.bilibili.live.bangumi.bean.RegionHeaderInfo;
import com.bilibili.live.bangumi.entity.BangumiEntity;
import com.bilibili.live.base.application.BilibiliApp;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

/**
 * Created by jason on 2018/9/27.
 */

public class BangumiHeaderProvider extends BaseItemProvider<BangumiEntity<RegionHeaderInfo>,BaseViewHolder> {
    @Override
    public int viewType() {
        return BangumiEntity.VIEW_TYPE_HEADER;
    }

    @Override
    public int layout() {
        return R.layout.bangumi_recommend_header_layout;
    }

    @Override
    public void convert(BaseViewHolder helper, BangumiEntity<RegionHeaderInfo> data, int position) {
        RegionHeaderInfo headerInfo = data.content;
        helper.setImageResource(R.id.item_type_img,headerInfo.iconRes);
        helper.setText(R.id.item_type_tv,headerInfo.title);
        if(!TextUtils.isEmpty(headerInfo.subTitle)){
            helper.setVisible(R.id.item_type_rank_btn,true);
            TextView subTv = helper.getView(R.id.item_type_rank_btn);
            if(headerInfo.drawableLeft > 0)
                subTv.setCompoundDrawablesWithIntrinsicBounds(BilibiliApp.mInstance.getResources().getDrawable(headerInfo.drawableLeft),null,null,null);
            helper.setText(R.id.item_type_rank_btn,headerInfo.subTitle);
        }else{
            helper.setVisible(R.id.item_type_rank_btn,false);
        }
    }
}
