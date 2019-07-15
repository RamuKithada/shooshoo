package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.android.shooshoo.fragment.BrandListFragment;
import com.android.shooshoo.fragment.ChallengeListFragment;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.CompanyDetails;

import java.util.List;

public class BrandProfilePagerAdapter extends FragmentPagerAdapter {

    private Context myContext;
    String[] names=null;
    CompanyDetails companyDetails;
//    BrandListFragment[] fragments=new BrandListFragment[3];

    public BrandProfilePagerAdapter(Context context, FragmentManager fm, String[] names ,CompanyDetails details) {
        super(fm);
        myContext = context;
        this.names = names;
        this.companyDetails=details;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        List<Challenge> challenges=null;
        BrandListFragment fragment=null;
        switch (position){
            case 0:
                if(companyDetails!=null)
                challenges=companyDetails.getRecent();
                    fragment = BrandListFragment.newInstance(position,challenges);
                break;
            case 1:
                if(companyDetails!=null)
                    challenges=companyDetails.getFinished();

                    fragment = BrandListFragment.newInstance(position,challenges);
                break;
            case 2:
                if(companyDetails!=null)
                    challenges=companyDetails.getRecent();

                    fragment = BrandListFragment.newInstance(position,challenges);

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