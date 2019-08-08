package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.android.shooshoo.utils.ApiUrls.IMAGE_URL;
import static com.android.shooshoo.utils.ApiUrls.PROFILE_IMAGE_URL;

/**
 * Created by admin on 4/20/2017.
 */

public class CompanyFollowAdapter extends RecyclerView.Adapter<CompanyFollowAdapter.MyViewHolder>
{
    private Context context;
    private List<Company> contactsModelArrayList;
    private  FollowCompanyListener followCompanyListener=null;
    public CompanyFollowAdapter(Context context, List<Company> users)
    {
        this.context=context;
        this.contactsModelArrayList=users;

    }

    public void setFollowCompanyListener(FollowCompanyListener followCompanyListener) {
        this.followCompanyListener = followCompanyListener;
    }

    @Override
    public CompanyFollowAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.find_user_model, parent, false);
        return new CompanyFollowAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CompanyFollowAdapter.MyViewHolder holder, final int position)
    {
        final CompanyFollowAdapter.MyViewHolder myViewHolder=holder;
        final Company model=contactsModelArrayList.get(position);
        holder.tv_personname.setText(model.getCompanyName());
        Picasso.with(context).load(IMAGE_URL+ "companies/" + model.getCompanyLogo()).error(R.drawable.h_m).into(holder.iv_profileimage);
            if(model.getSelected()==1){
                holder.added.setVisibility(View.VISIBLE);
                holder.add.setVisibility(View.INVISIBLE);
            }else {
                holder.added.setVisibility(View.INVISIBLE);
                holder.add.setVisibility(View.VISIBLE);
            }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(followCompanyListener!=null)
                    followCompanyListener.onFollow(model);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return contactsModelArrayList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tv_personname)
        TextView tv_personname;

        @BindView(R.id.iv_profileimage)
        CircleImageView iv_profileimage;

        @BindView(R.id.tv_personnumber)
        TextView tv_personnumber;

        @BindView(R.id.add)
        RelativeLayout add;
        @BindView(R.id.added)
        RelativeLayout added;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    public interface FollowCompanyListener{
        void onFollow(Company company);
    }

}
