package com.android.shooshoo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.SearchChatFriendAdapter;

public class ChatSearchActivity extends AppCompatActivity {

    RecyclerView search_result_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_search);
        search_result_list=findViewById(R.id.search_result_list);
        search_result_list.setLayoutManager(new LinearLayoutManager(this));
        search_result_list.setAdapter(new SearchChatFriendAdapter());
    }
}
