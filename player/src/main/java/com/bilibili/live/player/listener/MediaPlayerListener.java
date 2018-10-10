package com.bilibili.live.player.listener;


public interface MediaPlayerListener {

  void start();

  void pause();

  int getDuration();

  int getCurrentPosition();

  void seekTo(long pos);

  boolean isPlaying();

  int getBufferPercentage();

  boolean canPause();
}
