package com.android.shooshoo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    @BindView(R.id.video_thumb)
    ImageView video_thumb;

    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.title)
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_challenges);
        ButterKnife.bind(this);
        brand.setOnClickListener(this);
        camera.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        rv_recnet_posts.setAdapter(new ProfileFeedsAdapter());
       int image =getIntent().getIntExtra("image",-1);
       if(image>-1)
           video_thumb.setImageResource(image);
        String titles=getIntent().getStringExtra("name");
        if(titles!=null){
            title.setText(titles);
        }
        String des=getIntent().getStringExtra("des");
        if(des!=null)
            sub_title.setText(des);




    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.camera:
            startActivity(new Intent(this,CameraActivity.class));
            break;
        case R.id.iv_back:
            finish();
            break;
        case R.id.brand:
            startActivity(new Intent(this,BrandProfileActivity.class));
            break;
    }
    }
}
