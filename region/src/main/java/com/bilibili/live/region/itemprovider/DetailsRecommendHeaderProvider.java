package com.bilibili.live.region.itemprovider;

import com.bilibili.live.region.R;
import com.bilibili.live.region.entity.RegionEntity;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

/**
 * Created by jason on 2018/10/17.
 */

public class DetailsRecommendHeaderProvider extends BaseItemProvider<RegionEntity<String>,BaseViewHolder> {

    @Override
    public int viewType() {
        return RegionEntity.VIEW_TYPE_DETAILS_RECOMMEDN_HEADER;
    }

    @Override
    public int layout() {
        return R.layout.layout_region_details_hot_head;
    }

    @Override
    public void convert(BaseViewHolder helper, RegionEntity<String> data, int position) {
        String content = data.content;
        helper.setText(R.id.item_type_tv,content);
    }

}
