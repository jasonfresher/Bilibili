package com.bilibili.live.base.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilibili.live.base.R;

/**
 * Created by jason on 2018/9/18.
 */

public class CustomEmptyView extends FrameLayout {

    private ImageView mEmptyImg;

    private TextView mEmptyText;

    public CustomEmptyView(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomEmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty, this);
        mEmptyImg = (ImageView) view.findViewById(R.id.empty_img);
        mEmptyText = (TextView) view.findViewById(R.id.empty_text);
    }


    public void setEmptyImage(int imgRes) {

        mEmptyImg.setImageResource(imgRes);
    }


    public void setEmptyText(String text) {

        mEmptyText.setText(text);
    }
}
