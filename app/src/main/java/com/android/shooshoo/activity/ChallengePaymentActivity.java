package com.android.shooshoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.shooshoo.R;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.presenters.SponsorChallengePresenter;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.views.SponsorChallengeView;
import java.util.List;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
/**{@link ChallengePaymentActivity} is used to show payment screen of the Jockpot challenge registration screen
 *  {@link SponsorChallengePresenter} is used to call related services for this screen
 *  {@link SponsorChallengeView} is used by sponsorChallengePresenter to interact with this screen.
 */
public class ChallengePaymentActivity extends BaseActivity implements SponsorChallengeView, View.OnClickListener {

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

   /* @BindView(R.id.budget_per_day)
    TextView budget_per_day;*/

    /*@BindView(R.id.edt_summary)
    EditText edt_summary;

    @BindView(R.id.tv_challenge_name)
    TextView tv_challenge_name;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;*/
   /* @BindView(R.id.tc_checkbox)
    CheckBox tc_checkbox;*/


    ConnectionDetector connectionDetector;
    SponsorChallengePresenter sponsorChallengePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_payment);
            ButterKnife.bind(this);
            btn_next.setOnClickListener(this);
            iv_back.setOnClickListener(this);
        iv_help.setOnClickListener(this);
            setStage(4);
            title.setText("Summary");
            this.connectionDetector = new ConnectionDetector(this);
        this.sponsorChallengePresenter = new SponsorChallengePresenter();
        this.sponsorChallengePresenter.attachView((SponsorChallengeView) this);
        if (getIntent().hasExtra("budget")) {
/*            int progress = getIntent().getIntExtra("budget", 0);
            TextView textView = this.budget_per_day;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("€ ");
            stringBuilder.append(progress);
            stringBuilder.append(" Average/Day");
            textView.setText(stringBuilder.toString());*/
        }
        Challenge company = this.userSession.getChallnge();
        if (company != null) {
//            this.tv_challenge_name.setText(company.getChallengeName());
//            this.tv_start_time.setText(company.getStartDate());
//            this.tv_end_time.setText(company.getEndDate());
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
             /*       if (this.connectionDetector.isConnectingToInternet()) {
                        SponsorChallengePresenter sponsorChallengePresenter = this.sponsorChallengePresenter;
                        String sponsorChallenge = this.userSession.getSponsorChallenge();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(getIntent().getIntExtra("budget", 0));
                        sponsorChallengePresenter.saveCampaign(sponsorChallenge,userSession.getUserId(), stringBuilder.toString(), this.edt_summary.getText().toString());
                        return;
                            }
                    showMessage("Please check your Internet Connection");*/
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
     * vlidations of the input fields of this screen
     *
     * @return
     */
    private boolean validate() {
//        if(!tc_checkbox.isChecked()){
//         showMessage("Please accept Terms and Conditions");
//         return false;
//        }
//
//        if (!ApiUrls.validateString(this.edt_summary.getText().toString())) {
//            this.edt_summary.requestFocus();
//            this.edt_summary.setError("enter summary for your challenge");
//            return false;
//        } else if (getIntent().getIntExtra("budget", 0) > 0) {
//            return true;
//        } else {
//            showMessage("Please set your budget per day");
//            return false;
//        }
//
return true;
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
