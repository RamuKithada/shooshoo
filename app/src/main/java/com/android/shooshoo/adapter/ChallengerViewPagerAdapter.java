package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.shooshoo.fragment.ChallengeListFragment;

public class ChallengerViewPagerAdapter  extends FragmentPagerAdapter {

    private Context myContext;
    String[] names=null;

    public ChallengerViewPagerAdapter(Context context, FragmentManager fm, String[] names) {
        super(fm);
        myContext = context;
        this.names = names;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
                Fragment fragment = ChallengeListFragment.newInstance(position);
                return fragment;
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        if(names==null)
            return 0;
        return names.length;
    }
    // overriding getPageTitle()
    @Override
    public CharSequence getPageTitle(int position) {
        if(names==null)
             return null;

        return names[position];
    }
}