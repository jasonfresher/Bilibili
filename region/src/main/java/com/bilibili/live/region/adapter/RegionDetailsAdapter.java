package com.bilibili.live.region.adapter;

import android.support.annotation.Nullable;

import com.bilibili.live.region.entity.RegionEntity;
import com.bilibili.live.region.itemprovider.DetailsHeaderProvider;
import com.bilibili.live.region.itemprovider.DetailsNewsXProvider;
import com.bilibili.live.region.itemprovider.DetailsRecommendProvider;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;

import java.util.List;

/**
 * Created by jason on 2018/10/17.
 */

public class RegionDetailsAdapter extends MultipleItemRvAdapter<RegionEntity,BaseViewHolder> {

    public RegionDetailsAdapter(@Nullable List<RegionEntity> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(RegionEntity regionEntity) {
        return regionEntity.getItemType();
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new DetailsHeaderProvider());
        mProviderDelegate.registerProvider(new DetailsRecommendProvider());
        mProviderDelegate.registerProvider(new DetailsNewsXProvider());
    }
}
