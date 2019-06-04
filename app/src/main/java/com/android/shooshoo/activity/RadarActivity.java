package com.android.shooshoo.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.RadarViewPagerAdapter;

public class RadarActivity extends BaseActivity {
    RadarViewPagerAdapter radarViewPagerAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);
        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_pager);
        ImageView iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabLayout.setTabTextColors(Color.parseColor("#CCCCCC"), Color.parseColor("#ffffff"));
        radarViewPagerAdapter=new RadarViewPagerAdapter(this,getSupportFragmentManager(),new String[]{"Distance","Start","End"});
        viewPager.setAdapter(radarViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
