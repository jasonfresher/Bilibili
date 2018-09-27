package com.bilibili.live.bangumi.itemprovider;

import com.bilibili.live.bangumi.R;
import com.bilibili.live.bangumi.entity.BangumiEntity;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

/**
 * Created by jason on 2018/9/27.
 */

public class BangumiHeaderProvider extends BaseItemProvider<BangumiEntity<Integer>,BaseViewHolder> {
    @Override
    public int viewType() {
        return BangumiEntity.VIEW_TYPE_HEADER;
    }

    @Override
    public int layout() {
        return R.layout.bangumi_recommend_header_layout;
    }

    @Override
    public void convert(BaseViewHolder helper, BangumiEntity<Integer> data, int position) {
        int rid = data.content;
        if (rid == 165) {
            helper.setVisible(R.id.item_type_rank_btn_1,false);
        } else {

        }
    }
}
