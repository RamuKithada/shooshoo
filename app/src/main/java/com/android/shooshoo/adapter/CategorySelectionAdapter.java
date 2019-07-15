package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Category;
import com.android.shooshoo.models.CategoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This is used in Audience activity in Jackpot challenge registration Process
 */
public class CategorySelectionAdapter extends RecyclerView.Adapter<CategorySelectionAdapter.CategoryHolder> {
    Context context;
    List<Category> categoryList;
    List<CategoryModel> categoryModels =new ArrayList<CategoryModel>();
    ArrayList<String> catNames=new ArrayList<String>();



    public CategorySelectionAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        for (Category category:categoryList) {
            catNames.add(category.getCategoryName());
        }
        categoryModels.add(new CategoryModel(0,0));
        categoryModels.add(new CategoryModel(0,0));
        categoryModels.add(new CategoryModel(0,0));
    }

    @NonNull
    @Override
    public CategorySelectionAdapter.CategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_category_item,null);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategorySelectionAdapter.CategoryHolder viewHolder, final int position) {

       final CategoryModel categoryModel=categoryModels.get(position);
        setAdapters(categoryModel,viewHolder);
        viewHolder.iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryModels.remove(position);
                notifyDataSetChanged();

            }
        });



    }

    private void setAdapters(final CategoryModel categoryModel, final CategoryHolder viewHolder) {
        if(categoryList.size()>categoryModel.getCategory()) {
            ArrayAdapter<String> catArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinnet_text, catNames);
            catArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            viewHolder.categorySpinner.setAdapter(catArrayAdapter);
            viewHolder.categorySpinner.setSelection(categoryModel.getCategory());

            viewHolder.categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("position", "" + position);
                    categoryModel.setCategory(position);
//                    Category category = categoryList.get(position);

                   /* List<String> list=category.getBrandNames();
                    if (list.size()==0){
                        list.add("No Subcategory");
                    }*/
                 /*   ArrayAdapter<String> brandArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinnet_text, list);
                    brandArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    viewHolder.subcategorySpinner.setAdapter(brandArrayAdapter);
                    if(category.getBrandNames().size()>categoryModel.getSubcategory())
                    viewHolder.subcategorySpinner.setSelection(categoryModel.getSubcategory());
                    viewHolder.subcategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            categoryModel.setSubcategory(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });*/

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    /**
     * It is called to add new item to list
     */
    public void add(){
        int pos=categoryModels.size();
        categoryModels.add(new CategoryModel(0,0));
        notifyItemInserted(pos);
    }

    public List<CategoryModel> getCategoryModels() {
        return categoryModels;
    }

    public class CategoryHolder extends RecyclerView.ViewHolder{
         Spinner categorySpinner;
//         Spinner subcategorySpinner;
         ImageView iv_remove;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            categorySpinner=itemView.findViewById(R.id.spinner_category);
//            subcategorySpinner=itemView.findViewById(R.id.spinner_subcategory);
            iv_remove=itemView.findViewById(R.id.iv_remove);

        }
    }
}
