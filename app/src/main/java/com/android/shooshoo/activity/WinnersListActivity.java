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
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.WinnersListAdapter;
import com.android.shooshoo.fragment.ChallengersFragment;
import com.android.shooshoo.fragment.FeedFragment;
import com.android.shooshoo.fragment.HomeFragment;
import com.android.shooshoo.fragment.RadarFragment;
import com.android.shooshoo.fragment.WinnersFragment;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.Winner;
import com.android.shooshoo.presenters.WinnersPresenter;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.WinnersListView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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
        navigation_home.setOnClickListener(bottomNavigationOnClickListener);
        navigation_challengers.setOnClickListener(bottomNavigationOnClickListener);
        navigation_feed.setOnClickListener(bottomNavigationOnClickListener);
        navigation_winners.setOnClickListener(bottomNavigationOnClickListener);
        navigation_radar.setOnClickListener(bottomNavigationOnClickListener);
        winnersPresenter=new WinnersPresenter();
        winnersPresenter.attachView(this);
        connectionDetector=new ConnectionDetector(this);
        if(connectionDetector.isConnectingToInternet()){
            winnersPresenter.getWinnersListForTheChallenge((Challenge) getIntent().getParcelableExtra("challenge"),getIntent().getStringExtra("type"));

        }

    }

    @Override
    public void onWinnersListresult(List<Winner> winners) {
        if(winners!=null){
            if(winners.size()>3){
               topWinners.add(winners.remove(0));
               topWinners.add(winners.remove(1));
               topWinners.add(winners.remove(2));
               this.winners=winners;
               if(topWinners.size()>0){
                   Winner first=topWinners.get(0);
                   first_winner.setVisibility(View.VISIBLE);
                   first_winner_name.setText(first.getUserName());
                   Picasso.with(this).load(PROFILE_IMAGE_URL+first.getImage()).error(R.drawable.profile_1).into(first_winner_image);

               }  if(topWinners.size()>1){
                   Winner second=topWinners.get(1);
                   second_winner.setVisibility(View.VISIBLE);
                   second_winner_name.setText(second.getUserName());
                   Picasso.with(this).load(PROFILE_IMAGE_URL+second.getImage()).error(R.drawable.profile_1).into(second_winner_image);

               }  if(topWinners.size()>2){
                   Winner third=topWinners.get(2);
                   third_winner.setVisibility(View.VISIBLE);
                   third_winner_name.setText(third.getUserName());
                   Picasso.with(this).load(PROFILE_IMAGE_URL+third.getImage()).error(R.drawable.profile_1).into(third_winner_image);

               }

            }else {
                topWinners=winners;
            }



        }


    }
}
