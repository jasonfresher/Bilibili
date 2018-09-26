package com.bilibili.live.netcasting.itemprovider;

import com.bilibili.live.netcasting.R;
import com.bilibili.live.netcasting.bean.EntranceInfo;
import com.bilibili.live.netcasting.entity.NetcastingEntity;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

/**
 * Created by jason on 2018/9/26.
 */

public class EntranceProvider extends BaseItemProvider<NetcastingEntity<EntranceInfo>,BaseViewHolder> {
    @Override
    public int viewType() {
        return NetcastingEntity.VIEW_TYPE_ENTRANCE;
    }

    @Override
    public int layout() {
        return R.layout.item_live_entrance;
    }

    @Override
    public void convert(BaseViewHolder helper, NetcastingEntity<EntranceInfo> data, int position) {
        EntranceInfo entranceInfo = data.content;
        helper.setText(R.id.live_entrance_title,entranceInfo.getTitle());
        helper.setImageResource(R.id.live_entrance_image,entranceInfo.getIconRes());
    }
}
