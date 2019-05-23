package com.android.shooshoo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.SnapHelper;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.FullVideoAdapter;
import com.android.shooshoo.models.VideoModel;

import java.util.ArrayList;

import im.ene.toro.widget.Container;

public class FeedsActivity extends AppCompatActivity {
    Container container;
    FullVideoAdapter adapter;
    LinearLayoutManager layoutManager;
    private ArrayList<VideoModel> modelArrayList=new ArrayList<>();
    String[] urls=new String[]{
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);
        container = findViewById(R.id.player_container);
        layoutManager = new LinearLayoutManager(this);
        SnapHelper snapHelper = new PagerSnapHelper();
        container.setLayoutManager(layoutManager);
        snapHelper.attachToRecyclerView(container);
        adapter = new FullVideoAdapter(this,modelArrayList);
        container.setAdapter(adapter);
        setDataToContainer();
    }
    private void setDataToContainer()
    {
        for (String s:urls) {
            VideoModel videoModel = new VideoModel();
            videoModel.setVideo(s);
            modelArrayList.add(videoModel);
            adapter.notifyDataSetChanged();
        }
    }
}
