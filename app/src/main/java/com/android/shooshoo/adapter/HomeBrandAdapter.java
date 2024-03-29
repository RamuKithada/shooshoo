package com.android.shooshoo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.CompanyDetailsActivity;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.utils.ApiUrls;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeBrandAdapter extends RecyclerView.Adapter<HomeBrandAdapter.CatViewHolder> {

    Context context;
    List<Company> brandList;
    int visibleAll=0;

    public HomeBrandAdapter(Context context, List<Company> brandList,int visibileAll) {
        this.context = context;
        this.brandList = brandList;
        this.visibleAll=visibileAll;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_brand_item,null);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder catViewHolder,final int i) {
        Picasso.with(context).load(ApiUrls.IMAGE_URL+"companies/"+brandList.get(i).getCompanyLogo()).into(catViewHolder.image);
        catViewHolder.brandName.setText(brandList.get(i).getCompanyName());
          catViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(context, CompanyDetailsActivity.class);
                 intent.putExtra("brand",brandList.get(i));
                 context.startActivity(intent);
             }
         });

    }

    @Override
    public int getItemCount() {
        return (brandList==null)?0:brandList.size();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        AppCompatTextView brandName;
        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            brandName=itemView.findViewById(R.id.name);
            if(visibleAll==1)
                brandName.setVisibility(View.VISIBLE);
            else
                brandName.setVisibility(View.GONE);
        }
    }
}
