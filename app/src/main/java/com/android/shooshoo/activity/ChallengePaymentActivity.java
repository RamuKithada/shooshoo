package com.android.shooshoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.presenters.SponcerChallengePresenter;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.views.SponsorChallengeView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class ChallengePaymentActivity extends BaseActivity implements SponsorChallengeView, View.OnClickListener {
    @BindView(R.id.btn_next)
    TextView btn_next;

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindViews({R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5})
    List<Button> buttons;

    @BindView(R.id.tv_title)
    TextView title;

    @BindView(R.id.budget_per_day)
    TextView budget_per_day;

    @BindView(R.id.edt_summary)
    EditText edt_summary;

    @BindView(R.id.tv_challenge_name)
    TextView tv_challenge_name;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;


    ConnectionDetector connectionDetector;
    SponcerChallengePresenter sponcerChallengePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_payment);
            ButterKnife.bind(this);
            btn_next.setOnClickListener(this);
            iv_back.setOnClickListener(this);
            setStage(4);
            title.setText("Payment");
            this.connectionDetector = new ConnectionDetector(this);
        this.sponcerChallengePresenter = new SponcerChallengePresenter();
        this.sponcerChallengePresenter.attachView((SponsorChallengeView) this);
        if (getIntent().hasExtra("budget")) {
            int progress = getIntent().getIntExtra("budget", 0);
            TextView textView = this.budget_per_day;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("€ ");
            stringBuilder.append(progress);
            stringBuilder.append(" Average/Day");
            textView.setText(stringBuilder.toString());
        }
        Challenge company = this.userSession.getChallnge();
        if (company != null) {
            this.tv_challenge_name.setText(company.getChallengeName());
            this.tv_start_time.setText(company.getStartDate());
            this.tv_end_time.setText(company.getEndDate());
        }
    }

    private void setStage(int i) {
        for(int index=0;index<buttons.size();index++){
            if(index==i){
                buttons.get(index).setBackgroundResource(R.drawable.selected);
                buttons.get(index).setText(String.valueOf(i+1));
            }
            else
                buttons.get(index).setBackgroundResource(R.drawable.unselected);

        }
    }

    public  void onClick(View view){
        switch (view.getId())
        {
            case R.id.btn_next:
                if(validate()){
                    if (this.connectionDetector.isConnectingToInternet()) {
                        SponcerChallengePresenter sponcerChallengePresenter = this.sponcerChallengePresenter;
                        String sponsorChallenge = this.userSession.getSponsorChallenge();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(getIntent().getIntExtra("budget", 0));
                        sponcerChallengePresenter.saveCampaign(sponsorChallenge,userSession.getUserId(), stringBuilder.toString(), this.edt_summary.getText().toString());
                        return;
                            }
                    showMessage("Please check your Internet Connection");
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }

    }

    private boolean validate() {
        if (!ApiUrls.validateString(this.edt_summary.getText().toString())) {
            this.edt_summary.requestFocus();
            this.edt_summary.setError("enter summary for your challenge");
            return false;
        } else if (getIntent().getIntExtra("budget", 0) > 0) {
            return true;
        } else {
            showMessage("Please set your budget per day");
            return false;
        }
    }

    @Override
    public void onCompanyRegister(Company company) {

    }

    @Override
    public void onChallengeResponse(Challenge company) {
        userSession.setSponsorChallenge(null);
        userSession.savaChallenge(null);
        Intent homeIntent=new Intent(this,HomeActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);

    }
}
