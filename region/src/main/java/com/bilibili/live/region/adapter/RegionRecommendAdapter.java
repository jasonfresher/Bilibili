package com.bilibili.live.region.adapter;

import android.support.annotation.Nullable;

import com.bilibili.live.region.entity.RegionEntity;
import com.bilibili.live.region.itemprovider.RecommendDynamicHeaderProvider;
import com.bilibili.live.region.itemprovider.RecommendHotHeaderProvider;
import com.bilibili.live.region.itemprovider.RecommendNewsXHeaderProvider;
import com.bilibili.live.region.itemprovider.DetailsRecommendHeaderProvider;
import com.bilibili.live.region.itemprovider.RecommendBannerProvider;
import com.bilibili.live.region.itemprovider.RecommendItemProvider;
import com.bilibili.live.region.itemprovider.RecommendTypeProvider;
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
        mProviderDelegate.registerProvider(new RecommendBannerProvider());
        mProviderDelegate.registerProvider(new RecommendTypeProvider());
        mProviderDelegate.registerProvider(new RecommendHotHeaderProvider());
        mProviderDelegate.registerProvider(new RecommendNewsXHeaderProvider());
        mProviderDelegate.registerProvider(new RecommendDynamicHeaderProvider());
        mProviderDelegate.registerProvider(new RecommendItemProvider());
    }
}
