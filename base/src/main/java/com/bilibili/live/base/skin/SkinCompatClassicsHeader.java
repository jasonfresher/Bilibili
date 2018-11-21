package com.bilibili.live.base.skin;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.bilibili.live.base.R;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import skin.support.content.res.SkinCompatResources;
import skin.support.widget.SkinCompatHelper;
import skin.support.widget.SkinCompatSupportable;

import static skin.support.widget.SkinCompatHelper.INVALID_ID;

/**
 * Created by jason on 2018/11/21.
 */

public class SkinCompatClassicsHeader extends ClassicsHeader implements SkinCompatSupportable {

    private int mSrlAccentColor = INVALID_ID;
    private int mSrlPrimaryColor = INVALID_ID;

    public SkinCompatClassicsHeader(Context context) {
        this(context,null);
    }

    public SkinCompatClassicsHeader(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SkinCompatClassicsHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClassicsHeader, defStyleAttr, 0);
        mSrlAccentColor = a.getResourceId(R.styleable.ClassicsHeader_srlAccentColor, INVALID_ID);
        mSrlPrimaryColor = a.getResourceId(R.styleable.ClassicsHeader_srlPrimaryColor, INVALID_ID);
        a.recycle();
        applySrlAccentColorResource();
        applySrlPrimaryColorResource();
    }

    private void applySrlAccentColorResource() {
        mSrlAccentColor = SkinCompatHelper.checkResourceId(mSrlAccentColor);
        if (mSrlAccentColor != INVALID_ID) {
            int color = SkinCompatResources.getColor(getContext(), mSrlAccentColor);
            setAccentColor(color);
        }
    }

    private void applySrlPrimaryColorResource() {
        mSrlPrimaryColor = SkinCompatHelper.checkResourceId(mSrlPrimaryColor);
        if (mSrlPrimaryColor != INVALID_ID) {
            int color = SkinCompatResources.getColor(getContext(), mSrlPrimaryColor);
            setPrimaryColor(color);
        }
    }

    @Override
    public ClassicsHeader setAccentColorId(int accentColor) {
        ClassicsHeader classicsHeader = super.setAccentColorId(mSrlAccentColor);
        mSrlAccentColor = accentColor;
        applySkin();
        return classicsHeader;
    }


    @Override
    public ClassicsHeader setPrimaryColorId(int primaryColor) {
        ClassicsHeader classicsHeader = super.setPrimaryColor(mSrlPrimaryColor);
        mSrlPrimaryColor = primaryColor;
        applySkin();
        return classicsHeader;
    }

    @Override
    public void applySkin() {
        applySrlPrimaryColorResource();
        applySrlAccentColorResource();
    }
}
