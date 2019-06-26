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
import com.android.shooshoo.adapter.BrandProfilePagerAdapter;
import com.android.shooshoo.adapter.ChallengerViewPagerAdapter;
import com.android.shooshoo.models.CompanyDetails;
import com.android.shooshoo.presenters.BrandDetailsPresenter;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.views.BrandProfileView;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * {@link BrandProfileActivity} is used to show the challenges of the related Brand
 *
 */
public class BrandProfileActivity extends BaseActivity implements BrandProfileView, View.OnClickListener {


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

    ConnectionDetector connectionDetector;
    BrandDetailsPresenter brandDetailsPresenter;
    BrandProfilePagerAdapter brandProfilePagerAdapter;
    TabLayout tabLayout=null;
    ViewPager viewPager=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_profile);
        ButterKnife.bind(this);
        connectionDetector=new ConnectionDetector(this);
        brandDetailsPresenter=new BrandDetailsPresenter();
        brandDetailsPresenter.attachView(this);
          tabLayout=findViewById(R.id.tab_layout);
          viewPager=findViewById(R.id.view_pager);
        //tab_layout colors settings
      /*  tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabLayout.setTabTextColors(Color.parseColor("#CCCCCC"), Color.parseColor("#ffffff"));
         brandProfilePagerAdapter=new BrandProfilePagerAdapter(this,getSupportFragmentManager(),new String[]{"Recent ","Finished ","Profile"},null);
        viewPager.setAdapter(brandProfilePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);*/
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

        if(connectionDetector.isConnectingToInternet()){
            brandDetailsPresenter.getBrandDetails("8");
         }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_categories:
//                Intent intent=new Intent(this, CreateSponsorChallengeActivity.class);
//                intent.putExtra("challenge_type",1);
//                startActivity(intent);
                break;
            case R.id.tv_follow:

                break;
            case R.id.iv_back:
                    finish();
                break;

        }
    }

    @Override
    public void onBrandDetails(CompanyDetails details) {
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabLayout.setTabTextColors(Color.parseColor("#CCCCCC"), Color.parseColor("#ffffff"));
        brandProfilePagerAdapter=new BrandProfilePagerAdapter(this,getSupportFragmentManager(),new String[]{"Recent ","Finished ","Profile"},details);
        viewPager.setAdapter(brandProfilePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        brandDetailsPresenter.detachView();
    }
}
