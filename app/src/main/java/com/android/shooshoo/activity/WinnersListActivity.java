package com.android.shooshoo.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.WinnersListAdapter;
import com.android.shooshoo.fragment.ChallengersFragment;
import com.android.shooshoo.fragment.FeedFragment;
import com.android.shooshoo.fragment.HomeFragment;
import com.android.shooshoo.fragment.RadarFragment;
import com.android.shooshoo.fragment.WinnersFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WinnersListActivity extends AppCompatActivity {
WinnersListAdapter listAdapter;
RecyclerView winnersList;
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
            Intent intent=new Intent(WinnersListActivity.this,HomeActivity.class);
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

        setContentView(R.layout.activity_winners_list);
        ButterKnife.bind(this);
        winnersList=findViewById(R.id.winner_list);
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listAdapter=new WinnersListAdapter(this);
        winnersList.setAdapter(listAdapter);
        navigation_home.setOnClickListener(bottomNavigationOnClickListener);
        navigation_challengers.setOnClickListener(bottomNavigationOnClickListener);
        navigation_feed.setOnClickListener(bottomNavigationOnClickListener);
        navigation_winners.setOnClickListener(bottomNavigationOnClickListener);
        navigation_radar.setOnClickListener(bottomNavigationOnClickListener);

    }
}
