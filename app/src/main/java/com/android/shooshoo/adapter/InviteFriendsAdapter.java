package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Follower;
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

public class InviteFriendsAdapter extends RecyclerView.Adapter<InviteFriendsAdapter.MyViewHolder> implements Filterable
{
    private Context context;
    private ArrayList<Follower> contactsModelArrayList;
    private ArrayList<Follower> filterArrayList=new ArrayList<Follower>();
    public InviteFriendsAdapter(Context context, ArrayList<Follower> contactsModelArrayList)
    {
        this.context=context;
        this.contactsModelArrayList=contactsModelArrayList;
        if(contactsModelArrayList!=null)
          this.filterArrayList=contactsModelArrayList;
        Log.e("size",""+filterArrayList.size());
    }

    @Override
    public InviteFriendsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invite_friends_item, parent, false);
        return new InviteFriendsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final InviteFriendsAdapter.MyViewHolder holder, final int position)
    {
        final Follower model=filterArrayList.get(position);
        holder.name.setText(model.getUserName());
        Picasso.with(context).load(PROFILE_IMAGE_URL+model.getImage()).error(R.drawable.profile_1).into(holder.image);
            if(model.isSelected()){
                holder.plus_mark.setVisibility(View.INVISIBLE);
            }else {
                holder.plus_mark.setVisibility(View.VISIBLE);
            }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setSelected(!model.isSelected());
                if(model.isSelected()){
                    holder.plus_mark.setVisibility(View.INVISIBLE);
                }else {
                    holder.plus_mark.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return filterArrayList.size();
    }

    MyFilter myFillter=null;
    @Override
    public Filter getFilter() {
        if(myFillter==null)
            myFillter=new MyFilter();

        return myFillter;
    }
    public void setSelectAll() {
        for (Follower model:filterArrayList) {
            model.setSelected(true);
        }
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.image)
        CircleImageView image;

        @BindView(R.id.plus_mark)
        ImageView plus_mark;


        public MyViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class MyFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults=new FilterResults();
            if(constraint.length()==0){
                filterResults.values=contactsModelArrayList;
                filterResults.count=contactsModelArrayList.size();
                return filterResults;
            }
            List<Follower> models=new ArrayList<Follower>();
            for (Follower contactsModel:contactsModelArrayList){
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
            if(results.count>=0){
                filterArrayList= (ArrayList<Follower>) results.values;
                notifyDataSetChanged();
            }



        }
    }
}
