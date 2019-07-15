package com.android.shooshoo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.ChattingAdapter;

public class ChattingActivity extends AppCompatActivity implements View.OnClickListener{
    /***
     * This is the screen of chatting functionality ui
     *  chatHistoryList is to show chatting conversation with an user
     *  edt_message is used to take input from user to make conversation
     *  iv_send_msg is used to send icon to send message on user action
     *  iv_back is used close the chatting screen go back to  chats screen
     */

   RecyclerView chatHistoryList;
   ChattingAdapter chattingAdapter;
   ImageView iv_back,iv_send_msg;
   EditText edt_message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        chatHistoryList=findViewById(R.id.chat_history_list);
        iv_back=findViewById(R.id.iv_back);
        iv_send_msg=findViewById(R.id.iv_send_msg);
        edt_message=findViewById(R.id.edt_message);
        iv_send_msg.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        chattingAdapter=new ChattingAdapter();
        chatHistoryList.setLayoutManager(new LinearLayoutManager(this));
        chatHistoryList.setAdapter(chattingAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_send_msg:
                edt_message.setText("");
                edt_message.clearFocus();
                break;
            case R.id.iv_back:
                finish();
                break;

        }

    }
}
