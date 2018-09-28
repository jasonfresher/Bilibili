package com.bilibili.live.home.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bilibili.live.home.R;

import java.util.List;

/**
 * Created by jason on 2018/9/28.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {

    private final String[] TITLES;
    private List<Fragment> mFragments;


    public HomePagerAdapter(FragmentManager fm, Context context, List<Fragment> fragments) {
        super(fm);
        TITLES = context.getResources().getStringArray(R.array.sections);
        mFragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }


    @Override
    public int getCount() {
        return TITLES.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
