package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.shooshoo.fragment.GridFragment;
import com.android.shooshoo.fragment.ProfileGridFragment;

public class FeedProfileGridFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context myContext;
    String[] names=null;
    String userId=null;
    public FeedProfileGridFragmentPagerAdapter(Context context, FragmentManager fm, String[] names,String userId) {
        super(fm);
        myContext = context;
        this.names = names;
        this.userId=userId;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                 fragment= ProfileGridFragment.newInstance("newposts",userId);
           break;
           case 1:
                 fragment=ProfileGridFragment.newInstance("bestposts",userId);
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