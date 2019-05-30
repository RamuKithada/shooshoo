package com.android.shooshoo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.FeedsImagesAdapter;
import com.android.shooshoo.adapter.ProfileFeedsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyChallengesActivity extends AppCompatActivity implements View.OnClickListener {
@BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rv_recnet_posts)
    RecyclerView rv_recnet_posts;
    @BindView(R.id.camera)
    LinearLayout camera;
    @BindView(R.id.brand)
    LinearLayout brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_challenges);
        ButterKnife.bind(this);
        brand.setOnClickListener(this);
        camera.setOnClickListener(this);
        rv_recnet_posts.setAdapter(new ProfileFeedsAdapter());
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.camera:
startActivity(new Intent(this,CameraActivity.class));
            break;
        case R.id.brand:
            break;
    }
    }
}
