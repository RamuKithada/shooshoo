package com.android.shooshoo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.RecentPostAdapter;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.Feed;
import com.android.shooshoo.presenters.PostChallengePresenter;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.PostChallengeView;
import com.google.gson.JsonArray;
import com.google.gson.annotations.JsonAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 *  {@link CreatedChallengeViewActivity } is used to show the detailed View of the challenge user created challenge.
 */

public class CreatedChallengeViewActivity extends  BaseActivity implements PostChallengeView {

    @BindView(R.id.navigation_home)
    LinearLayout navigation_home;
    @BindView(R.id.navigation_challengers)
    LinearLayout navigation_challengers;
    @BindView(R.id.navigation_feed)
    LinearLayout navigation_feed;
    @BindView(R.id.navigation_winners)
    LinearLayout navigation_winners;
    @BindView(R.id.navigation_radar)
    LinearLayout navigation_radar;

    @BindView(R.id.iv_help)
    ImageView iv_help;

    @BindView(R.id.iv_chat)
    ImageView iv_chat;

    @BindView(R.id.iv_profile)
    ImageView iv_profile;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.price)
    TextView price;

    @BindView(R.id.time)
    TextView time;


    @BindView(R.id.no_of_posts)
    TextView no_of_posts;

    @BindView(R.id.no_of_likes)
    TextView no_of_likes;

    @BindView(R.id.no_of_comments)
    TextView no_of_comments;

    @BindView(R.id.no_of_views)
    TextView no_of_views;


    @BindView(R.id.no_of_clicks)
    TextView no_of_clicks;

    @BindView(R.id.challenge_image)
    ImageView challenge_image;

    @BindView(R.id.rv_recent_posts)
    RecyclerView rv_recent_posts;

    ConnectionDetector connectionDetector = null;
    PostChallengePresenter challengePresenter = null;

    RecentPostAdapter recentPostAdapter;
    UserSession userSession;
    ArrayList<Feed> feeds = new ArrayList<Feed>();
    private View.OnClickListener bottomNavigationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(CreatedChallengeViewActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            switch (v.getId()) {
                case R.id.navigation_home:
                    intent.putExtra("icon", 0);
                    break;
                case R.id.navigation_challengers:
                    intent.putExtra("icon", 1);
                    break;
                case R.id.navigation_feed:
                    intent.putExtra("icon", 2);
                    break;
                case R.id.navigation_winners:
                    intent.putExtra("icon", 3);
                    break;
                case R.id.navigation_radar:
                    intent.putExtra("icon", 4);
                    break;
                case R.id.iv_profile:
                    intent.putExtra("icon", 6);
                    break;
                case R.id.iv_chat:
                    intent.putExtra("icon", 7);
                    break;
            }
            startActivity(intent);
            finish();

        }
    };
    Challenge challenge = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_challenge_view);
        ButterKnife.bind(this);
        navigation_home.setOnClickListener(bottomNavigationOnClickListener);
        navigation_challengers.setOnClickListener(bottomNavigationOnClickListener);
        navigation_feed.setOnClickListener(bottomNavigationOnClickListener);
        navigation_winners.setOnClickListener(bottomNavigationOnClickListener);
        navigation_radar.setOnClickListener(bottomNavigationOnClickListener);
        iv_chat.setOnClickListener(bottomNavigationOnClickListener);
        iv_profile.setOnClickListener(bottomNavigationOnClickListener);

        connectionDetector = new ConnectionDetector(this);
        challengePresenter = new PostChallengePresenter();
        challengePresenter.attachView(this);
        recentPostAdapter = new RecentPostAdapter(this, feeds);
        rv_recent_posts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_recent_posts.setAdapter(recentPostAdapter);
        userSession = new UserSession(this);

        challenge = getIntent().getParcelableExtra("challenge");
        if (challenge != null) {
            setChallenge(challenge);
        }
    }

    private void setChallenge(Challenge challenge) {
        this.challenge = challenge;
        title.setText(challenge.getChallengeName());
        if (connectionDetector.isConnectingToInternet()) {
            challengePresenter.getRecentPosts(challenge.getChallengeId(), challenge.getType());
            challengePresenter.getChallengeInfo(challenge.getChallengeId(),challenge.getType(),null);
            Picasso.with(this)
                    .load("http://165.22.94.168/uploads/" + challenge.getType() + "s/banners/" + challenge.getBannerImage())
                    .error(R.drawable.logo_pink)
                    .placeholder(R.drawable.logo_pink)
                    .into(challenge_image);
        } else
            showMessage("Check Internet connection");
    }

    @Override
    public void onSuccessfulUpload(String msg) {

    }

    @Override
    public void onRecentPosts(List<Feed> feeds) {
        rv_recent_posts.setAdapter(new RecentPostAdapter(this, feeds));

    }

    @Override
    public void onRules(List<String> rules) {

    }

    /**
     *
     * @param responseBody is the response of the service call we have parse the  response to show the total data.
     */
    @Override
    public void onChallengeInfo(ResponseBody responseBody) {
        try {
            String result=responseBody.string();
//            Log.e("Result",result);
            //TODO {"status":1,"message":"Success","challenge":[{"postsCount":"0","likesCount":null,"viewsCount":null}]} has to parsed
            JSONObject object=new JSONObject(result);
            if(object.optInt("status")==1){
                JSONArray jsonArray=object.getJSONArray("challenge");
                if(jsonArray!=null){
                    if(jsonArray.length()>0){
                        JSONObject object1=jsonArray.getJSONObject(0);
                        no_of_posts.setText(object1.optString("postsCount"));
                        no_of_likes.setText(object1.optString("likesCount"));
                        no_of_views.setText(object1.optString("viewsCount"));
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
