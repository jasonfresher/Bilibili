package com.bilibili.live.player.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.bilibili.live.player.R;
import com.bilibili.live.player.utils.TimeFormatUtils;
import com.bilibili.live.player.widget.ShowChangeLayout;

/**
 * 手势控制面板-惊醒快进快退
 * Created by jason on 2018/10/29.
 */

public class VideoGestureRelativeLayout extends RelativeLayout {

    private static final int NONE = 0, VOLUME = 1, BRIGHTNESS = 2, FF_REW = 3;

    private int mScrollMode = NONE;

    private int offsetX = 1;

    private boolean hasFF_REW = false;

    private GestureCallback mGestureCallback;

    private GestureDetector mGestureDetector;

    private IMediaPlayer mPlayer;

    private ShowChangeLayout mShowChangeLayout;

    private int newProgress = 0, oldProgress = 0 ,oldE2X = -1;

    public VideoGestureRelativeLayout(Context context) {
        super(context);
        init(context);
    }

    public VideoGestureRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setGestureCallback(GestureCallback gestureCallback) {
        mGestureCallback = gestureCallback;
    }

    private void init(Context context) {
        attachShowChangeView();
        VideoPlayerOnGestureListener videoPlayerOnGestureListener = new VideoPlayerOnGestureListener();
        mGestureDetector = new GestureDetector(context,videoPlayerOnGestureListener);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (hasFF_REW) {
                        if(mPlayer != null){
                            //手指抬起进行seekTo
                            long seekToMsec = (long)(mPlayer.getDuration() * (newProgress * 1.0f / 1000));
                            mPlayer.seekTo(seekToMsec);
                            //seekTo结束之后mediaController3秒后消失
                            ((MediaController)mPlayer.getMediaController()).show(3000);
                        }else if (mGestureCallback != null) {
                            mGestureCallback.onEndFF_REW(event);
                        }
                        hasFF_REW = false;
                    }
                }
                //监听触摸事件
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }

    private void attachShowChangeView() {
        mShowChangeLayout = new ShowChangeLayout(getContext());
        addView(mShowChangeLayout);
    }

    public void setMediaPlayer(IMediaPlayer videoPlayerView) {
        mPlayer = videoPlayerView;
    }

    public class VideoPlayerOnGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            switch (mScrollMode) {
                case NONE:
                    //offset是让快进快退不要那么敏感的值
                    if (Math.abs(distanceX) - Math.abs(distanceY) > offsetX) {
                        mScrollMode = FF_REW;
                    } else {
                        if (e1.getX() < getWidth() / 2) {
                            mScrollMode = BRIGHTNESS;
                        } else {
                            mScrollMode = VOLUME;
                        }
                    }
                    break;
                case VOLUME:
                    if (mGestureCallback != null) {
                        mGestureCallback.onVolumeGesture(e1, e2, distanceX, distanceY);
                    }
                    break;
                case BRIGHTNESS:
                    if (mGestureCallback != null) {
                        mGestureCallback.onBrightnessGesture(e1, e2, distanceX, distanceY);
                    }
                    break;
                case FF_REW:
                    if(mPlayer != null){
                        //在手指滑动屏幕进行快进快退时保持mediaController一直显示
                        ((MediaController)mPlayer.getMediaController()).show(0);
                        //根据移动的正负决定快进还是快退
                        float offset = e2.getX() - e1.getX();
                        if(oldE2X != -1) {
                            if (e2.getX() - oldE2X > 0) {
                                mShowChangeLayout.setImageResource(R.drawable.ff);
                            } else {
                                mShowChangeLayout.setImageResource(R.drawable.fr);
                            }
                        }
                        if (offset > 0) {
                            newProgress = (int) (oldProgress + offset/getWidth() * 1000);
                            if (newProgress > 1000){
                                newProgress = 1000;
                            }
                        }else {
                            newProgress = (int) (oldProgress + offset/getWidth() * 1000);
                            if (newProgress < 0){
                                newProgress = 0;
                            }
                        }
                        long seekPosition = (mPlayer.getDuration() * newProgress) / 1000;
                        mShowChangeLayout.setCurrentTime(TimeFormatUtils.generateTime(seekPosition));
                        mShowChangeLayout.setProgress(newProgress);
                        mShowChangeLayout.show();
                        oldE2X = (int) e2.getX();
                    }else if (mGestureCallback != null) {
                        mGestureCallback.onFF_REWGesture(e1, e2, distanceX, distanceY);
                    }
                    hasFF_REW = true;
                    break;
            }
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            hasFF_REW = false;
            //每次按下都重置为NONE
            mScrollMode = NONE;
            //记录当前progress
            oldProgress = newProgress;
            if(mPlayer != null){
                //设置快进快退信息框的时间信息
                int duration = mPlayer.getDuration();
                int currentPosition = mPlayer.getCurrentPosition();
                mShowChangeLayout.setCurrentTime(TimeFormatUtils.generateTime(currentPosition));
                mShowChangeLayout.setDurationTime(TimeFormatUtils.generateTime(duration));

            }else if (mGestureCallback != null) {
                mGestureCallback.onDown(e);
            }
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (mGestureCallback != null) {
                mGestureCallback.onDoubleTapGesture(e);
            }
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            //手指按下执行mediaController的显示与消失
            if (((MediaController)mPlayer.getMediaController()).isShowing()) {
                ((MediaController)mPlayer.getMediaController()).hide();
            } else {
                ((MediaController)mPlayer.getMediaController()).show();
            }
            if (mGestureCallback != null) {
                mGestureCallback.onSingleTapGesture(e);
            }
            return true;
        }
    }


    public interface GestureCallback {
        //亮度手势，手指在Layout左半部上下滑动时候调用
        void onBrightnessGesture(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);

        //音量手势，手指在Layout右半部上下滑动时候调用
        void onVolumeGesture(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);

        //快进快退手势，手指在Layout左右滑动的时候调用
        void onFF_REWGesture(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);

        //单击手势，确认是单击的时候调用
        void onSingleTapGesture(MotionEvent e);

        //双击手势，确认是双击的时候调用
        void onDoubleTapGesture(MotionEvent e);

        //按下手势，第一根手指按下时候调用
        void onDown(MotionEvent e);

        //快进快退执行后的松开时候调用
        void onEndFF_REW(MotionEvent e);
    }
}
