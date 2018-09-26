package com.bilibili.live.netcasting.adapter;

import android.support.annotation.Nullable;

import com.bilibili.live.netcasting.entity.NetcastingEntity;
import com.bilibili.live.netcasting.itemprovider.BannerProvider;
import com.bilibili.live.netcasting.itemprovider.EntranceProvider;
import com.bilibili.live.netcasting.itemprovider.PartitionHeadProvider;
import com.bilibili.live.netcasting.itemprovider.PartitionProvider;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;

import java.util.List;

/**
 * Created by jason on 2018/9/26.
 */

public class NetcastingRvAdapter extends MultipleItemRvAdapter<NetcastingEntity,BaseViewHolder> {

    public NetcastingRvAdapter(@Nullable List<NetcastingEntity> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(NetcastingEntity netcastingEntity) {
        return netcastingEntity.getItemType();
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new BannerProvider());
        mProviderDelegate.registerProvider(new EntranceProvider());
        mProviderDelegate.registerProvider(new PartitionProvider());
        mProviderDelegate.registerProvider(new PartitionHeadProvider());
    }
}
