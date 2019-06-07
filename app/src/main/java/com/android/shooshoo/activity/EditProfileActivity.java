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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.ChallengerViewPagerAdapter;
import com.android.shooshoo.adapter.ProfileViewPagerAdapter;
import com.android.shooshoo.utils.FragmentListDialogListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentListDialogListener {
    DrawerLayout mDrawerLayout;
    ImageView iv_chat;
    ImageView iv_back;

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
    private View.OnClickListener bottomNavigationOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(EditProfileActivity.this,HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            switch (v.getId()) {
                case R.id.navigation_home:
                    intent.putExtra("icon",0);
                    break;
                case R.id.navigation_challengers:
                    intent.putExtra("icon",1);
                    break;
                case R.id.navigation_feed:
                    intent.putExtra("icon",2);
                    break;
                case R.id.navigation_winners:
                    intent.putExtra("icon",3);
                    break;
                case R.id.navigation_radar:
                    intent.putExtra("icon",4);
                    break;
            }
            startActivity(intent);
            finish();

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
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
        navigationView.setNavigationItemSelectedListener(this);
;
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
        navigation_home.setOnClickListener(bottomNavigationOnClickListener);
        navigation_challengers.setOnClickListener(bottomNavigationOnClickListener);
        navigation_feed.setOnClickListener(bottomNavigationOnClickListener);
        navigation_winners.setOnClickListener(bottomNavigationOnClickListener);
        navigation_radar.setOnClickListener(bottomNavigationOnClickListener);
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
        finishAffinity();
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

    @Override
    public void onEditView(int view, int pos) {

    }
}
