package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.shooshoo.fragment.WinnerChallengeFragment;
import com.android.shooshoo.fragment.WinnersFragment;
import com.android.shooshoo.fragment.WinnersListFragment;
import com.android.shooshoo.fragment.WinnersUserWiseFragment;

public class WinnersPagerAdapter extends FragmentPagerAdapter {

    private Context myContext;

    public WinnersPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        myContext = context;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                 fragment= WinnerChallengeFragment.newInstance("Best","winners");
                break;
            case 1:
                 fragment= WinnersUserWiseFragment.newInstance("User","wise");
                break;


        }
        return fragment;
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
            return 2;
    }

}