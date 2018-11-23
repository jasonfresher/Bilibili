package com.bilibili.live.base.skin;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.bilibili.live.base.R;
import com.bilibili.live.base.widget.CircleProgressView;

import skin.support.content.res.SkinCompatResources;
import skin.support.widget.SkinCompatHelper;
import skin.support.widget.SkinCompatSupportable;

import static skin.support.widget.SkinCompatHelper.INVALID_ID;

/**
 * Created by jason on 2018/11/22.
 */

public class SkinCompatCircleProgressView extends CircleProgressView implements SkinCompatSupportable{

    private int matProgBarColor = INVALID_ID;

    public SkinCompatCircleProgressView(Context context) {
        this(context,null);
    }

    public SkinCompatCircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView, 0, 0);
        matProgBarColor = a.getResourceId(R.styleable.CircleProgressView_matProg_barColor, INVALID_ID);
        a.recycle();
        applyMatProgBarColorResource();
    }

    private void applyMatProgBarColorResource() {
        matProgBarColor = SkinCompatHelper.checkResourceId(matProgBarColor);
        if (matProgBarColor != INVALID_ID) {
            int color = SkinCompatResources.getColor(getContext(), matProgBarColor);
            setBarColor(color);
        }
    }

    @Override
    public void applySkin() {
        applyMatProgBarColorResource();
    }
}
