package com.android.shooshoo.activity;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.utils.ApiUrls;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeSearchActivity extends AppCompatActivity {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    int[] images=new int[]{R.drawable.ic_filter_icon,R.drawable.lastname_normal,R.mipmap.challenges_normal,R.drawable.save_icon};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        ButterKnife.bind(this);
        for(int i=0;i<4;i++){
            View view= LayoutInflater.from(this).inflate(R.layout.custome_tab,null);
            ImageView image=view.findViewById(R.id.tab_image);
            Picasso.with(HomeSearchActivity.this).load(images[i]).into(image);
            tabLayout.addTab(tabLayout.newTab().setCustomView(image));
        }
    }
}
