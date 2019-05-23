package com.android.shooshoo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class TheChallengeActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.btn_next)
    TextView btn_next;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindViews({R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5})
    List<Button> buttons;

    @BindView(R.id.tv_title)
    TextView title;
    int challengeType=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_challenge);
        ButterKnife.bind(this);
        btn_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        title.setText("The Challenge");
        setStage(1);

        challengeType=getIntent().getIntExtra("challenge_type",0);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_next:
                if(challengeType==1)
                    startActivity(new Intent(this,SponsorChallengeFormActivity.class));
                else if(challengeType==2){
                    startActivity(new Intent(this,JackpotChallengeFormActivity.class));
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
    private void setStage(int i) {
        for(int index=0;index<buttons.size();index++){
            if(index==i){
                buttons.get(index).setBackgroundResource(R.drawable.selected);
            }else buttons.get(index).setBackgroundResource(R.drawable.unselected);

        }
    }
}
