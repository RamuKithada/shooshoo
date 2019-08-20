package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.shooshoo.fragment.MyToroFragment;
import com.android.shooshoo.fragment.WinnersListFragment;

public class WinnersListFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context myContext;

    public WinnersListFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        myContext = context;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment= WinnersListFragment.newInstance("Best","winners");
                break;
            case 1:
                fragment=WinnersListFragment.newInstance("Friends","winners");
                break;
            case 2:
                fragment=WinnersListFragment.newInstance("Me","winners");
                break;

        }
        return fragment;
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
            return 3;
    }

}