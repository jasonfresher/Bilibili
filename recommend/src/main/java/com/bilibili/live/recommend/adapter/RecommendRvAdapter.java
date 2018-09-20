package com.bilibili.live.recommend.adapter;

import android.support.annotation.Nullable;

import com.bilibili.live.recommend.entity.RecommendEntity;
import com.bilibili.live.recommend.itemproviders.BannerProvider;
import com.bilibili.live.recommend.itemproviders.BodyProvider;
import com.bilibili.live.recommend.itemproviders.FooterProvider;
import com.bilibili.live.recommend.itemproviders.HeaderProvider;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;

import java.util.List;

public class RecommendRvAdapter extends MultipleItemRvAdapter<RecommendEntity,BaseViewHolder> {

    public RecommendRvAdapter(@Nullable List<RecommendEntity> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(RecommendEntity entity) {
        return entity.getItemType();
    }


    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new BannerProvider());
        mProviderDelegate.registerProvider(new HeaderProvider());
        mProviderDelegate.registerProvider(new BodyProvider());
        mProviderDelegate.registerProvider(new FooterProvider());
    }
}
