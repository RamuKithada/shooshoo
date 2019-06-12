package com.android.shooshoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class CreateSponsorChallengeActivity extends BaseActivity implements View.OnClickListener {

    /**
     * {@link CreateSponsorChallengeActivity} is use to show the create challenge  screen in process
      * sponsor or jackpot challenge registration .This is the first step.
     */

    @BindView(R.id.btn_next)
    TextView btn_next;

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindViews({R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5})
    List<Button> buttons;

    @BindView(R.id.tv_title)
    TextView title;

    @BindView(R.id.jackpot_challenge_guide)
    CardView jackpot_challenge_guide;

    @BindView(R.id.sponsor_challenge_guide)
    CardView sponsor_challenge_guide;

    int challengeType=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);
        ButterKnife.bind(this);
        btn_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);


        setStage(0);
        title.setText("Create a Challenge");
      challengeType=getIntent().getIntExtra("challenge_type",0);
      if(challengeType==1){
            sponsor_challenge_guide.setVisibility(View.VISIBLE);
            jackpot_challenge_guide.setVisibility(View.GONE);
      }else if(challengeType==2){
          sponsor_challenge_guide.setVisibility(View.GONE);
          jackpot_challenge_guide.setVisibility(View.VISIBLE);
      }


    }
    /**
     * setStage is for selection one of registration step
     * @param step is step of registration process of a challenge
     */
    private void setStage(int step) {
        for(int index=0;index<buttons.size();index++){
            if(index==step){
                buttons.get(index).setBackgroundResource(R.drawable.selected);
            }else buttons.get(index).setBackgroundResource(R.drawable.unselected);

        }
    }

    public  void onClick(View view){
        switch (view.getId())
        {
            case R.id.btn_next:
                if(challengeType==1)
                    startActivity(new Intent(this, CompanyProfileActivity.class));
                else if(challengeType==2)
                    startActivity(new Intent(this, GameMasterActivity.class));
            break;
            case R.id.iv_back:
                finish();
                break;
        }

    }
}
