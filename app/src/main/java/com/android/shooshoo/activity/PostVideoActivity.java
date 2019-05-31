package com.android.shooshoo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.shooshoo.R;

public class PostVideoActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_video);
        ImageView back=findViewById(R.id.iv_back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();

    }
}
