package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Category;
import com.android.shooshoo.utils.ApiUrls;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.CatViewHolder> {
    Context context;
    List<Category> categoryList;

    public HomeCategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_cat_item,null);
        return new CatViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder catViewHolder,final int position) {
        catViewHolder.name.setText(categoryList.get(position).getCategoryName());
        Picasso.with(context).load(ApiUrls.IMAGE_URL+"category/"+categoryList.get(position).getIcon()).resize(200,0).placeholder(R.drawable.giphy).into(catViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return categoryList==null?0:categoryList.size();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
        }
    }
}
