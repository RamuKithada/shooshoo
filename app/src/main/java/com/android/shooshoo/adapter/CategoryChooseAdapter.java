package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.models.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryChooseAdapter extends RecyclerView.Adapter<CategoryChooseAdapter.CatViewHolder> {
    String active="#CCCCCC",inactive="#FFFFFF";
   int[] isActive=new int[0];
   List<Category> categories=new ArrayList<Category>();
   Context context;
   int width=100;

    public CategoryChooseAdapter(Context context) {
        this.context = context;
        width=context.getResources().getDisplayMetrics().widthPixels;
    }

    public void setCategories(List<Category> categories) {

        if(categories!=null) {
            this.categories = categories;
            isActive = new int[categories.size()];
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item,null);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder catViewHolder,final int i) {
        Category category=categories.get(i);
        catViewHolder.textView.setText(category.getCategoryName());
        Picasso.with(context).load(ApiUrls.IMAGE_URL+"category/"+category.getIcon()).into(catViewHolder.imageView);
        if(isActive[i]==1)
        {
            catViewHolder.cardView.setBackgroundResource(R.drawable.cat_selected);
        }
        else
        {
            catViewHolder.cardView.setBackgroundResource(R.drawable.cat_unselected);
        }

        catViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  isActive[i]=Math.abs(isActive[i]-1);
                  if(isActive[i]==1)
                  {
                      catViewHolder.cardView.setBackgroundResource(R.drawable.cat_selected);
                  }
                  else
                  {
                      catViewHolder.cardView.setBackgroundResource(R.drawable.cat_unselected);
                  }
              }
          });


    }

    @Override
    public int getItemCount() {
        if(categories==null)
            return 0;

        return categories.size();
    }
    public String getCats(){
StringBuilder builder=new StringBuilder();
        if(categories!=null&&categories.size()>0)
        for (int index=0;index<categories.size();index++){


            if(isActive[index]==1)
            if(builder.length()==0){
                builder.append(categories.get(index).getCategoryId());
            }else {
                builder.append(',');
                builder.append(categories.get(index).getCategoryId());
            }

        }

        return builder.toString();

    }
    public int selectedSize(){
        int selected_size=0;
        if(categories!=null&&categories.size()>0)
            for (int index=0;index<categories.size();index++){
                if(isActive[index]==1)
                    selected_size++;

        }

        return selected_size;
    }

    public class CatViewHolder extends RecyclerView.ViewHolder{
         RelativeLayout cardView;
         ImageView imageView;
         TextView textView;
        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.card);
            imageView=itemView.findViewById(R.id.image);
            textView=itemView.findViewById(R.id.name);

        }
    }
}
