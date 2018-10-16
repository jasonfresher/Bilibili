package com.bilibili.live.region.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bilibili.live.base.RxBaseActivity;
import com.bilibili.live.base.constants.RouteInfo;
import com.bilibili.live.region.R;
import com.bilibili.live.region.R2;
import com.bilibili.live.region.bean.RegionTypesInfo;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponentCallback;

import butterknife.BindView;

/**
 * Created by jason on 2018/10/16.
 */

public class RegionTypeDetailsActivity extends RxBaseActivity {

    public static void launch(Activity activity, RegionTypesInfo.DataBean dataBean,boolean isLive) {
        Intent mIntent = new Intent(activity, RegionTypeDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("extra_partition", dataBean);
        bundle.putBoolean("other", isLive);
        mIntent.putExtras(bundle);
        activity.startActivity(mIntent);
    }

    private RegionTypesInfo.DataBean mDataBean;

    private FragmentManager fm;

    private FragmentTransaction ft;

    @BindView(R2.id.toolbar)
    protected Toolbar mToolbar;

    @Override
    public int getLayoutId() {
        return R.layout.region_details_layout;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            mDataBean = mBundle.getParcelable("extra_partition");
            boolean isLive = mBundle.getBoolean("other");
            replaceFragment(mDataBean,isLive);
        }
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle(String.valueOf(mDataBean.getName()));
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void replaceFragment(RegionTypesInfo.DataBean mDataBean, boolean isLive) {

        if(isLive){
            CC.obtainBuilder(RouteInfo.NETCASTING_COMPONENT_NAME).build().callAsyncCallbackOnMainThread(new IComponentCallback() {
                @Override
                public void onResult(CC cc, CCResult result) {
                    fm = getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    Fragment fragment = result.getDataItem(RouteInfo.NETCASTING_COMPONENT_NAME);
                    ft.replace(R.id.content, fragment);
                    ft.commit();
                }
            });

        }else{
            fm = getSupportFragmentManager();
            ft = fm.beginTransaction();
            ft.replace(R.id.content, RegionTypeRecommendFragment.newInstance(true,mDataBean));
            ft.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
