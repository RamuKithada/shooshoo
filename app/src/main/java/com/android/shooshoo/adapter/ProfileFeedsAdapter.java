package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.android.shooshoo.utils.ApiUrls.SPONSOR_FEEDS_VIDEO_URL;

public class ProfileFeedsAdapter extends RecyclerView.Adapter<ProfileFeedsAdapter.CatViewHolder> {
    Context context;
    List<Post> posts;

    public ProfileFeedsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_feed_list_item,null);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder catViewHolder,final int pos) {
        String url=SPONSOR_FEEDS_VIDEO_URL+posts.get(pos).getType()+"/"+posts.get(pos).getChallengeId()+"/"+posts.get(pos).getUrl();
        Picasso.with(catViewHolder.itemView.getContext()).load(url).into(catViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return posts==null?0:posts.size();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
           imageView= itemView.findViewById(R.id.image);
        }
    }
}
