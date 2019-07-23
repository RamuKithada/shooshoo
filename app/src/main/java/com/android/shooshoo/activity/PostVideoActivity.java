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
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.InviteFriendsAdapter;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.Feed;
import com.android.shooshoo.models.Follower;
import com.android.shooshoo.presenters.InviteFriendsPresenter;
import com.android.shooshoo.presenters.PostChallengePresenter;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.InviteFriendsView;
import com.android.shooshoo.views.PostChallengeView;
import com.squareup.picasso.Picasso;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class PostVideoActivity extends BaseActivity implements View.OnClickListener,PostChallengeView , InviteFriendsView {
    //    @BindView(R.id.challenge_video_layout)
//    FrameLayout challenge_video_layout;
    @BindView(R.id.video_post_layout)
    FrameLayout video_post_layout;
    @BindView(R.id.iv_playpause_post)
    ImageView iv_playpause_post;
    @BindView(R.id.video_thumb_post)
    ImageView video_thumb_post;
    @BindView(R.id.list_friends)
    RecyclerView list_friends;

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

    RetrofitApis retrofitApis;
    UserSession userSession;
    ArrayList<Follower> followers=new ArrayList<>();
    InviteFriendsPresenter inviteFriendsPresenter;
    InviteFriendsAdapter inviteFriendsAdapter;

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
        userSession=new UserSession(this);
        inviteFriendsPresenter=new InviteFriendsPresenter();
        inviteFriendsPresenter.attachView(this);
        inviteFriendsAdapter=new InviteFriendsAdapter(this,followers);
        if(detector.isConnectingToInternet()){
            inviteFriendsPresenter.getFollowers(userSession.getUserId());
        }

//        challenge_video_layout.setOnClickListener(this);
        Bitmap thumb=getIntent().getParcelableExtra("thumb");
        if(thumb!=null)
            video_thumb_post.setImageBitmap(thumb);

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

    @Override
    public void onChallengeInfo(ResponseBody responseBody) {

    }

    @Override
    public void onFriendsList(List<Follower> mFollowers) {
        if(mFollowers!=null){
            followers.addAll(mFollowers);
            inviteFriendsAdapter=new InviteFriendsAdapter(PostVideoActivity.this,followers);
            list_friends.setAdapter(inviteFriendsAdapter);
        }
    }

    @Override
    public void onSuccessFullInvitation(String message) {

    }
    private void sendNotification() {
        StringBuilder invites=new StringBuilder();
        for (Follower follower :followers) {
            if(follower.isSelected())
                if(invites.length()==0){
                    invites.append(follower.getFromId());
                }else {
                    invites.append(',').append(follower.getFromId());

                }

        }
        if(invites.length()>0) {
            if (detector.isConnectingToInternet())
                inviteFriendsPresenter.sendInvitations(userSession.getJackpot().getChallengeId(), invites.toString());
        }else {
            onSuccessFullInvitation("");
        }
    }
}
