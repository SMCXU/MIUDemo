package com.example.zlq_pc.miudemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by U
 * <p/>
 * on 2017/9/8
 * <p/>
 * QQ:1347414707
 * <p/>
 * For:
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> mList;
    public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> mList) {
        super(fm);
        this.mList=mList;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}