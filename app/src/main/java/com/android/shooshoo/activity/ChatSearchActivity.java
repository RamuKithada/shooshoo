package com.android.shooshoo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.SearchChatFriendAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatSearchActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.search_result_list)
    RecyclerView search_result_list;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.edt_search)
    EditText edt_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_search);
        ButterKnife.bind(this);
        iv_back.setOnClickListener(this);
        search_result_list.setLayoutManager(new LinearLayoutManager(this));
        search_result_list.setAdapter(new SearchChatFriendAdapter());
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
