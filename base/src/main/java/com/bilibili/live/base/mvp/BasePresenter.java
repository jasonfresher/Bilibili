package com.bilibili.live.base.mvp;

import java.lang.ref.WeakReference;

/**
 * Created by jason on 2018/11/1.
 */

public abstract class BasePresenter<V> {

    protected WeakReference<V> mViewRef;

    public void attachView(V view) {
        mViewRef = new WeakReference<>(view);
    }

    public void detachView() {
        if(mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    protected V getView() {
        return mViewRef.get();
    }
}