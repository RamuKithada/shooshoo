package com.android.shooshoo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.presenters.SponsorChallengePresenter;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.views.SponsorChallengeView;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
/**
 * This is used to show Campaign screen of the sponsor challenge registration process
 *   {@link SponsorChallengePresenter} is used to call related services for this screen
 *   {@link SponsorChallengeView} is used by sponsorChallengePresenter to interact with this screen.
 *
 */
public class CampaignActivity extends BaseActivity implements SponsorChallengeView,View.OnClickListener, TextWatcher {

    @BindView(R.id.btn_next)
    TextView btn_next;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.iv_help)
    ImageView iv_help;

    @BindViews({R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5})
    List<Button> buttons;

    @BindView(R.id.tv_title)
    TextView title;



    @BindView(R.id.max_budget_per_day)
    TextView max_budget_per_day;

    @BindView(R.id.audience_size)
    TextView audience_size;

    @BindView(R.id.edt_budget_per_day)
    EditText edt_budget_per_day;

    @BindView(R.id.country_name)
    AppCompatTextView country_name;
    @BindView(R.id.city_name)
    AppCompatTextView city_name;
    @BindView(R.id.zip_code)
    AppCompatTextView zip_code;
    @BindView(R.id.category_title)
    AppCompatTextView category_title;
    @BindView(R.id.age_range)
    AppCompatTextView age_range;
    @BindView(R.id.gender)
    AppCompatTextView gender;
    @BindView(R.id.categories)
    AppCompatTextView categories;



    ConnectionDetector connectionDetector;
    SponsorChallengePresenter sponsorChallengePresenter;
    Challenge challenge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);
        ButterKnife.bind(this);
        btn_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_help.setOnClickListener(this);
        title.setText("Campaign");
        challenge=getIntent().getParcelableExtra("challenge");
        setStage(3);
        String audienceSize= userSession.getAudSize();
        audience_size.setText(audienceSize);
        this.connectionDetector = new ConnectionDetector(this);
        this.sponsorChallengePresenter = new SponsorChallengePresenter();
        this.sponsorChallengePresenter.attachView(this);

        if(challenge!=null) {
            country_name.setText(challenge.getCountryName());
            city_name.setText(challenge.getCityName());
            age_range.setText(challenge.getAgeStart() + " - " + challenge.getAgeEnd());
            StringBuilder builderCat=new StringBuilder();
            for (String name:challenge.getCategoryNames()) {
                if(builderCat.length()==0)
                    builderCat.append(name);
                else{
                    builderCat.append(',').append(name);
                }
            }
            categories.setText(builderCat.toString());
            zip_code.setText(challenge.getAudZipcode());
            gender.setText(challenge.getAudGender());
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_next:
                if (ApiUrls.validateString(edt_budget_per_day.getText().toString())) {
                    if (this.connectionDetector.isConnectingToInternet()) {
                        SponsorChallengePresenter sponsorChallengePresenter = this.sponsorChallengePresenter;
                        String sponsorChallenge = this.userSession.getSponsorChallenge();
                        sponsorChallengePresenter.saveCampaign(sponsorChallenge,userSession.getUserId(), edt_budget_per_day.getText().toString(), "");
                        return;
                    }
                    else
                    showMessage("Please check your Internet Connection");
                } else {
                    showMessage("Please select Average budget per day");
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_help:
               showMessage("Help");
                break;
        }
    }
    /**
     * setStage is for selection one of registration step
     * @param step is step of registration process of a challenge
     */
    private void setStage(int step) {
        for(int index=0;index<buttons.size();index++){
            if(index<=step){
                buttons.get(index).setBackgroundResource(R.drawable.selected);
                buttons.get(index).setText(String.valueOf(step+1));
            }else buttons.get(index).setBackgroundResource(R.drawable.unselected);

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCompanyRegister(Company company) {

    }

    @Override
    public void onChallengeResponse(Challenge challenge) {
        Intent intent = new Intent(this, ChallengePaymentActivity.class);
        intent.putExtra("challenge",challenge);
        startActivity(intent);
    }
}

