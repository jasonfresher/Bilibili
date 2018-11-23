package com.bilibili.live.base.skin;

import android.content.Context;
import android.util.AttributeSet;

import com.flyco.tablayout.SlidingTabLayout;

import skin.support.widget.SkinCompatBackgroundHelper;
import skin.support.widget.SkinCompatSupportable;

/**
 * Created by jason on 2018/11/22.
 */

public class SkinCompatSlidingTabLayout extends SlidingTabLayout implements SkinCompatSupportable {

    private SkinCompatBackgroundHelper mBackgroundTintHelper;

    public SkinCompatSlidingTabLayout(Context context) {
        this(context,null);
    }

    public SkinCompatSlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SkinCompatSlidingTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    @Override
    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(resId);
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.onSetBackgroundResource(resId);
        }
    }

    @Override
    public void applySkin() {
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.applySkin();
        }
    }
}
