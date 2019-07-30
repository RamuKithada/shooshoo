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
import com.android.shooshoo.adapter.SponsorChallengersAdapter;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.models.CompanyDetails;
import com.android.shooshoo.presenters.BrandDetailsPresenter;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.views.BrandProfileView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CompanyDetailsActivity extends BaseActivity implements BrandProfileView {
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

    @BindView(R.id.rv_challenge_list)
    RecyclerView rv_challenge_list;

    @BindView(R.id.save_brand)
    ImageView save_brand;

    @BindView(R.id.sub_title)
    TextView sub_title;

    @BindView(R.id.company_name)
    TextView company_name;

    @BindView(R.id.tv_link)
    TextView tv_link;
    //  sponsor challengeModels is used to hold jackpot challenges list
    ArrayList<Challenge> sponsorChallenges=new ArrayList<Challenge>();

    //Sponsor challenge List adapter
    SponsorChallengersAdapter sponsorChallengersAdapter;

    ConnectionDetector connectionDetector;
    BrandDetailsPresenter brandDetailsPresenter;
    Company company=null;

    private View.OnClickListener bottomNavigationOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(CompanyDetailsActivity.this,HomeActivity.class);
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
        setContentView(R.layout.activity_company_details);
        ButterKnife.bind(this);
        connectionDetector=new ConnectionDetector(this);
        brandDetailsPresenter=new BrandDetailsPresenter();
        brandDetailsPresenter.attachView(this);
        company=getIntent().getParcelableExtra("brand");

        navigation_home.setOnClickListener(bottomNavigationOnClickListener);
        navigation_challengers.setOnClickListener(bottomNavigationOnClickListener);
        navigation_feed.setOnClickListener(bottomNavigationOnClickListener);
        navigation_winners.setOnClickListener(bottomNavigationOnClickListener);
        navigation_radar.setOnClickListener(bottomNavigationOnClickListener);
        sponsorChallengersAdapter=new SponsorChallengersAdapter(this,sponsorChallenges);
        rv_challenge_list.setLayoutManager(new GridLayoutManager(this, 2));
        rv_challenge_list.setAdapter(sponsorChallengersAdapter);
        if(company!=null)
        setDetails(company);

    }

    private void setDetails(Company company) {
        if(connectionDetector.isConnectingToInternet()){
            brandDetailsPresenter.getBrandDetails(company.getCompanyId());
        }
        company_name.setText(company.getCompanyName());
        sub_title.setText(company.describeContents());
        tv_link.setText(company.getCompanyEmail());


    }

    @Override
    public void onBrandDetails(CompanyDetails details) {
          if(details.getStatus()==1){
              if(details.getChallenges()!=null)
              {
                  sponsorChallenges.addAll(details.getChallenges());
                  sponsorChallengersAdapter.notifyDataSetChanged();
              }

          }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        brandDetailsPresenter.detachView();
    }

}
