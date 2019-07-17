package com.android.shooshoo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.Feed;
import com.android.shooshoo.presenters.PostChallengePresenter;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.views.PostChallengeView;
import com.squareup.picasso.Picasso;

import java.net.URISyntaxException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostVideoActivity extends BaseActivity implements View.OnClickListener,PostChallengeView {
    //    @BindView(R.id.challenge_video_layout)
//    FrameLayout challenge_video_layout;
    @BindView(R.id.video_post_layout)
    FrameLayout video_post_layout;
    @BindView(R.id.iv_playpause_post)
    ImageView iv_playpause_post;
    @BindView(R.id.video_thumb_post)
    ImageView video_thumb_post;

    //    @BindView(R.id.iv_back)
//    ImageView iv_back;
    @BindView(R.id.about_video)
    AppCompatEditText about_video;
    @BindView(R.id.btn_publish)
    AppCompatTextView btn_publish;
    ConnectionDetector detector;
    PostChallengePresenter challengePresenter;
    Challenge challenge=null;

    Uri uri;

    boolean isImagePost=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_video);
        ButterKnife.bind(this);
//        iv_back.setOnClickListener(this);
        String post=  getIntent().getStringExtra("post");
        uri=getIntent().getParcelableExtra("mpost");
        isImagePost= getIntent().getBooleanExtra("type",false);
        challenge=getIntent().getParcelableExtra("challenge");
        detector=new ConnectionDetector(this);
        challengePresenter=new PostChallengePresenter();
        challengePresenter.attachView(this);
//        challenge_video_layout.setOnClickListener(this);
        if(isImagePost)
        {
            iv_playpause_post.setVisibility(View.GONE);
            Picasso.with(this).load(uri).into(video_thumb_post);
        }
        else
        {
            video_post_layout.setOnClickListener(this);
        }

        btn_publish.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
          /*  case R.id.iv_back:
                    finish();
                break;*/
          /*  case R.id.challenge_video_layout:
                 intent=new Intent(this,SingleVideoViewActivity.class);
                intent.putExtra("uri",Uri.parse(getIntent().getStringExtra("post")));
                startActivity(intent);

                break;*/
            case R.id.video_post_layout:
                intent=new Intent(this,SingleVideoViewActivity.class);
                intent.putExtra("uri",getIntent().getParcelableExtra("mpost"));
                startActivity(intent);
                break;
            case R.id.btn_publish:
                if(detector.isConnectingToInternet()){
                    try {
                        String challengeVideoUri= ApiUrls.getFilePath(this,uri);
                        challengePresenter.postChallenge(userSession.getUserId(),challenge.getChallengeId(),challengeVideoUri,"sponsor",about_video.getText().toString());
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }else {
                    showMessage("No Internet Try Again");
                }
                break;
        }

    }

    @Override
    public void onSuccessfulUpload(String msg) {
        showMessage(msg);
        Intent intent=new Intent(this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onRecentPosts(List<Feed> posts) {

    }

    @Override
    public void onRules(List<String> rules) {

    }
}
