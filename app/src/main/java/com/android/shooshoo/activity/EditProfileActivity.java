package com.android.shooshoo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.ChallengerViewPagerAdapter;
import com.android.shooshoo.adapter.ProfileViewPagerAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout mDrawerLayout;
    ImageView iv_chat;
    ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        iv_chat=(ImageView)findViewById(R.id.iv_chat);
        iv_back=(ImageView)findViewById(R.id.iv_back);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        CircleImageView profilepic = (CircleImageView) headerview.findViewById(R.id.profilepic);
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
            }
        });
        TextView profilename = (TextView) headerview.findViewById(R.id.profilename);
        TextView location = (TextView) headerview.findViewById(R.id.location);
//        profilename.setText("your name");
        navigationView.setNavigationItemSelectedListener(this);
//        NavigationMenuView navMenuView = (NavigationMenuView) navigationView.getChildAt(0);
//        navMenuView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
switch (menuItem.getItemId()){
    case R.id.navigation_legal:
        break;
    case R.id.navigation_Licenses:
        break;
    case R.id.navigation_user_agreement:
        break;
    case R.id.navigation_logout:
        userSession.logout();
        Intent intent=new Intent(this,SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        break;
    case R.id.navigation_pws:
        startActivity(new Intent(this,ChangePasswordActivity.class));
        break;
    case R.id.navigation_tc:
        break;


}
        mDrawerLayout.closeDrawer(GravityCompat.END);
        return false;
    }
}
