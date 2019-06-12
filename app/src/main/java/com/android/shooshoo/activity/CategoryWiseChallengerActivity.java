package com.android.shooshoo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.HomeCategoryAdapter;
import com.android.shooshoo.adapter.JackpotChallengersAdapter;
import com.android.shooshoo.adapter.WinnersListAdapter;
import com.android.shooshoo.models.ChallengeModel;
import com.android.shooshoo.utils.ClickListener;
import com.android.shooshoo.utils.RecyclerTouchListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * {@link CategoryWiseChallengerActivity} is used to show the all challenges of respective category of the home page category wise challenges.
 * this is called from home fragment
 *
 */

public class CategoryWiseChallengerActivity extends AppCompatActivity {


    RecyclerView challengesList;
    ImageView iv_back;


    @BindView(R.id.tv_title)
    TextView tv_title;
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
    String[] titles=new String[]{"Beard \nChallenge","Drink Challenge","Eating Challenge","Handstand Challenge","Hips Exercise Challenge",
            "Ice Skating Challenge","Laugh Challenge","Pullups Challenge","Running Challenge","Yoga Challenge","BlackFly ","Closeup smile ","Dance music ","Drink Challenge","Holiday Challenge",
            "Hotel Challenge","Ice Bucket Challenge","Swimming Challenge","World music Contest","Young Challenge"};
    int[] images=new int[]{R.drawable.beard_challange,R.drawable.drink_challange,R.drawable.eating_challange,R.drawable.handstand_challange,
            R.drawable.hips_excersize_chalange,R.drawable.iceskating_challange,R.drawable.laugh_challange,R.drawable.pullup_challange,R.drawable.running_challange
            ,R.drawable.yoga_challange,R.drawable.blackfly_challange,R.drawable.closeup_smile_challange,R.drawable.dance_music_challange,R.drawable.drinks_challange,
            R.drawable.holiday_challange,R.drawable.hotel_challange,R.drawable.icebucket_challange,R.drawable.swimmimg_challange,R.drawable.world_music_contest
            ,R.drawable.young_challange};
    String[] des=new String[]{"Large Beard","Drink 2 Liters coke","Eating 2 Biryani","1 hour Handstand ","100 HipsUps",
            "1 kilometer Ice Skating in 2 minutes","Laugh loud ","30 Pullups in 5minutes","2k Running in 90sec","5 hours Yoga",
            "BlackFly bird capture","Closeup smile ads","Dance music to Puma","Drink  Coke ads","Holiday Trip flight",
            "Hotel Banjara","Ice Bucket Challenge","World Swimming Day","World music Day","Young India "};

    int[] catimgs=new int[]{
            R.drawable.animals,R.drawable.art,R.drawable.cars,R.drawable.comics,
            R.drawable.electronics,R.drawable.fitness,R.drawable.games,R.drawable.humor,R.drawable.movie,R.drawable.shopping,
            R.drawable.style,R.drawable.travel,R.drawable.animals,R.drawable.art,R.drawable.cars,R.drawable.comics,
            R.drawable.electronics,R.drawable.fitness,R.drawable.games,R.drawable.humor,R.drawable.movie,R.drawable.shopping,
            R.drawable.style,R.drawable.travel,R.drawable.animals,R.drawable.art,R.drawable.cars,R.drawable.comics,
            R.drawable.electronics,R.drawable.fitness,R.drawable.games,R.drawable.humor,R.drawable.movie,R.drawable.shopping,
            R.drawable.style,R.drawable.travel,R.drawable.animals,R.drawable.art,R.drawable.cars,R.drawable.comics,
            R.drawable.electronics,R.drawable.fitness,R.drawable.games,R.drawable.humor,R.drawable.movie,R.drawable.shopping,
            R.drawable.style,R.drawable.travel
    };
    String[] catNames=new String[]{
            "Animals","Art","Cars","Comics","Electronics",
            "Fitness","Games","Humor","Movies","Shopping",
            "Style","Travel", "Animals","Art","Cars","Comics","Electronics",
            "Fitness","Games","Humor","Movies","Shopping",
            "Style","Travel", "Animals","Art","Cars","Comics","Electronics",
            "Fitness","Games","Humor","Movies","Shopping",
            "Style","Travel", "Animals","Art","Cars","Comics","Electronics",
            "Fitness","Games","Humor","Movies","Shopping",
            "Style","Travel"
    };
    
    ArrayList<ChallengeModel> challengeModels=new ArrayList<ChallengeModel>();
    JackpotChallengersAdapter jackpotChallengersAdapter;
    private View.OnClickListener bottomNavigationOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(CategoryWiseChallengerActivity.this,HomeActivity.class);
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
    private HomeCategoryAdapter homeCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise_challenger);
        ButterKnife.bind(this);
        challengesList = findViewById(R.id.winner_list);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        for (int index = 0; index < titles.length; index++) {
            ChallengeModel model = new ChallengeModel();
            model.setDescription(des[index]);
            model.setTitle(titles[index]);
            model.setImage(images[index]);
            challengeModels.add(model);
        }
        String title = getIntent().getStringExtra("title");
        if (title != null)
            tv_title.setText(title);
        int catId = getIntent().getIntExtra("catId", 0);
        if (catId == 3) {
            challengesList.setLayoutManager(new GridLayoutManager(this,4));
            homeCategoryAdapter=new HomeCategoryAdapter(catimgs,catNames);
            challengesList.setAdapter(homeCategoryAdapter);
            
        } else {
            jackpotChallengersAdapter = new JackpotChallengersAdapter(challengeModels);
            challengesList.setAdapter(jackpotChallengersAdapter);
            navigation_home.setOnClickListener(bottomNavigationOnClickListener);
            navigation_challengers.setOnClickListener(bottomNavigationOnClickListener);
            navigation_feed.setOnClickListener(bottomNavigationOnClickListener);
            navigation_winners.setOnClickListener(bottomNavigationOnClickListener);
            navigation_radar.setOnClickListener(bottomNavigationOnClickListener);
            challengesList.addOnItemTouchListener(new RecyclerTouchListener(this, challengesList, new ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Intent intent = new Intent(CategoryWiseChallengerActivity.this, MyChallengesActivity.class);
                    intent.putExtra("image", challengeModels.get(position).getImage());
                    intent.putExtra("name", challengeModels.get(position).getTitle());
                    intent.putExtra("des", challengeModels.get(position).getDescription());
                    startActivity(intent);
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
        }
    }
}
