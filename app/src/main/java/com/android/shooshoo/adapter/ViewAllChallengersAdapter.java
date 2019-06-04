package com.android.shooshoo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.RadarActivity;
import com.android.shooshoo.models.ChallengeModel;

import java.util.ArrayList;

public class ViewAllChallengersAdapter extends RecyclerView.Adapter<ViewAllChallengersAdapter.CatViewHolder> {
      ArrayList<ChallengeModel> challengeModels;
    Context context;
    public ViewAllChallengersAdapter(Context context,ArrayList<ChallengeModel> challengeModels) {
        this.challengeModels = challengeModels;
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
        if(i==getItemCount()-1) {
            catViewHolder.viewall.setVisibility(View.VISIBLE);
            catViewHolder.card.setVisibility(View.INVISIBLE);
        }else {
            ChallengeModel model = challengeModels.get(i);
            catViewHolder.image.setImageResource(model.getImage());
            catViewHolder.title.setText(model.getTitle());
            catViewHolder.description.setText(model.getDescription());
            catViewHolder.viewall.setVisibility(View.GONE);
            catViewHolder.card.setVisibility(View.VISIBLE);
        }
        catViewHolder.viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, RadarActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return challengeModels.size();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title,description,time,brand;
        TextView viewall;
        CardView card;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.image);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.sub_title);
            time=itemView.findViewById(R.id.time);
            brand=itemView.findViewById(R.id.brand);
            viewall=itemView.findViewById(R.id.view_all);
            card=itemView.findViewById(R.id.card);
        }
    }

}
