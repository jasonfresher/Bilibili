package com.bilibili.live.recommend.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by jason on 2018/9/19.
 */

public class RecommendMultiItem<T> implements MultiItemEntity {

    public static final int VIEW_TYPE_BANNER = 1;
    public static final int VIEW_TYPE_HEADER = 2;
    public static final int VIEW_TYPE_FOOTER = 3;
    public static final int VIEW_TYPE_ITEM_LOADED = 4;

    private int itemType;
    private int spanSize;
    private T content;


    public RecommendMultiItem(int itemType, int spanSize, T t) {
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.content = t;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
