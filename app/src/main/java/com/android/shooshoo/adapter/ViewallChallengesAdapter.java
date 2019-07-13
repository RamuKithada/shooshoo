package com.android.shooshoo.adapter;

import android.app.Activity;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Manish on 10/3/2017.
 */

public class ViewallChallengesAdapter extends RecyclerView.Adapter<ViewallChallengesAdapter.ViewHolder>{

    private Activity context;
    private ArrayList<Challenge> challengeArrayList=new ArrayList<>();
    private String imagebaseurl;

    public ViewallChallengesAdapter(Activity context,ArrayList<Challenge> challengeArrayList){
        this.context = context;
        this.challengeArrayList=challengeArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.viewall_challengesmodel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ViewHolder myViewHolder=holder;
        Challenge model=challengeArrayList.get(position);
        holder.tv_challenge_name.setText(model.getChallengeName());
        holder.tv_personname.setText(model.getUserName());
        holder.tv_tag.setText(model.getDescription());
        holder.tv_price.setText(" "+model.getBudget()+" € ");
        if(model.getType().equalsIgnoreCase("sponsor"))
            imagebaseurl=ApiUrls.SPONSOR_BANNER_IMAGE_URL;
        else if(model.getType().equalsIgnoreCase("jackpot"))
            imagebaseurl=ApiUrls.JACKPOT_BANNER_IMAGE_URL;

        Picasso.with(context).load(imagebaseurl+model.getBannerImage()).error(R.drawable.h_m)
                .placeholder(R.drawable.h_m).into(holder.iv_videothumb);
        Picasso.with(context).load(imagebaseurl+model.getBannerImage()).error(R.drawable.h_m)
                .placeholder(R.drawable.h_m).into(holder.ci_profileimage);
    }

    @Override
    public int getItemCount() {
        return challengeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ci_profileimage)
        CircleImageView ci_profileimage;
        @BindView(R.id.iv_videothumb)
        ImageView iv_videothumb;
        @BindView(R.id.tv_challenge_name)
        TextView tv_challenge_name;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_personname)
        TextView tv_personname;
        @BindView(R.id.tv_tag)
        TextView tv_tag;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
