package com.android.shooshoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.fragment.ChallengersFragment;
import com.android.shooshoo.fragment.ChatsFragment;
import com.android.shooshoo.fragment.HomeFragment;
import com.android.shooshoo.fragment.ProfileFragment;
import com.android.shooshoo.fragment.RadarFragment;
import com.android.shooshoo.fragment.WinnersFragment;
import com.android.shooshoo.models.HomeResponse;
import com.android.shooshoo.views.HomeView;


import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements HomeView,View.OnClickListener{

    private TextView mTextMessage;
    @BindView(R.id.iv_help)
    ImageView iv_help;
    /*@BindView(R.id.iv_wallet)
    ImageView iv_wallet;*/
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
    @BindView(R.id.bottom_navi_view)
    LinearLayout bottom_navi_view;
    @BindView(R.id.iv_navigation_home)
    ImageView iv_navigation_home;
    @BindView(R.id.iv_navigation_challengers)
    ImageView iv_navigation_challengers;
    @BindView(R.id.iv_navigation_feed)
    ImageView iv_navigation_feed;
    @BindView(R.id.iv_navigation_winners)
    ImageView iv_navigation_winners;
    @BindView(R.id.iv_navigation_radar)
    ImageView iv_navigation_radar;
    @BindView(R.id.tv_navigation_home)
    AppCompatTextView tv_navigation_home;
    @BindView(R.id.tv_navigation_challengers)
    AppCompatTextView tv_navigation_challengers;
    @BindView(R.id.tv_navigation_feed)
    AppCompatTextView tv_navigation_feed;
    @BindView(R.id.tv_navigation_winners)
    AppCompatTextView tv_navigation_winners;
    @BindView(R.id.tv_navigation_radar)
    AppCompatTextView tv_navigation_radar;

    @BindView(R.id.navigation_home)
    LinearLayout navigation_home;
    @BindView(R.id.navigation_challengers)
    LinearLayout navigation_challengers;
    @BindView(R.id.navigation_feed)
    LinearLayout navigation_feed;
    @BindView(R.id.navigation_winners)
    LinearLayout navigation_winners;
    @BindView(R.id.navigation_radar)
    LinearLayout navigation_radar;



    /**
     * This is fragment to present Home tab view
     * {@link #bottomNavigationOnClickListener} is used to click functionality for bottom menu items
     * here we transform one tab to another
     * Those are Home,Challenges,Feed,Winners,Radar
     *
     */
private View.OnClickListener bottomNavigationOnClickListener=new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        switch (v.getId()) {
            case R.id.navigation_home:
                mTextMessage.setText(R.string.title_home);
                iv_chat.setVisibility(View.VISIBLE);
                iv_help.setVisibility(View.VISIBLE);
                iv_search.setVisibility(View.VISIBLE);
//                iv_wallet.setVisibility(View.VISIBLE);
                iv_filters.setVisibility(View.GONE);
                iv_edit_profile.setVisibility(View.GONE);
                iv_profile.setVisibility(View.VISIBLE);
                fragmentManager.beginTransaction().replace(R.id.home_fragment_container, HomeFragment.newInstance("Hi","Ram"),"home").commit();
                break;
            case R.id.navigation_challengers:
                mTextMessage.setText(R.string.title_challengers);
                iv_chat.setVisibility(View.VISIBLE);
                iv_help.setVisibility(View.VISIBLE);
                iv_search.setVisibility(View.GONE);
//                iv_wallet.setVisibility(View.GONE);
                iv_filters.setVisibility(View.GONE);
                iv_edit_profile.setVisibility(View.GONE);
                iv_profile.setVisibility(View.VISIBLE);
                fragmentManager.beginTransaction().replace(R.id.home_fragment_container, ChallengersFragment.newInstance("Hi","Ram"),"challenges").commit();
                    break;
            case R.id.navigation_feed:
                mTextMessage.setText(R.string.title_feed);
                iv_chat.setVisibility(View.VISIBLE);
                iv_help.setVisibility(View.GONE);
                iv_search.setVisibility(View.GONE);
//                iv_wallet.setVisibility(View.GONE);
                iv_filters.setVisibility(View.VISIBLE);
                iv_edit_profile.setVisibility(View.GONE);
                iv_profile.setVisibility(View.VISIBLE);
                startActivity(new Intent(HomeActivity.this,FeedsActivity.class));
//                fragmentManager.beginTransaction().replace(R.id.home_fragment_container, FeedFragment.newInstance("Hi","Ram")).commit();
              break;
            case R.id.navigation_winners:
                mTextMessage.setText(R.string.title_winners);
                iv_chat.setVisibility(View.VISIBLE);
                iv_help.setVisibility(View.VISIBLE);
                iv_search.setVisibility(View.GONE);
//                iv_wallet.setVisibility(View.GONE);
                iv_filters.setVisibility(View.GONE);
                iv_edit_profile.setVisibility(View.GONE);
                iv_profile.setVisibility(View.VISIBLE);
                fragmentManager.beginTransaction().replace(R.id.home_fragment_container, WinnersFragment.newInstance("Hi","Ram"),"winners").commit();
                break;
            case R.id.navigation_radar:
                mTextMessage.setText(R.string.title_radar);
                iv_chat.setVisibility(View.GONE);
                iv_help.setVisibility(View.GONE);
                iv_search.setVisibility(View.GONE);
//                iv_wallet.setVisibility(View.GONE);
                iv_filters.setVisibility(View.GONE);
                iv_edit_profile.setVisibility(View.GONE);
                iv_profile.setVisibility(View.VISIBLE);
                fragmentManager.beginTransaction().replace(R.id.home_fragment_container, RadarFragment.newInstance("Hi","Ram"),"radar").commit();
                break;
        }
        selectNavi(v);
    }
};

/**
 * {@link #selectNavi(View)} is used to tab when user select a tab highlights and default it home is highlighted.
 */

    private void selectNavi(View view) {
        iv_navigation_home.setImageResource(R.mipmap.home_normal);
        iv_navigation_challengers.setImageResource(R.mipmap.challenges_normal);
        iv_navigation_feed.setImageResource(R.mipmap.feed_normal);
        iv_navigation_winners.setImageResource(R.mipmap.winners_normal);
        iv_navigation_radar.setImageResource(R.mipmap.radar_normal);
        tv_navigation_home.setTextColor(getResources().getColor(R.color.gray_text));
        tv_navigation_challengers.setTextColor(getResources().getColor(R.color.gray_text));
        tv_navigation_feed.setTextColor(getResources().getColor(R.color.gray_text));
        tv_navigation_winners.setTextColor(getResources().getColor(R.color.gray_text));
        tv_navigation_radar.setTextColor(getResources().getColor(R.color.gray_text));
        switch (view.getId()){
            case R.id.navigation_home:
                iv_navigation_home.setImageResource(R.mipmap.home_active);
                tv_navigation_home.setTextColor(getResources().getColor(R.color.pink_drak));

                break;
            case R.id.navigation_challengers:
                iv_navigation_challengers.setImageResource(R.mipmap.challenges_active);
                tv_navigation_challengers.setTextColor(getResources().getColor(R.color.pink_drak));

                 break;
            case R.id.navigation_feed:
                iv_navigation_feed.setImageResource(R.mipmap.feed_active);
                tv_navigation_feed.setTextColor(getResources().getColor(R.color.pink_drak));


                break;
            case R.id.navigation_winners:
                iv_navigation_winners.setImageResource(R.mipmap.winners_active);
                tv_navigation_winners.setTextColor(getResources().getColor(R.color.pink_drak));


                break;
            case R.id.navigation_radar:
                iv_navigation_radar.setImageResource(R.mipmap.radar_active);
                tv_navigation_radar.setTextColor(getResources().getColor(R.color.pink_drak));
               break;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mTextMessage = (TextView) findViewById(R.id.tv_title);
        iv_chat.setVisibility(View.VISIBLE);
        iv_help.setVisibility(View.VISIBLE);
        iv_search.setVisibility(View.VISIBLE);
//        iv_wallet.setVisibility(View.VISIBLE);
        iv_profile.setVisibility(View.VISIBLE);
        iv_filters.setVisibility(View.GONE);
        iv_edit_profile.setVisibility(View.GONE);
        bottom_navi_view.setOnClickListener(bottomNavigationOnClickListener);

        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.home_fragment_container, HomeFragment.newInstance("Hi","Ram"),"home").commit();
        iv_profile.setOnClickListener(this);
        iv_edit_profile.setOnClickListener(this);
        iv_chat.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        navigation_home.setOnClickListener(bottomNavigationOnClickListener);
        navigation_challengers.setOnClickListener(bottomNavigationOnClickListener);
        navigation_feed.setOnClickListener(bottomNavigationOnClickListener);
        navigation_winners.setOnClickListener(bottomNavigationOnClickListener);
        navigation_radar.setOnClickListener(bottomNavigationOnClickListener);
        int icon=getIntent().getIntExtra("icon",0);
        switch (icon){
            case 0:
                bottomNavigationOnClickListener.onClick(navigation_home);
                break;
            case 1:
                bottomNavigationOnClickListener.onClick(navigation_challengers);
                break;
            case 2:
                bottomNavigationOnClickListener.onClick(navigation_feed);
                break;
            case 3:
                bottomNavigationOnClickListener.onClick(navigation_winners);
                break;
            case 4:
                bottomNavigationOnClickListener.onClick(navigation_radar);
                break;
            case 6:
                onClick(iv_profile);
                break;
            case 7:
                onClick(iv_chat);
                break;
        }


    }


/**
*  onClick is used to handling top menu icon click actions
 *  like search ,profile,Chat
*
* */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_profile:
                FragmentManager fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.home_fragment_container, ProfileFragment.newInstance(userSession.getUserId(),"Ram"),"profile").commit();
                mTextMessage.setText(R.string.profile);
                iv_chat.setVisibility(View.VISIBLE);
                iv_help.setVisibility(View.GONE);
                iv_search.setVisibility(View.GONE);
//                iv_wallet.setVisibility(View.GONE);
                iv_filters.setVisibility(View.GONE);
                iv_profile.setVisibility(View.GONE);
                iv_edit_profile.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_edit_profile:
                Intent intent=new Intent(this,EditProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_chat:
                mTextMessage.setText("Chats");
                iv_chat.setVisibility(View.GONE);
                iv_profile.setVisibility(View.VISIBLE);
                iv_help.setVisibility(View.GONE);
                iv_search.setVisibility(View.VISIBLE);
//                iv_wallet.setVisibility(View.GONE);
                iv_filters.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, ChatsFragment.newInstance("Hi","Ram"),"chat").commit();
                break;
            case R.id.iv_search:
                /**
                 * calling Chatting search friends activity
                 */
                HomeFragment homeFragment = (HomeFragment)getSupportFragmentManager().findFragmentByTag("home");
                if (homeFragment != null && homeFragment.isVisible()) {
                    Intent intentsearch=new Intent(this,HomeSearchActivity.class);
                    startActivity(intentsearch);
                    return;
                }
                ChatsFragment chatsFragment = (ChatsFragment) getSupportFragmentManager().findFragmentByTag("chat");
                if (chatsFragment != null && chatsFragment.isVisible()) {
                    Intent intentsearch=new Intent(this,ChatSearchActivity.class);
                    startActivity(intentsearch);
                    return;
                }


                break;
        }

    }

    @Override
    public void onLoadService(HomeResponse response) {

    }
}
