package com.bilibili.live.player.ui;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilibili.live.base.RxLazyFragment;
import com.bilibili.live.base.constants.ParamsConstant;
import com.bilibili.live.base.mvp.BasePresenter;
import com.bilibili.live.player.R;
import com.bilibili.live.player.R2;
import com.bilibili.live.player.core.MediaController;
import com.bilibili.live.player.core.VideoGestureRelativeLayout;
import com.bilibili.live.player.core.VideoPlayerView;
import com.bilibili.live.player.listener.DanmukuSwitchListener;

import butterknife.BindView;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by jason on 2018/11/6.
 */

public class VideoPlayerFragment extends RxLazyFragment {

    @BindView(R2.id.playerView)
    VideoPlayerView mPlayerView;

    @BindView(R2.id.media_controller)
    MediaController mMediaController;

    @BindView(R2.id.video_gesture_view)
    VideoGestureRelativeLayout mVideoGestureView;

    @BindView(R2.id.buffering_indicator)
    View mBufferingIndicator;

    @BindView(R2.id.video_start)
    RelativeLayout mVideoPrepareLayout;

    @BindView(R2.id.bili_anim)
    ImageView mAnimImageView;

    @BindView(R2.id.video_start_info)
    TextView mPrepareText;

    private AnimationDrawable mLoadingAnim;

    private String playUrl;

    private String title = "";

    private boolean hardDecode;


    public static VideoPlayerFragment newInstance(boolean isLazyLoad,String playUrl,String playTitle,boolean hardDecode) {
        Bundle args = new Bundle();
        args.putBoolean(RxLazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        args.putString(ParamsConstant.EXTRA_PLAYER_URL,playUrl);
        args.putString(ParamsConstant.EXTRA_PLAYER_TITLE,playTitle);
        args.putBoolean(ParamsConstant.EXTRA_PLAYER_HARDDECODE, hardDecode);
        VideoPlayerFragment fragment = new VideoPlayerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_video_player_layout;
    }

    @Override
    protected void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            playUrl = bundle.getString(ParamsConstant.EXTRA_PLAYER_URL);
            title = bundle.getString(ParamsConstant.EXTRA_PLAYER_TITLE);
            hardDecode = bundle.getBoolean(ParamsConstant.EXTRA_PLAYER_HARDDECODE,false);
        }
        initAnimation();
        initMediaPlayer();
    }

    /**
     * 初始化加载动画
     */
    private void initAnimation() {
        mVideoPrepareLayout.setVisibility(View.VISIBLE);
        mLoadingAnim = (AnimationDrawable) mAnimImageView.getBackground();
        mLoadingAnim.start();
    }

    private void initMediaPlayer() {
        //配置播放器
        mMediaController.setTitle(title);
        mMediaController.disappearHeader();
        mPlayerView.setMediaController(mMediaController);
        mPlayerView.setVideoGestureView(mVideoGestureView);
        mPlayerView.setHardDecode(hardDecode);
        mPlayerView.setMediaBufferingIndicator(mBufferingIndicator);
        mPlayerView.requestFocus();
        mPlayerView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    if (mBufferingIndicator != null) {
                        mBufferingIndicator.setVisibility(View.VISIBLE);
                    }
                } else if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    if (mBufferingIndicator != null) {
                        mBufferingIndicator.setVisibility(View.GONE);
                    }
                }
                return true;
            }
        });
        mPlayerView.setOnSeekCompleteListener(new IMediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(IMediaPlayer iMediaPlayer) {

            }
        });
        mPlayerView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                mPlayerView.pause();
            }
        });
        mPlayerView.setOnControllerEventsListener(new VideoPlayerView.OnControllerEventsListener() {
            @Override
            public void onVideoPause() {

            }

            @Override
            public void OnVideoResume() {

            }
        });
        //设置弹幕开关监听
        mMediaController.setDanmakuSwitchListener(new DanmukuSwitchListener() {
            @Override
            public void setDanmakushow(boolean isShow) {

            }
        });
        mPlayerView.setVideoPath(playUrl);
        mPlayerView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                mLoadingAnim.stop();
//                startText = startText + "【完成】\n视频缓冲中...";
//                mPrepareText.setText(startText);
                mVideoPrepareLayout.setVisibility(View.GONE);
            }
        });
    }
}
