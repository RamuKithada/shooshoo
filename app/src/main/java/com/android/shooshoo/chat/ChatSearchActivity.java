package com.android.shooshoo.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.HomeActivity;
import com.android.shooshoo.adapter.SearchChatFriendAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatSearchActivity extends AppCompatActivity implements View.OnClickListener{

    /***
     * {@link ChatSearchActivity} is used to search the user by name ,brand etc., to find a user and chat with him
     *
     *
     */

    @BindView(R.id.search_result_list)
    RecyclerView search_result_list;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.edt_search)
    EditText edt_search;

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
    private View.OnClickListener bottomNavigationOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(ChatSearchActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            switch (v.getId()) {
                case R.id.navigation_home:
                    intent.putExtra("icon",0);
                    break;
                case R.id.navigation_challengers:
                    intent.putExtra("icon",1);
                    break;
                case R.id.navigation_feed:
                    intent.putExtra("icon",2);
                    break;
                case R.id.navigation_winners:
                    intent.putExtra("icon",3);
                    break;
                case R.id.navigation_radar:
                    intent.putExtra("icon",4);
                    break;
            }
            startActivity(intent);
            finish();

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_search);
        ButterKnife.bind(this);
        iv_back.setOnClickListener(this);
        search_result_list.setLayoutManager(new LinearLayoutManager(this));
        search_result_list.setAdapter(new SearchChatFriendAdapter());
        navigation_home.setOnClickListener(bottomNavigationOnClickListener);
        navigation_challengers.setOnClickListener(bottomNavigationOnClickListener);
        navigation_feed.setOnClickListener(bottomNavigationOnClickListener);
        navigation_winners.setOnClickListener(bottomNavigationOnClickListener);
        navigation_radar.setOnClickListener(bottomNavigationOnClickListener);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
