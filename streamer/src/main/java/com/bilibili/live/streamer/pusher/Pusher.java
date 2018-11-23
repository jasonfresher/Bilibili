package com.bilibili.live.streamer.pusher;

public abstract class Pusher {

	public abstract void startPush();
	
	public abstract void stopPush();
	
	public abstract void release();
	
}
