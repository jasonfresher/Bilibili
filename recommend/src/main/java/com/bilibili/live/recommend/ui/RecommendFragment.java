package com.bilibili.live.recommend.ui;

import android.os.Bundle;

import com.bilibili.live.base.RxLazyFragment;

/**
 * Created by jason on 2018/9/14.
 */

public class RecommendFragment extends RxLazyFragment {
    @Override
    public int getLayoutResId() {
        return 0;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void loadData() {
        super.loadData();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }

        loadData();

        isPrepared = false;
    }
}
