package com.bilibili.live.region.ui;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bilibili.live.base.RxLazyFragment;
import com.bilibili.live.region.R;
import com.bilibili.live.region.R2;
import com.bilibili.live.region.adapter.RegionHomeAdapter;
import com.bilibili.live.region.bean.RegionHomeItemBean;
import com.bilibili.live.region.bean.RegionTypesInfo;
import com.bilibili.live.region.mvp.presenter.IRegionHomePresenter;
import com.bilibili.live.region.mvp.presenter.RegionHomePresenterImpl;
import com.bilibili.live.region.mvp.view.IRegionHomeView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jason on 2018/10/15.
 */

public class RegionFragment extends RxLazyFragment implements IRegionHomeView,BaseQuickAdapter.OnItemClickListener {

    @BindView(R2.id.recyclerview)
    protected RecyclerView mRecyclerView;

    private GridLayoutManager mLayoutManager;

    private List<RegionTypesInfo.DataBean> regionTypes = new ArrayList<>();

    private RegionHomeAdapter regionAdapter;

    public static RegionFragment newInstance(boolean isLazyLoad) {
        Bundle args = new Bundle();
        args.putBoolean(RxLazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        RegionFragment fragment = new RegionFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    protected int getLayoutResId() {
        return R.layout.region_home_layout;
    }

    @Override
    protected void init() {
        IRegionHomePresenter homePresenter = new RegionHomePresenterImpl(this);
        homePresenter.getRegionHomeData();
        mLayoutManager = new GridLayoutManager(getActivity(),3);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void loadRegionHomeInfo(List<RegionHomeItemBean> regionHomeItemBeans) {
        regionAdapter = new RegionHomeAdapter(R.layout.region_home_item_layout,regionHomeItemBeans);
        regionAdapter.setOnItemClickListener(this);
        loadData();
    }

    protected void loadData() {
        Observable.just(readAssetsJson())
                .map(new Function<String, RegionTypesInfo>() {
                    @Override
                    public RegionTypesInfo apply(String s) throws Exception {
                        return new Gson().fromJson(s, RegionTypesInfo.class);
                    }
                })
                .map(new Function<RegionTypesInfo, List<RegionTypesInfo.DataBean>>() {
                    @Override
                    public List<RegionTypesInfo.DataBean> apply(RegionTypesInfo regionTypesInfo) throws Exception {
                        List<RegionTypesInfo.DataBean> regionTypesInfoData = regionTypesInfo.getData();
                        return regionTypesInfoData;
                    }
                })
                .compose(this.<List<RegionTypesInfo.DataBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<RegionTypesInfo.DataBean>>() {
                    @Override
                    public void accept(List<RegionTypesInfo.DataBean> dataBeans) throws Exception {
                        regionTypes.addAll(dataBeans);
                        mRecyclerView.setAdapter(regionAdapter);
                    }
                });
    }


    /**
     * 读取assets下的json数据
     */
    private String readAssetsJson() {
        AssetManager assetManager = getActivity().getAssets();
        try {
            InputStream is = assetManager.open("region.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                stringBuilder.append(str);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position) {
            case 0:
                RegionTypesInfo.DataBean dataBean = new RegionTypesInfo.DataBean();
                dataBean.setName("直播");
                LiveAppActivity.launch(getActivity(),dataBean);
                break;

            case 1:
                //番剧
                RegionTypeDetailsActivity.launch(getActivity(),regionTypes.get(1));
                break;

            case 2:
                //动画
                break;

            case 3:
                //音乐
                break;

            case 4:
                //舞蹈
                break;

            case 5:
                //游戏
                break;

            case 6:
                //科技
                break;

            case 7:
                //生活
                break;

            case 8:
                //鬼畜
                break;

            case 9:
                //时尚
                break;

            case 10:
                //广告
                break;

            case 11:
                //娱乐
                break;

            case 12:
                //电影
                break;

            case 13:
                //电视剧
                break;

            case 14:
                // 游戏中心
                break;

            default:
                break;
        }
    }

    @Override
    public void errorCallback(Throwable throwable) {

    }
}
