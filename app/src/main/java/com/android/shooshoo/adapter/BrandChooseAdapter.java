package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.android.shooshoo.R;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.utils.ApiUrls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/***
 * BrandChooseAdapter is used to present the list of Brands When the user registration process
 */
public class BrandChooseAdapter extends RecyclerView.Adapter<BrandChooseAdapter.CatViewHolder> {
   List<Company> brands=new ArrayList<Company>();
   Context context;
    private boolean isLoadingAdded=false;

    public BrandChooseAdapter(Context context) {
        this.context = context;
    }

    public void setBrands(List<Company> brands) {
   if(brands!=null){
       this.brands.addAll(brands);
       notifyDataSetChanged();
   }
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.brand_item,null);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder catViewHolder,final int i) {
      final   Company brand=brands.get(i);
        if(brand.getSelected()==1)
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
                  /**
                   * here change color of the selected background
                   */
                  brand.setSelected(Math.abs(brand.getSelected()-1));
                  if(brand.getSelected()==1)
                  {
                      catViewHolder.cardView.setBackgroundResource(R.drawable.cat_selected);
                  }
                  else
                  {
                      catViewHolder.cardView.setBackgroundResource(R.drawable.cat_unselected);
                  }
              }
          });

//        catViewHolder.textView.setText(brand.getBrandName());
        Picasso.with(context).load(ApiUrls.IMAGE_URL+"companies/"+brand.getCompanyLogo()).into(catViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        if(brands==null)
            return 0;
        return brands.size();
    }

    /**
     * get list selected brands size
     * @return size of the selected brands
     */
    public int selectedSize(){
        int selected_size=0;
        if(brands!=null&&brands.size()>0)
            for (int index=0;index<brands.size();index++){
                if(brands.get(index).getSelected()==1)
                    selected_size++;

            }

        return selected_size;
    }
    /**
     * get list selected brands ids in String format
     * @return the selected brands ids
     */
    public String getBrandIds(){
        StringBuilder builder=new StringBuilder();
        if(brands!=null&&brands.size()>0)
            for (int index=0;index<brands.size();index++){
                if(brands.get(index).getSelected()==1)
                    if(builder.length()==0){
                        builder.append(brands.get(index).getCompanyId());
                    }else {
                        builder.append(',');
                        builder.append(brands.get(index).getCompanyId());
                    }
            }

        return builder.toString();

    }
    public void addLoadingFooter() {
        isLoadingAdded = true;
//        add(new Comment());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

      /*  int position = comments.size() - 1;
        Comment item = getItem(position);
        if (item != null) {
            comments.remove(position);
            notifyItemRemoved(position);
        }*/
    }

    public class CatViewHolder extends RecyclerView.ViewHolder{
         RelativeLayout cardView;
         ImageView imageView;
//         TextView textView;
        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.card);
            imageView=itemView.findViewById(R.id.image);
//            textView=itemView.findViewById(R.id.name);
        }
    }
}
