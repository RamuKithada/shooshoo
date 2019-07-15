package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.shooshoo.fragment.FeedListFragment;
import com.android.shooshoo.fragment.GridFragment;

public class FeedGridFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context myContext;
    String[] names=null;
    public FeedGridFragmentPagerAdapter(Context context, FragmentManager fm, String[] names) {
        super(fm);
        myContext = context;
        this.names = names;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment=GridFragment.newInstance("NewGrid","new");
           break;
           case 1:
               fragment=GridFragment.newInstance("Popular","popular");
               break;
            case 2:
                fragment=GridFragment.newInstance("Random","random");
                break;

        }
        return fragment;
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        if(names==null)
            return 0;
        return names.length;
    }

}