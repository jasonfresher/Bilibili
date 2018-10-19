package com.bilibili.live.region.itemprovider;

import com.bilibili.live.region.R;
import com.bilibili.live.region.entity.RegionEntity;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

/**
 * Created by jason on 2018/10/17.
 */

public class RecommendNewsXHeaderProvider extends BaseItemProvider {

    @Override
    public int viewType() {
        return RegionEntity.VIEW_TYPE_NEWSX_HEADER;
    }

    @Override
    public int layout() {
        return R.layout.layout_region_recommend_new_head;
    }

    @Override
    public void convert(BaseViewHolder helper, Object data, int position) {

    }
}
