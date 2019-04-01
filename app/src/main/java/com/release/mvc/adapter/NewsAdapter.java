package com.release.mvc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
public class NewsAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mItems;

    public NewsAdapter(FragmentManager fm, List<Fragment> items) {
        super(fm);
        mItems = items;
    }

    @Override
    public Fragment getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }
}
