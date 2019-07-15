package com.android.shooshoo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.shooshoo.R;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
/**
 * This is used to show Campaign screen of the sponsor challenge registration process
 *
 */
public class CampaignActivity extends BaseActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.btn_next)
    TextView btn_next;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindViews({R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5})
    List<Button> buttons;

    @BindView(R.id.tv_title)
    TextView title;

    @BindView(R.id.seekbar)
    AppCompatSeekBar appCompatSeekBar;

    @BindView(R.id.budget_per_day)
    TextView budget_per_day;

    @BindView(R.id.audience_size)
    TextView audience_size;




        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            TextView textView = CampaignActivity.this.budget_per_day;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("€ ");
            stringBuilder.append(progress);
            stringBuilder.append(" Average/Day");
            textView.setText(stringBuilder.toString());
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);
        ButterKnife.bind(this);
        btn_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        title.setText("Campaign");
        setStage(3);
        this.appCompatSeekBar.setOnSeekBarChangeListener(this   );
        appCompatSeekBar.setProgress(5);
        String audienceSize= userSession.getAudSize();
        audience_size.setText(audienceSize);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_next:
                if (this.appCompatSeekBar.getProgress() > 0) {
                    Intent intent = new Intent(this, ChallengePaymentActivity.class);
                    intent.putExtra("budget", this.appCompatSeekBar.getProgress());
                    startActivity(intent);
                } else {
                    showMessage("Please select Average budget per day");
                }
                break;
            case R.id.iv_back:
                finish();
                break;
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
                buttons.get(index).setText(String.valueOf(step+1));
            }else buttons.get(index).setBackgroundResource(R.drawable.unselected);

        }
    }
}

