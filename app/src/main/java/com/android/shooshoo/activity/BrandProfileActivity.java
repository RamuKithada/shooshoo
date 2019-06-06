package com.android.shooshoo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.ChallengerViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrandProfileActivity extends BaseActivity implements View.OnClickListener {


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
            Intent intent=new Intent(BrandProfileActivity.this,HomeActivity.class);
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
        setContentView(R.layout.activity_brand_profile);
        ButterKnife.bind(this);
        final TabLayout tabLayout=findViewById(R.id.tab_layout);
        final ViewPager viewPager=findViewById(R.id.view_pager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabLayout.setTabTextColors(Color.parseColor("#CCCCCC"), Color.parseColor("#ffffff"));
        ChallengerViewPagerAdapter challengerViewPagerAdapter=new ChallengerViewPagerAdapter(this,getSupportFragmentManager(),new String[]{"Recent ","Finished ","Profile"});
        viewPager.setAdapter(challengerViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        TextView tv_categories=findViewById(R.id.tv_categories);
        TextView tv_follow=findViewById(R.id.tv_follow);
        tv_categories.setOnClickListener(this);
        tv_follow.setOnClickListener(this);
        ImageView back=findViewById(R.id.iv_back);
        back.setOnClickListener(this);
        navigation_home.setOnClickListener(bottomNavigationOnClickListener);
        navigation_challengers.setOnClickListener(bottomNavigationOnClickListener);
        navigation_feed.setOnClickListener(bottomNavigationOnClickListener);
        navigation_winners.setOnClickListener(bottomNavigationOnClickListener);
        navigation_radar.setOnClickListener(bottomNavigationOnClickListener);

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this, CreateSponsorChallengeActivity.class);
        switch (v.getId()) {
            case R.id.tv_categories:
                intent.putExtra("challenge_type",1);
//                startActivity(intent);
                break;
            case R.id.tv_follow:
                intent.putExtra("challenge_type",2);
//                startActivity(intent);
                break;
            case R.id.iv_back:
                    finish();
                break;

        }
    }
}
