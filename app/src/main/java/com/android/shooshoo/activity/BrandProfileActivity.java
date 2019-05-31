package com.android.shooshoo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.ChallengerViewPagerAdapter;

public class BrandProfileActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_profile);

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
