package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.android.shooshoo.utils.ApiUrls.PROFILE_IMAGE_URL;

/**
 * Created by admin on 4/20/2017.
 */

public class UserTadAdapter extends RecyclerView.Adapter<UserTadAdapter.MyViewHolder>
{
    private Context context;
    private List<User> userList;
    private  TagUserListener tagUserListener=null;
    public UserTadAdapter(Context context, List<User> users)
    {
        this.context=context;
        this.userList =users;
    }

    public void setTagUserListener(TagUserListener tagUserListener) {
        this.tagUserListener = tagUserListener;
    }

    @Override
    public UserTadAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_user_model, parent, false);
        return new UserTadAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserTadAdapter.MyViewHolder holder, final int position)
    {
        final User model= userList.get(position);
        holder.tv_personname.setText(model.getUserName());
        Picasso.with(context).load(PROFILE_IMAGE_URL+model.getImage()).error(R.drawable.error).into(holder.iv_profileimage);
            if(model.isSelected()){
                holder.iv_tag.setImageResource(R.drawable.ic_tick_white_24dp);
            }else {
                holder.iv_tag.setImageResource(R.drawable.tag);
            }

        holder.tag_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tagUserListener!=null)
                    tagUserListener.onTag(model);

            }
        });
    }

    @Override
    public int getItemCount()
    {
      if(userList ==null)
          return 0;

        return userList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tv_personname)
        TextView tv_personname;

        @BindView(R.id.iv_profileimage)
        CircleImageView iv_profileimage;

        @BindView(R.id.tv_personnumber)
        TextView tv_personnumber;

        @BindView(R.id.tag_card)
        RelativeLayout tag_card;
        @BindView(R.id.iv_tag)
        ImageView iv_tag;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

/*    public class MyFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults=new FilterResults();
            if(constraint.length()==0){
                filterResults.values=userList;
                filterResults.count=userList.size();
                return filterResults;
            }
            List<User> models=new ArrayList<User>();
            for (User contactsModel:userList){
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
//                filterArrayList= (ArrayList<User>) results.values;
                notifyDataSetChanged();
            }



        }
    }*/

    public interface TagUserListener{
        void onTag(User user);
    }

}
