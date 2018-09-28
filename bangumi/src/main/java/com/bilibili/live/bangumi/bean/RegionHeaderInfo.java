package com.bilibili.live.bangumi.bean;

/**
 * Created by jason on 2018/9/28.
 */

public class RegionHeaderInfo {
    public int iconRes;
    public String title;
    public int drawableLeft;
    public String subTitle;

    public RegionHeaderInfo(int iconRes, String title, int drawableLeft, String subTitle) {
        this.iconRes = iconRes;
        this.title = title;
        this.drawableLeft = drawableLeft;
        this.subTitle = subTitle;
    }
}
