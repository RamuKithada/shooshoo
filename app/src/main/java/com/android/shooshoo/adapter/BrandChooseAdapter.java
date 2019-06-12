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
import com.android.shooshoo.models.Brand;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/***
 * BrandChooseAdapter is used to present the list of Brands When the user registration process
 */
public class BrandChooseAdapter extends RecyclerView.Adapter<BrandChooseAdapter.CatViewHolder> {
    String active="#CCCCCC",inactive="#FFFFFF";
   int[] isActive=new int[0];
   List<Brand> brands=new ArrayList<Brand>();
   Context context;

    public BrandChooseAdapter(Context context) {
        this.context = context;
    }

    public void setBrands(List<Brand> brands) {
   if(brands!=null){
       isActive=new int[brands.size()];
       this.brands = brands;
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
                  /**
                   * here change color of the selected background
                   */
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
        Brand brand=brands.get(i);
        catViewHolder.textView.setText(brand.getBrandName());
        Picasso.with(context).load(ApiUrls.IMAGE_URL+"brands/"+brand.getIcon()).into(catViewHolder.imageView);

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
                if(isActive[index]==1)
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
                if(isActive[index]==1)
                    if(builder.length()==0){
                        builder.append(brands.get(index).getBrandId());
                    }else {
                        builder.append(',');
                        builder.append(brands.get(index).getBrandId());
                    }
            }

        return builder.toString();

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
