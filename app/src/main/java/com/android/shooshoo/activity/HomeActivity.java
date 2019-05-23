package com.android.shooshoo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.fragment.ChallengersFragment;
import com.android.shooshoo.fragment.FeedFragment;
import com.android.shooshoo.fragment.HomeFragment;
import com.android.shooshoo.fragment.ProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener,View.OnClickListener{

    private TextView mTextMessage;
    @BindView(R.id.iv_help)
    ImageView iv_help;
    @BindView(R.id.iv_wallet)
    ImageView iv_wallet;
    @BindView(R.id.iv_filters)
    ImageView iv_filters;
    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.iv_chat)
    ImageView iv_chat;
    @BindView(R.id.iv_edit_profile)
    ImageView iv_edit_profile;
    @BindView(R.id.iv_profile)
    ImageView iv_profile;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager=getSupportFragmentManager();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    iv_chat.setVisibility(View.VISIBLE);
                    iv_help.setVisibility(View.VISIBLE);
                    iv_search.setVisibility(View.VISIBLE);
                    iv_wallet.setVisibility(View.VISIBLE);
                    iv_filters.setVisibility(View.GONE);
                    iv_edit_profile.setVisibility(View.GONE);
                    iv_profile.setVisibility(View.VISIBLE);
                    fragmentManager.beginTransaction().replace(R.id.home_fragment_container, HomeFragment.newInstance("Hi","Ram")).commit();
                    return true;
                case R.id.navigation_challengers:
                    mTextMessage.setText(R.string.title_challengers);
                    iv_chat.setVisibility(View.VISIBLE);
                    iv_help.setVisibility(View.VISIBLE);
                    iv_search.setVisibility(View.GONE);
                    iv_wallet.setVisibility(View.GONE);
                    iv_filters.setVisibility(View.GONE);
                    iv_edit_profile.setVisibility(View.GONE);
                    iv_profile.setVisibility(View.VISIBLE);
                    fragmentManager.beginTransaction().replace(R.id.home_fragment_container, ChallengersFragment.newInstance("Hi","Ram")).commit();


                    return true;
                case R.id.navigation_feed:
                    mTextMessage.setText(R.string.title_feed);
                    iv_chat.setVisibility(View.VISIBLE);
                    iv_help.setVisibility(View.GONE);
                    iv_search.setVisibility(View.GONE);
                    iv_wallet.setVisibility(View.GONE);
                    iv_filters.setVisibility(View.VISIBLE);
                    iv_edit_profile.setVisibility(View.GONE);
                    iv_profile.setVisibility(View.VISIBLE);
                    fragmentManager.beginTransaction().replace(R.id.home_fragment_container, FeedFragment.newInstance("Hi","Ram")).commit();
                    return true;
                case R.id.navigation_winners:
                    mTextMessage.setText(R.string.title_winners);
                    iv_chat.setVisibility(View.VISIBLE);
                    iv_help.setVisibility(View.VISIBLE);
                    iv_search.setVisibility(View.GONE);
                    iv_wallet.setVisibility(View.GONE);
                    iv_filters.setVisibility(View.GONE);
                    iv_edit_profile.setVisibility(View.GONE);
                    iv_profile.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_radar:
                    mTextMessage.setText(R.string.title_radar);
                    iv_chat.setVisibility(View.GONE);
                    iv_help.setVisibility(View.GONE);
                    iv_search.setVisibility(View.GONE);
                    iv_wallet.setVisibility(View.GONE);
                    iv_filters.setVisibility(View.GONE);
                    iv_edit_profile.setVisibility(View.GONE);
                    iv_profile.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mTextMessage = (TextView) findViewById(R.id.tv_title);
        iv_chat.setVisibility(View.VISIBLE);
        iv_help.setVisibility(View.VISIBLE);
        iv_search.setVisibility(View.VISIBLE);
        iv_wallet.setVisibility(View.VISIBLE);
        iv_profile.setVisibility(View.VISIBLE);
        iv_filters.setVisibility(View.GONE);
        iv_edit_profile.setVisibility(View.GONE);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.home_fragment_container, HomeFragment.newInstance("Hi","Ram")).commit();
        iv_profile.setOnClickListener(this);
        iv_edit_profile.setOnClickListener(this);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_profile:
                FragmentManager fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.home_fragment_container, ProfileFragment.newInstance("Hi","Ram")).commit();
                mTextMessage.setText(R.string.profile);
                iv_chat.setVisibility(View.VISIBLE);
                iv_help.setVisibility(View.GONE);
                iv_search.setVisibility(View.GONE);
                iv_wallet.setVisibility(View.GONE);
                iv_filters.setVisibility(View.GONE);
                iv_profile.setVisibility(View.GONE);
                iv_edit_profile.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_edit_profile:
                Intent intent=new Intent(this,EditProfileActivity.class);
                startActivity(intent);
                break;
        }

    }
}
