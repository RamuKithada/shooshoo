package com.android.shooshoo.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.shooshoo.R;
import com.android.shooshoo.models.Brand;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.utils.ApiUrls;
import com.squareup.picasso.Picasso;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileBrandAdapter extends RecyclerView.Adapter<ProfileBrandAdapter.CatViewHolder> {
   Context context;
   List<Company> brandList;

    public ProfileBrandAdapter(Context context, List<Company> brandList) {
        this.context = context;
        this.brandList = brandList;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.circle_image_item,null);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder catViewHolder,final int position) {

        Picasso.with(context).load(ApiUrls.IMAGE_URL+"companies/"+brandList.get(position).getCompanyLogo()).into(catViewHolder.image);
      /*   catViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 context.startActivity(new Intent(context, BrandProfileActivity.class));
             }
         });*/
    }

    @Override
    public int getItemCount() {
        return brandList==null?0:brandList.size();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
        }
    }
}
