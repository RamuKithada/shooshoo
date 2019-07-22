package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.android.shooshoo.fragment.MyToroFragment;

public class FeedListFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context myContext;
    String[] names=null;

    public FeedListFragmentPagerAdapter(Context context, FragmentManager fm, String[] names) {
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
                fragment= MyToroFragment.newInstance("sponsor","sponsor");
                break;
            case 1:
                fragment=MyToroFragment.newInstance("jackpot","jackpot");
                break;
            case 2:
                fragment=MyToroFragment.newInstance("friends","friends");
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