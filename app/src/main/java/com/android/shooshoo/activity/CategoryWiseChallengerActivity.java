package com.android.shooshoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.HomeCategoryAdapter;
import com.android.shooshoo.models.CatResult;
import com.android.shooshoo.models.Category;
import com.android.shooshoo.utils.RetrofitApis;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * {@link CategoryWiseChallengerActivity} is used to show the all challenges of respective category of the home page category wise challenges.
 * this is called from home fragment
 *
 */

public class CategoryWiseChallengerActivity extends BaseActivity {


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

    
    ArrayList<Category> challengeModels=new ArrayList<Category>();
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
        String title = getIntent().getStringExtra("title");
        if (title != null)
            tv_title.setText(title);
        int catId = getIntent().getIntExtra("catId", 0);
        if (catId == 3) {
            challengesList.setLayoutManager(new GridLayoutManager(this,4));
            homeCategoryAdapter=new HomeCategoryAdapter(this,challengeModels);
            challengesList.setAdapter(homeCategoryAdapter);
            loadCategory(0);
            navigation_home.setOnClickListener(bottomNavigationOnClickListener);
            navigation_challengers.setOnClickListener(bottomNavigationOnClickListener);
            navigation_feed.setOnClickListener(bottomNavigationOnClickListener);
            navigation_winners.setOnClickListener(bottomNavigationOnClickListener);
            navigation_radar.setOnClickListener(bottomNavigationOnClickListener);
            
        } /*else {
            jackpotChallengersAdapter = new JackpotChallengersAdapter(this,null);
            challengesList.setAdapter(jackpotChallengersAdapter);

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
        }*/
    }


    private void loadCategory(int offset) {
        showProgressIndicator(true);
        RetrofitApis.Factory.create(this).getCategories("100",""+offset).enqueue(new Callback<CatResult>() {
            @Override
            public void onResponse(Call<CatResult> call, Response<CatResult> response) {
                showProgressIndicator(false);
                if(response.isSuccessful()){
                    CatResult catResult=response.body();
                    if(catResult.getCategories()!=null)
                    challengeModels.addAll(catResult.getCategories());
                    homeCategoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CatResult> call, Throwable t) {
                showProgressIndicator(false);

            }
        });

    }
}
