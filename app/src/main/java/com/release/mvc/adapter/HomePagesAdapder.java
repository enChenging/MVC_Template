package com.release.mvc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.release.mvc.base.BaseFragment;

import java.util.List;

/**
 * @author Mr.release
 * @create 2019/3/26
 * @Describe
 */
public class HomePagesAdapder extends FragmentPagerAdapter {

    private List<BaseFragment> mData;
    public HomePagesAdapder(FragmentManager fm, List<BaseFragment> homePages) {
        super(fm);
        this.mData = homePages;
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }
}
