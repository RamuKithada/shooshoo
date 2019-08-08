package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.shooshoo.fragment.BrandListFragment;
import com.android.shooshoo.fragment.BrandSearchFragment;
import com.android.shooshoo.fragment.ChallengeSearchFragment;
import com.android.shooshoo.fragment.FilterSearchFragment;
import com.android.shooshoo.fragment.UserSearchFragment;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.CompanyDetails;

import java.util.List;

public class HomeSearchPagerAdapter extends FragmentPagerAdapter {

    private Context myContext;

    public HomeSearchPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        myContext = context;

    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment= FilterSearchFragment.newInstance("FilterSearchFragment","");
                break;
            case 1:
                fragment= UserSearchFragment.newInstance("UserSearchFragment","");
                break;
            case 2:
                fragment= ChallengeSearchFragment.newInstance("ChallengeSearchFragment","");

                break;
            case 3:
                fragment= BrandSearchFragment.newInstance("BrandSearchFragment","");
                break;
        }
                return fragment;
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
            return 4;
       }

}