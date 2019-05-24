package com.android.shooshoo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.FeedCommentsAdapter;

public class FeedCommentsActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView commentList;
    FeedCommentsAdapter feedCommentsAdapter;
    ImageView iv_back, iv_send_cmnt;
    EditText edt_comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_comments);
        commentList=findViewById(R.id.comment_list);
        iv_back=findViewById(R.id.iv_back);
        iv_send_cmnt =findViewById(R.id.iv_send_msg);
        edt_comment =findViewById(R.id.edt_message);
        iv_send_cmnt.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        feedCommentsAdapter=new FeedCommentsAdapter();
        commentList.setLayoutManager(new LinearLayoutManager(this));
        commentList.setAdapter(feedCommentsAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_send_msg:
                edt_comment.setText("");
                edt_comment.clearFocus();
                break;
            case R.id.iv_back:
                finish();
                break;

        }

    }
}
