package com.android.shooshoo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.utils.ApiUrls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SponsorChallengersAdapter extends RecyclerView.Adapter<SponsorChallengersAdapter.CatViewHolder> {
    Context context;
    List<Challenge> challenges=new ArrayList<Challenge>();
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public SponsorChallengersAdapter(Context context, List<Challenge> challenges) {
        this.challenges=challenges;
        this.context=context;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.challengers_item,null);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder catViewHolder,final int i) {
          if(challenges!=null){
               Challenge challenge=challenges.get(i);
               catViewHolder.title.setText(challenge.getChallengeName());
              Picasso.with(context)
                      .load(ApiUrls.SPONSOR_BANNER_IMAGE_URL+challenge.getBannerImage())
                      .error(R.drawable.rose)
                      .placeholder(R.drawable.rose)
                      .into(catViewHolder.imageView);
              catViewHolder.time.setText(ApiUrls.getDurationTimeStamp(challenge.getCreatedOn()));
              catViewHolder.subtitle.setText(challenge.getDescription());
          }
        catViewHolder.itemView.setOnClickListener(onClickListener);



    }

    @Override
    public int getItemCount() {
        if(challenges==null)
        return 16;

        return challenges.size();
    }
    public void setChallenges(List<Challenge> challenges){
       this.challenges=challenges;
       notifyDataSetChanged();
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
