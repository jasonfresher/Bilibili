package com.bilibili.live.player.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilibili.live.player.R;

/**
 * Created by jason on 2018/10/29.
 */

public class ShowChangeLayout extends RelativeLayout {
    private ImageView mCenter;
    private ProgressBar mPb;
    private HideRunnable mHideRunnable;
    private int mDelayTime = 1000;
    private TextView mCurrentTime;
    private TextView mDurationTime;

    public ShowChangeLayout(Context context) {
        super(context);
        init(context);
    }

    public ShowChangeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_show_change,this);
        mCenter = findViewById(R.id.iv_center);
        mPb = findViewById(R.id.pb);
        mCurrentTime = findViewById(R.id.current_time);
        mDurationTime = findViewById(R.id.duration_time);
        mHideRunnable = new HideRunnable();
        setVisibility(GONE);
    }

    //显示
    public void show(){
        setVisibility(VISIBLE);
        removeCallbacks(mHideRunnable);
        postDelayed(mHideRunnable,mDelayTime);
    }

    //设置进度
    public void setProgress(int progress){
        mPb.setProgress(progress);
    }

    //设置持续时间
    public void setDelayTime(int delayTime) {
        mDelayTime = delayTime;
    }

    public void setCurrentTime(String currentTime){
        mCurrentTime.setText(currentTime);
    }

    public void setDurationTime(String durationTime){
        mDurationTime.setText(durationTime);
    }

    //设置显示图片
    public void setImageResource(int resource){
        mCenter.setImageResource(resource);
    }

    //隐藏自己的Runnable
    private class HideRunnable implements Runnable{
        @Override
        public void run() {
            ShowChangeLayout.this.setVisibility(GONE);
        }
    }
}
