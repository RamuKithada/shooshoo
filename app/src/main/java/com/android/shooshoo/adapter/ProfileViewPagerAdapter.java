package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.shooshoo.fragment.ChallengeListFragment;
import com.android.shooshoo.fragment.ProfileSettingFragment;
import com.android.shooshoo.fragment.ProfileVisibilityFragment;

public class ProfileViewPagerAdapter extends FragmentPagerAdapter {

    private Context myContext;
    String[] names=null;

    public ProfileViewPagerAdapter(Context context, FragmentManager fm, String[] names) {
        super(fm);
        myContext = context;
        this.names = names;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position) {
            case 0:
                 fragment = ProfileSettingFragment.newInstance("sai","ram");
                break;
            case 1:
                 fragment = ProfileVisibilityFragment.newInstance("sai","ram");
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
    // overriding getPageTitle()
    @Override
    public CharSequence getPageTitle(int position) {
        if(names==null)
            return null;

        return names[position];
    }
}