package com.bilibili.live.region.adapter;

import android.support.annotation.Nullable;

import com.bilibili.live.region.entity.RegionEntity;
import com.bilibili.live.region.itemprovider.BannerProvider;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;

import java.util.List;

/**
 * Created by jason on 2018/10/16.
 */

public class RegionRecommendAdapter extends MultipleItemRvAdapter<RegionEntity,BaseViewHolder> {

    public RegionRecommendAdapter(@Nullable List<RegionEntity> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(RegionEntity regionEntity) {
        return regionEntity.getItemType();
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new BannerProvider());
    }
}
