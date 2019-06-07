package com.android.shooshoo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.shooshoo.R;
import java.util.List;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class AudienceInstructionActivity extends AppCompatActivity {
@BindView(R.id.btn_next)
    TextView btn_next;
@BindView(R.id.sponsor_challenge_guide)
CardView sponsor_challenge_guide;
    @BindViews({R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5})
    List<Button> buttons;
    @BindView(R.id.jackpot_challenge_guide)
    CardView jackpot_challenge_guide;
    @BindView(R.id.tv_title)
    TextView title;

    int type=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audience_instruction);
        ButterKnife.bind(this);

        setStage(2);
        title.setText("Audience");

               if(getIntent().hasExtra("challenge_type"))
        type=getIntent().getIntExtra("challenge_type",0);
        if(type==1){
            sponsor_challenge_guide.setVisibility(View.VISIBLE);
            jackpot_challenge_guide.setVisibility(View.GONE);
        }else if(type==2){
            sponsor_challenge_guide.setVisibility(View.GONE);
            jackpot_challenge_guide.setVisibility(View.VISIBLE);

        }
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(type==1){
                    Intent intent=new Intent(AudienceInstructionActivity.this,AudienceActivity.class);
                    startActivity(intent);
                }else if(type==2){
                    Intent intent=new Intent(AudienceInstructionActivity.this,JackpotAudienceActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void setStage(int i) {
        for(int index=0;index<buttons.size();index++){
            if(index==i){
                buttons.get(index).setBackgroundResource(R.drawable.selected);
            }else buttons.get(index).setBackgroundResource(R.drawable.unselected);

        }
    }
}
