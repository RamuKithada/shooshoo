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
import com.android.shooshoo.models.Follower;
import com.android.shooshoo.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.android.shooshoo.utils.ApiUrls.PROFILE_IMAGE_URL;

/**
 * Created by admin on 4/20/2017.
 */

public class UserFollowAdapter extends RecyclerView.Adapter<UserFollowAdapter.MyViewHolder> implements Filterable
{
    private Context context;
    private List<User> contactsModelArrayList;
    private List<User> filterArrayList=new ArrayList<User>();
    public UserFollowAdapter(Context context, List<User> users)
    {
        this.context=context;
        this.contactsModelArrayList=users;
        if(contactsModelArrayList!=null)
          this.filterArrayList=contactsModelArrayList;
        Log.e("size",""+filterArrayList.size());
    }

    @Override
    public UserFollowAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.find_user_model, parent, false);
        return new UserFollowAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserFollowAdapter.MyViewHolder holder, final int position)
    {
        final UserFollowAdapter.MyViewHolder myViewHolder=holder;
        final User model=filterArrayList.get(position);
        holder.tv_personname.setText(model.getUserName());
        Picasso.with(context).load(PROFILE_IMAGE_URL+model.getImage()).error(R.drawable.profile_1).into(holder.iv_profileimage);
            if(model.isSelected()){
                holder.added.setVisibility(View.VISIBLE);
                holder.add.setVisibility(View.INVISIBLE);
            }else {
                holder.added.setVisibility(View.INVISIBLE);
                holder.add.setVisibility(View.VISIBLE);
            }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setSelected(!model.isSelected());
                if(model.isSelected()){
                    holder.added.setVisibility(View.VISIBLE);
                    holder.add.setVisibility(View.INVISIBLE);
                }else {
                    holder.added.setVisibility(View.INVISIBLE);
                    holder.add.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return filterArrayList.size();
    }

    MyFillter myFillter=null;
    @Override
    public Filter getFilter() {
        if(myFillter==null)
            myFillter=new MyFillter();

        return myFillter;
    }
    public void setSelectAll() {
        for (User model:filterArrayList) {
            model.setSelected(true);
        }
        notifyDataSetChanged();
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

    public class MyFillter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults=new FilterResults();
            if(constraint.length()==0){
                filterResults.values=contactsModelArrayList;
                filterResults.count=contactsModelArrayList.size();
                return filterResults;
            }
            List<User> models=new ArrayList<User>();
            for (User contactsModel:contactsModelArrayList){
                if(contactsModel.getUserName().toLowerCase().contains(constraint.toString().toLowerCase())){
                    models.add(contactsModel);
                }

            }

            filterResults.values=models;
            filterResults.count=models.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if(results.count>0){
                filterArrayList= (ArrayList<User>) results.values;
                notifyDataSetChanged();
            }



        }
    }
}
