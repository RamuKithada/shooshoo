package com.android.shooshoo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.shooshoo.R;

import butterknife.BindView;

public class SponsorChallenge extends AppCompatActivity implements View.OnClickListener{
LinearLayout privateChallenge,comapanyRegiser;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_challenge);
        privateChallenge=findViewById(R.id.private_sponsor);
        comapanyRegiser=findViewById(R.id.company_register);
        privateChallenge.setOnClickListener(this);
        comapanyRegiser.setOnClickListener(this);
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.private_sponsor:
                Intent privateSponsor=new Intent(this,SponsorChallengeFormActivity.class);
                privateSponsor.putExtra("privateSponsor",1);
                startActivity(privateSponsor);
                break;
            case R.id.company_register:
                startActivity(new Intent(this, CompanyProfileActivity.class));
                break;
            case R.id.iv_back:
                 finish();
                break;
        }

    }
}
