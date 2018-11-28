package com.bilibili.live.streamer.pusher;


import android.content.Context;
import android.hardware.Camera.CameraInfo;
import android.util.Pair;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.bilibili.live.base.utils.ScreenResolution;
import com.bilibili.live.streamer.jni.PushNative;
import com.bilibili.live.streamer.listener.LiveStateChangeListener;
import com.bilibili.live.streamer.params.AudioParam;
import com.bilibili.live.streamer.params.VideoParam;

public class LivePusher implements Callback {

	private SurfaceHolder surfaceHolder;
	private VideoPusher videoPusher;
	private AudioPusher audioPusher;
	private PushNative pushNative;
	private Context mContext;

	public LivePusher(SurfaceView surfaceView) {
		mContext = surfaceView.getContext();
		this.surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		prepare();
	}

	/**
	 * 预览准备
	 */
	private void prepare() {
		pushNative = new PushNative();
		Pair<Integer, Integer> res = ScreenResolution.getResolution(mContext);
		//实例化视频推流器
		VideoParam videoParam = new VideoParam(640, 480, CameraInfo.CAMERA_FACING_BACK);
		videoPusher = new VideoPusher(surfaceHolder,videoParam,pushNative);
		
		//实例化音频推流器
		AudioParam audioParam = new AudioParam();
		audioPusher = new AudioPusher(audioParam,pushNative);
	}

	/**
	 * 切换摄像头
	 */
	public void switchCamera() {
		videoPusher.switchCamera();
	}

	/**
	 * 开始推流
	 * @param url
	 * @param liveStateChangeListener
	 */
	public void startPush(String url,LiveStateChangeListener liveStateChangeListener) {
		videoPusher.startPush();
		audioPusher.startPush();
		pushNative.startPush(url);
		pushNative.setLiveStateChangeListener(liveStateChangeListener);
	}
	
	
	/**
	 * 停止推流
	 */
	public void stopPush() {
		videoPusher.stopPush();
		audioPusher.stopPush();
		pushNative.stopPush();
		pushNative.removeLiveStateChangeListener();
	}
	
	/**
	 * 释放资源
	 */
	private void release() {
		videoPusher.release();
		audioPusher.release();
		pushNative.release();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		stopPush();
		release();
	}
	
}
