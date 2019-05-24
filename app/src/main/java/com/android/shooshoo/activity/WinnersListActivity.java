package com.android.shooshoo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.WinnersListAdapter;

public class WinnersListActivity extends AppCompatActivity {
WinnersListAdapter listAdapter;
RecyclerView winnersList;
ImageView iv_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winners_list);
        winnersList=findViewById(R.id.winner_list);
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listAdapter=new WinnersListAdapter(this);
        winnersList.setAdapter(listAdapter);

    }
}
