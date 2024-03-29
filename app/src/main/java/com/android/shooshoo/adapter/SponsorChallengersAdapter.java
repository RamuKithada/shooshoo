package com.android.shooshoo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class SponsorChallengersAdapter extends RecyclerView.Adapter<SponsorChallengersAdapter.CatViewHolder> {
    Context context;
    List<Challenge> challenges=new ArrayList<Challenge>();
    public SponsorChallengersAdapter(Context context, List<Challenge> challenges) {
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

          if(challenges!=null){
              Log.e("challenge type",""+challenges.get(i).getType());
               Challenge challenge=challenges.get(i);
               catViewHolder.title.setText(challenge.getChallengeName());
               String uri=null;
              StringBuilder builder=new StringBuilder();
               if(challenge.getType().equalsIgnoreCase("sponsor"))
               {
                   uri=ApiUrls.SPONSOR_BANNER_IMAGE_URL;
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
                   catViewHolder.brand.setBackgroundColor(Color.parseColor("#F31F68"));
                   catViewHolder.brand.setText(ApiUrls.removeDecimals(challenge.getTotalPrize())+" "+challenge.getCurrency());

               }
                 else if(challenge.getType().equalsIgnoreCase("jackpot"))
               {
                   uri=ApiUrls.JACKPOT_BANNER_IMAGE_URL;

                   if(challenge.getFirstName()!=null)
                       builder.append(challenge.getFirstName());
                   if(challenge.getLastName()!=null)
                       builder.append(' ').append(challenge.getLastName());
                   if(builder.length()<=0)
                       builder.append(challenge.getUserName());

                   catViewHolder.brand.setBackgroundColor(Color.parseColor("#549BC1"));
                   catViewHolder.brand.setText(ApiUrls.removeDecimals(challenge.getAmount())+" "+challenge.getCurrency());
               }

              Picasso.with(context)
                      .load(uri+challenge.getBannerImage())
                      .error(R.drawable.error)
                      .placeholder(R.drawable.giphy)
                      .into(catViewHolder.imageView);
              catViewHolder.time.setText(ApiUrls.getDurationTimeStamp(challenge.getEndDate()+" "+challenge.getEndTime()));
              catViewHolder.subtitle.setText(builder.toString());
          }

    }

    @Override
    public int getItemCount() {
        if(challenges==null)
        return 0;
//        if(challenges.size()>4)
//            return 4;

        return challenges.size();

    }
    public void setChallenges(List<Challenge> challenges){
       this.challenges=challenges;
       notifyDataSetChanged();
    }

    public List<Challenge> getChallenges() {
        return challenges;
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
