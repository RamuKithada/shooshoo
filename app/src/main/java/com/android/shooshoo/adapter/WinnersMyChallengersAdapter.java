package com.android.shooshoo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.WinnersListActivity;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.ChallengeModel;
import com.android.shooshoo.utils.ApiUrls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WinnersMyChallengersAdapter extends RecyclerView.Adapter<WinnersMyChallengersAdapter.CatViewHolder> {
    Context context;
    List<Challenge> challenges=new ArrayList<Challenge>();

    public WinnersMyChallengersAdapter(Context context, List<Challenge> challenges) {
        this.challenges=challenges;
        this.context=context;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.challengers_item,null);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder catViewHolder,final int i) {
        if (challenges != null) {
            Challenge challenge = challenges.get(i);
            catViewHolder.title.setText(challenge.getChallengeName());

            String url;
            if(challenge.getCompanies()!=null){
                url=ApiUrls.SPONSOR_BANNER_IMAGE_URL;
            }else {
                url=ApiUrls.JACKPOT_BANNER_IMAGE_URL;
            }
            Log.e("url ",url+ challenge.getBannerImage());
            Picasso.with(context)
                    .load(url+ challenge.getBannerImage())
                    .error(R.drawable.rose)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(catViewHolder.imageView);
            catViewHolder.time.setText(ApiUrls.getDurationTimeStamp(challenge.getCreatedOn()));
            catViewHolder.subtitle.setText(challenge.getDescription());
        }
          catViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent=new Intent(context,WinnersListActivity.class);
                  context.startActivity(intent);
              }
          });



    }

    @Override
    public int getItemCount() {
        if(challenges==null)
        return 0;

        return challenges.size();
    }


    public class CatViewHolder extends RecyclerView.ViewHolder{
      ImageView imageView;
      TextView title,subtitle,brand,time;


        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            title=itemView.findViewById(R.id.title);
            subtitle=itemView.findViewById(R.id.sub_title);
            brand=itemView.findViewById(R.id.brand);
            time=itemView.findViewById(R.id.time);

        }
    }
}
