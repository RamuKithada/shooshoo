package com.android.shooshoo.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.FullVideoAdapter;
import com.android.shooshoo.models.Feed;
import com.android.shooshoo.models.VideoModel;
import com.android.shooshoo.presenters.FeedsPresenter;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.FeedsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.ene.toro.widget.Container;

/**
 * Feed activity is used to show the list new feeds
 * container is used to show list of videos,it is subclass if RecyclerView,it is imported  from Toro player
 * please check Toro player library
 * adapter is recyclerview adapter for container
 *
 *
 *
 */

public class FeedsActivity extends BaseActivity implements FullVideoAdapter.FeedClickListener, FeedsView {
    Container container;
    FullVideoAdapter adapter;
    LinearLayoutManager layoutManager;
    RelativeLayout bottom_view;
    private ArrayList<Feed> modelArrayList=new ArrayList<>();

/*    String[] urls=new String[]{
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
            "http://www.exit109.com/~dnn/clips/RW20seconds_2.mp4",
            "http://www.exit109.com/~dnn/clips/RW20seconds_1.mp4",

    };*/


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
    /**
     * feedsPresenter is used to load the data;
     */
    FeedsPresenter feedsPresenter;

    ConnectionDetector detector;
    View view=null;
    private View.OnClickListener bottomNavigationOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           view=v;
            Intent intent=new Intent(FeedsActivity.this,HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            switch (v.getId()) {
                case R.id.navigation_home:
                    intent.putExtra("icon",0);
                    break;
                case R.id.navigation_challengers:
                    intent.putExtra("icon",1);
                    break;
                case R.id.navigation_feed:
                   ImageView imageView= navigation_feed.findViewById(R.id.iv_navigation_feed);
                    TextView textView= navigation_feed.findViewById(R.id.tv_navigation_feed);
                    imageView.setImageResource(R.mipmap.feed_active);
                    textView.setTextColor(getResources().getColor(R.color.pink_drak));
                    return;
                case R.id.navigation_winners:
                    intent.putExtra("icon",3);
                    break;
                case R.id.navigation_radar:
                    intent.putExtra("icon",4);
                    break;
            }
            if(!userSession.isLogin())
            {
                Intent loginIntent=new Intent(FeedsActivity.this,LoginActivity.class);
                  startActivityForResult(loginIntent,150);
            }
            else
            {
                Log.e("user id",""+userSession.getUserId());
                startActivity(intent);
                finish();
            }


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);
        ButterKnife.bind(this);
        container = findViewById(R.id.player_container);
        bottom_view=findViewById(R.id.bottom_view);
        userSession=new UserSession(this);
        detector=new ConnectionDetector(this);
        feedsPresenter=new FeedsPresenter();
        feedsPresenter.attachView(this);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        SnapHelper snapHelper = new PagerSnapHelper();
        container.setLayoutManager(layoutManager);
        snapHelper.attachToRecyclerView(container);
        adapter = new FullVideoAdapter(this,modelArrayList,this);
        container.setAdapter(adapter);
        if(detector.isConnectingToInternet())
            feedsPresenter.loadFeeds();
        else {
            showMessage("Check internet connection");
        }
//        setDataToContainer();
        navigation_home.setOnClickListener(bottomNavigationOnClickListener);
        navigation_challengers.setOnClickListener(bottomNavigationOnClickListener);
        navigation_feed.setOnClickListener(bottomNavigationOnClickListener);
        navigation_winners.setOnClickListener(bottomNavigationOnClickListener);
        navigation_radar.setOnClickListener(bottomNavigationOnClickListener);
        bottomNavigationOnClickListener.onClick(navigation_feed);
    }
/*    private void setDataToContainer()
    {
        for (String s:urls) {
            VideoModel videoModel = new VideoModel();
            videoModel.setVideo(s);
            modelArrayList.add(videoModel);
            adapter.notifyDataSetChanged();
        }
    }*/

    @Override
    public void onClick(View v,Feed feed) {
        Intent intent=new Intent(this, LoginActivity.class);

        switch (v.getId()){
            case R.id.comment_view:
                if(!userSession.isLogin())
                    startActivityForResult(intent,100);
                else {
                    onActivityResult(100,RESULT_OK,null);
                }

                break;
            case R.id.donation_view:
                if(!userSession.isLogin())
                    startActivityForResult(intent,101);
                else {
                    onActivityResult(101,RESULT_OK,null);
                }
                break;
            case R.id.likes_view:
                if(!userSession.isLogin())
                    startActivityForResult(intent,102);
                else {
                   feed.setLike(!feed.isLike());
                   adapter.notifyDataSetChanged();
//                     onActivityResult(102,RESULT_OK,null);
                }
                break;
            case R.id.share_view:
                if(!userSession.isLogin())
                    startActivityForResult(intent,103);
                else {
                    onActivityResult(103,RESULT_OK,null);
                }
                break;



        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Intent intent;
                switch (requestCode){
                    case 100:
                        intent=new Intent(this,FeedCommentsActivity.class);
                        startActivity(intent);
                        break;
                    case 101:

                        break;
                    case 102:

                        break;
                    case 103:

                        break;
                    case 150:
                        if(bottomNavigationOnClickListener!=null&&view!=null)
                           bottomNavigationOnClickListener.onClick(view);
                        break;
                }
        }
    }


    @Override
    public void onFeedsLoaded(List<Feed> feeds) {
        modelArrayList.addAll(feeds);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        feedsPresenter.detachView();
    }
}
