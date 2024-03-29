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
/***
 * CategoryChooseAdapter is used to present the list of Categories When the user registration process
 */
public class CategoryChooseAdapter extends RecyclerView.Adapter<CategoryChooseAdapter.CatViewHolder> {
    String active="#CCCCCC",inactive="#FFFFFF";
   List<Category> categories=new ArrayList<Category>();
   Context context;
    boolean isLoadingAdded;

    public CategoryChooseAdapter(Context context) {
        this.context = context;
    }

    /***
     *  to attach categories
     * @param categories list of categories
     */
    public void setCategories(List<Category> categories) {

        if(categories!=null) {
            this.categories.addAll(categories);
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
       final Category category=categories.get(i);
        catViewHolder.textView.setText(category.getCategoryName());
        /**
         * here change color of the selected background
         */

        if(category.getSelected()==1)
        {
            catViewHolder.cardView.setBackgroundResource(R.drawable.cat_selected);
            catViewHolder.textView.setTextColor(context.getResources().getColor(R.color.white_text));
        }
        else
        {
            catViewHolder.cardView.setBackgroundResource(R.drawable.cat_unselected);
            catViewHolder.textView.setTextColor(context.getResources().getColor(R.color.bg_color));
        }

        catViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            /**
             * here change color of the selected background
             */
              @Override
              public void onClick(View v) {
                  category.setSelected(Math.abs(category.getSelected()-1));
                  if(category.getSelected()==1)
                  {
                      catViewHolder.cardView.setBackgroundResource(R.drawable.cat_selected);
                      catViewHolder.textView.setTextColor(context.getResources().getColor(R.color.white_text));
                  }
                  else
                  {
                      catViewHolder.cardView.setBackgroundResource(R.drawable.cat_unselected);
                      catViewHolder.textView.setTextColor(context.getResources().getColor(R.color.bg_color));
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
    /**
     * get list selected categories id in String format
     * @return the selected categories ids
     */
    public String getCats(){
StringBuilder builder=new StringBuilder();
        if(categories!=null&&categories.size()>0)
        for (int index=0;index<categories.size();index++){


            if(categories.get(index).getSelected()==1)
            if(builder.length()==0){
                builder.append(categories.get(index).getCategoryId());
            }else {
                builder.append(',');
                builder.append(categories.get(index).getCategoryId());
            }

        }

        return builder.toString();

    }
    /**
     * get list selected categories size
     * @return size of the selected categories
     */
    public int selectedSize(){
        int selected_size=0;
        if(categories!=null&&categories.size()>0)
            for (int index=0;index<categories.size();index++){
                if(categories.get(index).getSelected()==1)
                    selected_size++;

        }
        return selected_size;
    }
    public void addLoadingFooter() {
        isLoadingAdded = true;
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
    }

    public class CatViewHolder extends RecyclerView.ViewHolder{
         RelativeLayout cardView;
         TextView textView;
        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.card);
            textView=itemView.findViewById(R.id.name);

        }
    }
}
