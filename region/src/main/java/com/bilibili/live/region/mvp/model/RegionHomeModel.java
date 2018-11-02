package com.bilibili.live.region.mvp.model;

import com.bilibili.live.region.R;
import com.bilibili.live.region.bean.RegionHomeItemBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jason on 2018/10/15.
 */

public class RegionHomeModel implements IRegionHomeModel{

    private String[] itemNames = new String[] {
            "直播", "番剧", "动画",
            "音乐", "舞蹈", "游戏",
            "科技", "生活", "鬼畜",
            "时尚", "广告", "娱乐",
            "电影", "电视剧", "游戏中心",
    };

    private Integer[] itemIcons = {
            R.drawable.ic_category_live, R.drawable.ic_category_t13,
            R.drawable.ic_category_t1, R.drawable.ic_category_t3,
            R.drawable.ic_category_t129, R.drawable.ic_category_t4,
            R.drawable.ic_category_t36, R.drawable.ic_category_t160,
            R.drawable.ic_category_t119, R.drawable.ic_category_t155,
            R.drawable.ic_category_t165, R.drawable.ic_category_t5,
            R.drawable.ic_category_t23, R.drawable.ic_category_t11,
            R.drawable.ic_category_game_center
    };

    private IRegionHomeModel.RegionHomeDataCallBackListener mCallbackListener;

    public RegionHomeModel(IRegionHomeModel.RegionHomeDataCallBackListener callBackListener) {
        this.mCallbackListener = callBackListener;
    }

    @Override
    public void getRegionHomeInfos() {
        final List<RegionHomeItemBean> regionHomeItemBeans = new ArrayList<>();
        Observable<String> namesObservable = Observable.fromArray(itemNames);
        Observable<Integer> iconsObservable = Observable.fromArray(itemIcons);
        Observable.zip(namesObservable, iconsObservable,
                new BiFunction<String, Integer, RegionHomeItemBean>() {
                    @Override
                    public RegionHomeItemBean apply(String name, Integer iconRes) throws Exception {
                        RegionHomeItemBean homeItemBean = new RegionHomeItemBean();
                        homeItemBean.setName(name);
                        homeItemBean.setIconRes(iconRes);
                        return homeItemBean;
                    }
                })
                .compose(mCallbackListener.<RegionHomeItemBean>bindToLifecycle())
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<RegionHomeItemBean>>() {
                    @Override
                    public void accept(List<RegionHomeItemBean> regionHomeItemBeans) throws Exception {
                        if (mCallbackListener != null)
                            mCallbackListener.onContentInfoSuccess(regionHomeItemBeans);
                    }
                });
    }
}
