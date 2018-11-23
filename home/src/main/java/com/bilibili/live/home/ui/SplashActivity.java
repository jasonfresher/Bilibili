package com.bilibili.live.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.bilibili.live.home.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jason on 2018/11/23.
 */

public class SplashActivity extends RxAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setBackgroundDrawable(null);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.splash_activity_layout);

        Observable.timer(3, TimeUnit.SECONDS)
                .compose(this.<Long>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
