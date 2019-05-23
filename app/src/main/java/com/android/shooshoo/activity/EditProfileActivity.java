package com.android.shooshoo.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.ChallengerViewPagerAdapter;
import com.android.shooshoo.adapter.ProfileViewPagerAdapter;

public class EditProfileActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    ImageView iv_chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        iv_chat=(ImageView)findViewById(R.id.iv_chat);
         TabLayout tabLayout=findViewById(R.id.tab_layout);
         ViewPager viewPager=findViewById(R.id.view_pager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabLayout.setTabTextColors(Color.parseColor("#CCCCCC"), Color.parseColor("#ffffff"));
        ProfileViewPagerAdapter profileViewPagerAdapter=new ProfileViewPagerAdapter(this,getSupportFragmentManager(),new String[]{"Profile Settings","Profile Visibility"});
        viewPager.setAdapter(profileViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        iv_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.END);
                }
            }
        });
    }
}
