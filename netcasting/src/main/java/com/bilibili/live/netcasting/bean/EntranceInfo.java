package com.bilibili.live.netcasting.bean;

/**
 * Created by jason on 2018/9/26.
 */

public class EntranceInfo {
    private String title;
    private int iconRes;

    public EntranceInfo(String title, int iconRes) {
        this.title = title;
        this.iconRes = iconRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }
}
