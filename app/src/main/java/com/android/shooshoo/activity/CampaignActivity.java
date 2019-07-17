package com.android.shooshoo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.utils.ApiUrls;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
/**
 * This is used to show Campaign screen of the sponsor challenge registration process
 *
 */
public class CampaignActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

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






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);
        ButterKnife.bind(this);
        btn_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_help.setOnClickListener(this);
        title.setText("Campaign");
        setStage(3);
        String audienceSize= userSession.getAudSize();
        audience_size.setText(audienceSize);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_next:
                if (ApiUrls.validateString(edt_budget_per_day.getText().toString())) {
                    Intent intent = new Intent(this, ChallengePaymentActivity.class);
                    intent.putExtra("budget", edt_budget_per_day.getText().toString());
                    startActivity(intent);
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
}

