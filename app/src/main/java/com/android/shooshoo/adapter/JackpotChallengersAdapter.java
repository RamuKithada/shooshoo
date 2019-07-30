package com.android.shooshoo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.utils.ApiUrls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class JackpotChallengersAdapter extends RecyclerView.Adapter<JackpotChallengersAdapter.CatViewHolder> {
    List<Challenge> challenges=new ArrayList<Challenge>();
    Context context=null;
    public JackpotChallengersAdapter(Context context, ArrayList<Challenge> challengeModels) {
        this.challenges = challengeModels;
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

            Picasso.with(context)
                    .load(ApiUrls.JACKPOT_BANNER_IMAGE_URL + challenge.getBannerImage())
                    .error(R.drawable.rose)
                    .placeholder(R.drawable.rose)
                    .into(catViewHolder.imageView);
            catViewHolder.time.setText(ApiUrls.getDurationTimeStamp(challenge.getCreatedOn()));
            catViewHolder.brand.setBackgroundColor(Color.parseColor("#549BC1"));
            catViewHolder.brand.setText(ApiUrls.removeDecimals(challenge.getAmount())+" "+challenge.getCurrency());
            StringBuilder builder=new StringBuilder();
            if(challenge.getFirstName()!=null)
                builder.append(challenge.getFirstName());
            if(challenge.getLastName()!=null)
                builder.append(' ').append(challenge.getLastName());

            catViewHolder.description.setText(builder.toString());
            if(challenge.getCompanies()!=null)
                for (Company company:challenge.getCompanies()) {
                    if(company.getCompanyName()!=null)
                    {
                        if(builder.length()>0)
                            builder.append(',').append(company.getCompanyName());
                        else
                            builder.append(company.getCompanyName());
                    }

                }
        }
    }

    @Override
    public int getItemCount() {
        return challenges==null?0:challenges.size();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title,description,time,brand;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =itemView.findViewById(R.id.image);
            title =itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.sub_title);
            time=itemView.findViewById(R.id.time);
            brand=itemView.findViewById(R.id.brand);
        }
    }

}
