package com.android.shooshoo.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.RadarViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RadarActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    RadarViewPagerAdapter radarViewPagerAdapter;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.distance_lay)
    LinearLayout distance_lay;

    @BindView(R.id.start_lay)
    LinearLayout start_lay;

    @BindView(R.id.end_lay)
    LinearLayout end_lay;

    @BindView(R.id.iv_back)
    ImageView iv_back;


    View.OnClickListener listChangeListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                switch (v.getId()){
                    case R.id.distance_lay:
                        viewPager.setCurrentItem(0,true);
                        break;
                    case R.id.start_lay:
                        viewPager.setCurrentItem(1,true);
                        break;
                    case R.id.end_lay:
                        viewPager.setCurrentItem(2,true);
                        break;

                }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);
        ButterKnife.bind(this);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        end_lay.setOnClickListener(listChangeListener);
        start_lay.setOnClickListener(listChangeListener);
        distance_lay.setOnClickListener(listChangeListener);
        radarViewPagerAdapter=new RadarViewPagerAdapter(this,getSupportFragmentManager(),new String[]{"Distance","Start","End"});
        viewPager.setAdapter(radarViewPagerAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
           onListPageSelected(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
    public void onListPageSelected(int pos) {
        ((TextView) distance_lay.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
        distance_lay.getChildAt(1).setBackgroundColor(Color.parseColor("#85868A"));

        ((TextView) start_lay.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
        start_lay.getChildAt(1).setBackgroundColor(Color.parseColor("#85868A"));


        ((TextView) end_lay.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
        end_lay.getChildAt(1).setBackgroundColor(Color.parseColor("#85868A"));

        switch (pos){
            case 0:
                ((TextView) distance_lay.getChildAt(0)).setTextColor(Color.parseColor("#F31F68"));
                distance_lay.getChildAt(1).setBackgroundColor(Color.parseColor("#F31F68"));
                break;
            case 1:
                ((TextView) start_lay.getChildAt(0)).setTextColor(Color.parseColor("#F31F68"));
                start_lay.getChildAt(1).setBackgroundColor(Color.parseColor("#F31F68"));
                break;
            case 2:
                ((TextView) end_lay.getChildAt(0)).setTextColor(Color.parseColor("#F31F68"));
                end_lay.getChildAt(1).setBackgroundColor(Color.parseColor("#F31F68"));
                break;
        }
    }
}
