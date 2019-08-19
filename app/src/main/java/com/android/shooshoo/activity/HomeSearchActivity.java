package com.android.shooshoo.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.HomeSearchPagerAdapter;
import com.android.shooshoo.fragment.BrandSearchFragment;
import com.android.shooshoo.fragment.ChallengeSearchFragment;
import com.android.shooshoo.fragment.FilterSearchFragment;
import com.android.shooshoo.fragment.UserSearchFragment;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.models.User;
import com.android.shooshoo.presenters.HomeSearchPresenter;
import com.android.shooshoo.views.SearchView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/** Here , We show the search functionality to brand,challenge,user.
 *
 */
public class HomeSearchActivity extends BaseActivity implements SearchView ,TextWatcher {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager view_pager;

    @BindView(R.id.search_view)
    EditText search_view;

    public HomeSearchPresenter homeSearchPresenter;

    HomeSearchPagerAdapter homeSearchPagerAdapter;

    /**
     * images are the icons  for the viewpager tabs.
     */
    int[] images=new int[]{R.drawable.ic_filter_icon,R.drawable.lastname_normal,R.mipmap.challenges_normal,R.drawable.save_icon};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        ButterKnife.bind(this);
        homeSearchPresenter=new HomeSearchPresenter();
        homeSearchPagerAdapter=new HomeSearchPagerAdapter(this,getSupportFragmentManager());
        homeSearchPresenter.attachView(this);
        view_pager.setAdapter(homeSearchPagerAdapter);
        tabLayout.setupWithViewPager(view_pager);
        search_view.addTextChangedListener(this);
        /** Loading the images into tabs */
        for(int i=0;i<tabLayout.getTabCount();i++){
            View view= LayoutInflater.from(this).inflate(R.layout.custome_tab,null);
            ImageView image=view.findViewById(R.id.tab_image);
            Picasso.with(HomeSearchActivity.this).load(images[i]).into(image);
            tabLayout.getTabAt(i).setCustomView(image);
        }


    }

    /**
     *
     * @param users list of users related to the user query.
     */
    @Override
    public void onUserSearchResult(List<User> users) {
        if(homeSearchPagerAdapter.getItem(1)!=null) {
            UserSearchFragment fragment = (UserSearchFragment) homeSearchPagerAdapter.getItem(1);
            fragment.onUserSearchResult(users);
        }
    }


    /**
     *
     * @param challenges list of challenges related to the user query
     */
    @Override
    public void onChallengeSearchResult(List<Challenge> challenges) {
        if(homeSearchPagerAdapter.getItem(2)!=null) {
            ChallengeSearchFragment fragment = (ChallengeSearchFragment) homeSearchPagerAdapter.getItem(2);
            fragment.onChallengeSearchResult(challenges);
        }
    }

    @Override
    public void onBrandSearchResult(List<Company> brands) {
        if(homeSearchPagerAdapter.getItem(3)!=null) {
            BrandSearchFragment fragment = (BrandSearchFragment) homeSearchPagerAdapter.getItem(3);
            fragment.onBrandSearchResult(brands);
        }
    }

    /**
     *
     * @param users list of the user related to the user query
       */
    // TODO  We need to implement the functionality for filters .User,Brands,Challenges  have separate Screens.in filters also we used same.
    @Override
    public void onFilterSearchResult(List<User> users) {
        if(homeSearchPagerAdapter.getItem(0)!=null) {
            FilterSearchFragment fragment = (FilterSearchFragment) homeSearchPagerAdapter.getItem(0);
            fragment.onFilterSearchResult(users);
        }
    }

    @Override
    protected void onDestroy() {
        if(homeSearchPresenter!=null)
            homeSearchPresenter.detachView();
        super.onDestroy();
    }

    public HomeSearchPresenter getHomeSearchPresenter() {
        return homeSearchPresenter;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if(homeSearchPagerAdapter.getItem(view_pager.getCurrentItem()) instanceof TextWatcher)
        {
            ((TextWatcher) homeSearchPagerAdapter.getItem(view_pager.getCurrentItem())).beforeTextChanged(s, start, count, after);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(homeSearchPagerAdapter.getItem(view_pager.getCurrentItem()) instanceof TextWatcher)
        {
            ((TextWatcher) homeSearchPagerAdapter.getItem(view_pager.getCurrentItem())).onTextChanged(s, start, before, count);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(homeSearchPagerAdapter.getItem(view_pager.getCurrentItem()) instanceof TextWatcher)
        {
            ((TextWatcher) homeSearchPagerAdapter.getItem(view_pager.getCurrentItem())).afterTextChanged(s);
        }
    }
}
