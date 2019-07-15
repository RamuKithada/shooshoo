package com.android.shooshoo.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.fragment.NotificationSettingFragment;
import com.android.shooshoo.fragment.ProfileSettingFragment;
import com.android.shooshoo.fragment.ProfileVisibilityFragment;
import com.android.shooshoo.fragment.ViewAllChallenges_Fragment;
import com.android.shooshoo.utils.ConnectionDetector;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.shooshoo.fragment.ViewAllChallenges_Fragment.SERVICE_TYPE;

public class ViewAllChallengesActivity extends BaseActivity implements View.OnClickListener {

    DrawerLayout mDrawerLayout;
    ImageView iv_filter;
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

    @BindView(R.id.filter_type)
    TextView filter_type;
    @BindView(R.id.filter_prize)
    TextView filter_prize;
    @BindView(R.id.filter_entry)
    TextView filter_entry;
    @BindView(R.id.filter_time)
    TextView filter_time;
    @BindView(R.id.filter_category)
    TextView filter_category;
    @BindView(R.id.filter_language)
    TextView filter_language;
    @BindView(R.id.filter_interaction)
    TextView filter_interaction;

    @BindView(R.id.tv_title)
    AppCompatTextView tv_title;
    ViewAllChallenges_Fragment viewAllChallenges_fragment;

    private View.OnClickListener bottomNavigationOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(ViewAllChallengesActivity.this,HomeActivity.class);
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
        setContentView(R.layout.activity_view_all_challenges);
        ButterKnife.bind(this);
        init();
    }

    private void init()
    {
        tv_title.setText(getIntent().getStringExtra("title"));
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        iv_filter=(ImageView)findViewById(R.id.iv_filter);
        iv_back=(ImageView)findViewById(R.id.iv_back);

        iv_filter.setOnClickListener(new View.OnClickListener() {
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

        filter_type.setOnClickListener(this);
        filter_prize.setOnClickListener(this);
        filter_entry.setOnClickListener(this);
        filter_time.setOnClickListener(this);
        filter_category.setOnClickListener(this);
        filter_language.setOnClickListener(this);
        filter_interaction.setOnClickListener(this);
        viewAllChallenges_fragment=ViewAllChallenges_Fragment.newInstance(getIntent().getStringExtra(SERVICE_TYPE));
        getSupportFragmentManager().beginTransaction().add(R.id.viewall_fragment_container,viewAllChallenges_fragment).commit();
    }

    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case R.id.filter_type:
                    tv_title.setText("Filter type");
                    break;

                case R.id.filter_prize:
                    tv_title.setText("filter_prize");
                    break;

                case R.id.filter_entry:
                    break;
                case R.id.filter_time:
                    break;
                case R.id.filter_category:
                    break;
                case R.id.filter_language:
                    break;
                case R.id.filter_interaction:

                    break;
            }
            mDrawerLayout.closeDrawer(GravityCompat.END);
    }

}
