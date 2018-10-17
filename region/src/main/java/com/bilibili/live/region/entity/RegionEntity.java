package com.bilibili.live.region.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by jason on 2018/10/16.
 */

public abstract class RegionEntity<T> implements MultiItemEntity {

    public static final int VIEW_TYPE_BANNER = 1;
    public static final int VIEW_TYPE_HEADER = 2;
    public static final int VIEW_TYPE_FOOTER = 3;
    public static final int VIEW_TYPE_ITEM_RECOMMEND_LOADED = 4;
    public static final int VIEW_TYPE_ITEM_NEWSX_LOADED = 5;
    public static final int VIEW_TYPE_SPECIAL_LOADED = 6;
    public static  final int VIEW_TYPE_ENTRANCE = 7;

    public static final int BANNER_SPAN_SIZE = 4;
    public static final int HEADER_SPAN_SIZE = 4;
    public static final int FOOTER_SPAN_SIZE = 4;
    public static final int ITEM_LOADED_SPAN_SIZE = 2;
    public static final int SPECIAL_LOADED_SPAN_SIZE = 4;
    public static final int ENTRANCE_SPAN_SIZE = 4;

    public T content;

    public RegionEntity(T content) {
        this.content = content;
    }

}