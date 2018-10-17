package com.bilibili.live.region.adapter;

import android.support.annotation.Nullable;

import com.bilibili.live.region.R;
import com.bilibili.live.region.bean.RegionHomeItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by jason on 2018/10/15.
 */

public class RegionHomeAdapter extends BaseQuickAdapter<RegionHomeItemBean,BaseViewHolder> {

    public RegionHomeAdapter(int layoutResId, @Nullable List<RegionHomeItemBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, RegionHomeItemBean item) {
        helper.setText(R.id.item_title,item.getName());
        helper.setImageResource(R.id.item_icon,item.getIconRes());
    }
}
