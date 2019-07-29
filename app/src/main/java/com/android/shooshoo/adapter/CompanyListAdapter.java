package com.android.shooshoo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.utils.ApiUrls;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.CatViewHolder> {

    Context context;
    List<Company> brandList;
    boolean isLoadingAdded = false;
    int activeColor = 0;
    int inactiveColor = 0;
    int selected_size = 0;
    SelectedChangeListener selectedChangeListener=new SelectedChangeListener() {
        @Override
        public void onSelection(int selectedSize) {

        }
    };


    public CompanyListAdapter(Context context, List<Company> brandList) {
        this.context = context;
        this.brandList = brandList;
        activeColor = Color.parseColor("#F31F68");
        inactiveColor = Color.parseColor("#00000000");

    }

    public void setSelectedChangeListener(SelectedChangeListener selectedChangeListener) {
        this.selectedChangeListener = selectedChangeListener;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.campany_list_item, null);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder catViewHolder, final int i) {
        final Company company = brandList.get(i);

        Picasso.with(context).load(ApiUrls.IMAGE_URL + "companies/" + company.getCompanyLogo()).into(catViewHolder.image);
        catViewHolder.brandName.setText(company.getCompanyName());
        if (company.getSelected() == 1) {
            catViewHolder.image.setBorderColor(activeColor);
//            catViewHolder.brandName.setTextColor(activeColor);
        } else {
            catViewHolder.image.setBorderColor(inactiveColor);
//            catViewHolder.brandName.setTextColor(inactiveColor);
        }
        catViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * here change color of the selected background
                 */
                company.setSelected(Math.abs(company.getSelected() - 1));
                if (company.getSelected() == 1) {
                    catViewHolder.image.setBorderColor(activeColor);
                    selected_size++;
//                     catViewHolder.brandName.setTextColor(activeColor);
                } else {

                    if(selected_size>0)
                       selected_size--;
                       else
                           return;
                    catViewHolder.image.setBorderColor(inactiveColor);
//                     catViewHolder.brandName.setTextColor(inactiveColor);
                }
                if(selectedChangeListener!=null)
                selectedChangeListener.onSelection(selected_size);

//                 context.startActivity(new Intent(context, CompanyDetailsActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return (brandList == null) ? 0 : brandList.size();
    }


    public void setBrands(List<Company> brands) {
        if (brands != null) {
            this.brandList.addAll(brands);
            notifyDataSetChanged();
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
    }

    /**
     * get list selected brands size
     *
     * @return size of the selected brands
     */
    public int selectedSize() {
        return selected_size;
    }

    /**
     * get list selected brands ids in String format
     *
     * @return the selected brands ids
     */
    public String getBrandIds() {
        StringBuilder builder = new StringBuilder();
        if (brandList != null && brandList.size() > 0)
            for (int index = 0; index < brandList.size(); index++) {
                if (brandList.get(index).getSelected() == 1)
                    if (builder.length() == 0) {
                        builder.append(brandList.get(index).getCompanyId());
                    } else {
                        builder.append(',');
                        builder.append(brandList.get(index).getCompanyId());
                    }
            }

        return builder.toString();

    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
    }

    public class CatViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        AppCompatTextView brandName;

        //        LinearLayout cardView;
        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.company_image);
            brandName = itemView.findViewById(R.id.name);
            image.setBorderColor(inactiveColor);
        }
    }

    public interface SelectedChangeListener {
      void   onSelection(int selectedSize);
    }



}
