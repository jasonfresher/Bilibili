package com.bilibili.live.player.core;


import android.view.View;

public interface IMediaPlayer {

  void start();

  void pause();

  int getDuration();

  int getCurrentPosition();

  void seekTo(long pos);

  boolean isPlaying();

  int getBufferPercentage();

  boolean canPause();

  View getMediaController();
}
