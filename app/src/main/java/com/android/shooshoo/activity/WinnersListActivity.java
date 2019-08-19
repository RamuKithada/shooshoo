package com.android.shooshoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.shooshoo.R;
import com.android.shooshoo.adapter.WinnersListAdapter;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.Winner;
import com.android.shooshoo.presenters.WinnersPresenter;
import com.android.shooshoo.utils.ClickListener;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.RecyclerTouchListener;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.WinnersListView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.android.shooshoo.utils.ApiUrls.PROFILE_IMAGE_URL;

/**
 * {@link WinnersListActivity} show the list of all winner participated in challenge
 * {@link WinnersListAdapter} is used to show the list;
 */
public class WinnersListActivity extends BaseActivity implements WinnersListView {
    ConnectionDetector connectionDetector;
    UserSession userSession;
    WinnersPresenter winnersPresenter;


WinnersListAdapter listAdapter;
RecyclerView winnersList;
ImageView iv_back;
List<Winner> winners=new ArrayList<Winner>();
List<Winner> topWinners=new ArrayList<Winner>();
@BindView(R.id.first_winner)
LinearLayout first_winner;
@BindView(R.id.first_winner_image)
    CircleImageView first_winner_image;
@BindView(R.id.first_winner_name)
    TextView first_winner_name;
@BindView(R.id.first_winner_prize)
TextView first_winner_prize;

@BindView(R.id.second_winner)
LinearLayout second_winner;
@BindView(R.id.second_winner_image)
    CircleImageView second_winner_image;
@BindView(R.id.second_winner_name)
    TextView second_winner_name;
@BindView(R.id.second_winner_prize)
TextView second_winner_prize;

@BindView(R.id.third_winner)
LinearLayout third_winner;
@BindView(R.id.third_winner_image)
    CircleImageView third_winner_image;
@BindView(R.id.third_winner_name)
    TextView third_winner_name;
@BindView(R.id.third_winner_prize)
TextView third_winner_prize;


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
        listAdapter=new WinnersListAdapter(this,winners);
        winnersList.setAdapter(listAdapter);
        winnersList.addOnItemTouchListener(new RecyclerTouchListener(this, winnersList, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                openProfile(listAdapter.getItem(position).getUserId(),"0");

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        navigation_home.setOnClickListener(bottomNavigationOnClickListener);
        navigation_challengers.setOnClickListener(bottomNavigationOnClickListener);
        navigation_feed.setOnClickListener(bottomNavigationOnClickListener);
        navigation_winners.setOnClickListener(bottomNavigationOnClickListener);
        navigation_radar.setOnClickListener(bottomNavigationOnClickListener);
        winnersPresenter=new WinnersPresenter();
        winnersPresenter.attachView(this);
        connectionDetector=new ConnectionDetector(this);
        if(connectionDetector.isConnectingToInternet()){
            winnersPresenter.getWinnersListForTheChallenge((Challenge) getIntent().getParcelableExtra("challenge"));

        }

    }

    @Override
    public void onWinnersListresult(List<Winner> winners) {
        if(winners!=null){
            if(winners.size()>3){
                topWinners=winners.subList(0,3);

                try {
                    List<Winner> remainingList=winners.subList(3,winners.size());
                    if(remainingList!=null)
                    this.winners.addAll(remainingList);
                }catch (Exception e){

                }

                listAdapter.notifyDataSetChanged();
            }else {
                topWinners=winners;
            }

            if(topWinners.size()>=1){
                final Winner first=topWinners.get(0);
                first_winner.setVisibility(View.VISIBLE);
                first_winner_name.setText(first.getUserName());
                first_winner_prize.setText(first.getViews()+" Views ");
                Picasso.with(this).load(PROFILE_IMAGE_URL+first.getImage()).error(R.drawable.error).into(first_winner_image);

                first_winner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        openProfile(first.getUserId(),"0");

                    }
                });

            }  if(topWinners.size()>=2){
                final Winner second=topWinners.get(1);
                second_winner.setVisibility(View.VISIBLE);
                second_winner_name.setText(second.getUserName());
                second_winner_prize.setText(second.getViews()+" Views ");
                Picasso.with(this).load(PROFILE_IMAGE_URL+second.getImage()).error(R.drawable.error).into(second_winner_image);

                second_winner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        openProfile(second.getUserId(),"0");

                    }
                });

            }  if(topWinners.size()>=3){
                final Winner third=topWinners.get(2);
                third_winner.setVisibility(View.VISIBLE);
                third_winner_name.setText(third.getUserName());
                third_winner_prize.setText(third.getViews()+" Views ");
                Picasso.with(this).load(PROFILE_IMAGE_URL+third.getImage()).error(R.drawable.error).into(third_winner_image);

                third_winner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        openProfile(third.getUserId(),"0");

                    }
                });

            }

        }


    }

    public void openProfile(String userId,String status)
    {
        Intent userProfileIntent=new Intent(this, UserProfileActivity.class);
        userProfileIntent.putExtra("userId",userId);
        userProfileIntent.putExtra("follow",status);
        startActivity(userProfileIntent);
    }
}
