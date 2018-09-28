package com.bilibili.live.bangumi.adapter;

import android.support.annotation.Nullable;

import com.bilibili.live.bangumi.entity.BangumiEntity;
import com.bilibili.live.bangumi.itemprovider.BangumiBannerProvider;
import com.bilibili.live.bangumi.itemprovider.BangumiHeaderProvider;
import com.bilibili.live.bangumi.itemprovider.BangumiRecommendProvider;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;

import java.util.List;

/**
 * Created by jason on 2018/9/27.
 */

public class BangumiRvAdapter extends MultipleItemRvAdapter<BangumiEntity,BaseViewHolder> {

    public BangumiRvAdapter(@Nullable List<BangumiEntity> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(BangumiEntity bangumiEntity) {
        return bangumiEntity.getItemType();
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new BangumiBannerProvider());
        mProviderDelegate.registerProvider(new BangumiHeaderProvider());
        mProviderDelegate.registerProvider(new BangumiRecommendProvider());
    }
}
