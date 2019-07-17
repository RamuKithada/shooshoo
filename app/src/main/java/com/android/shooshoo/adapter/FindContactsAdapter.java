package com.android.shooshoo.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.android.shooshoo.R;
import com.android.shooshoo.models.ContactsModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 4/20/2017.
 */

public class FindContactsAdapter extends RecyclerView.Adapter<FindContactsAdapter.MyViewHolder> implements Filterable
{
    private Context context;
    private ArrayList<ContactsModel> contactsModelArrayList;
    private ArrayList<ContactsModel> filterArrayList=new ArrayList<ContactsModel>();
    public FindContactsAdapter(Context context, ArrayList<ContactsModel> contactsModelArrayList)
    {
        this.context=context;
        this.contactsModelArrayList=contactsModelArrayList;
        if(contactsModelArrayList!=null)
          this.filterArrayList=contactsModelArrayList;

        Log.e("size",""+filterArrayList.size());
    }

    @Override
    public FindContactsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.find_contactsmodel, parent, false);
        return new FindContactsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FindContactsAdapter.MyViewHolder holder, final int position)
    {
        final FindContactsAdapter.MyViewHolder myViewHolder=holder;
        final ContactsModel model=filterArrayList.get(position);
        holder.tv_personname.setText(model.getName());
        holder.tv_personnumber.setText(model.getNumber());

            if(model.isSelected()){
                holder.added.setVisibility(View.VISIBLE);
                holder.add.setVisibility(View.INVISIBLE);
            }else {
                holder.added.setVisibility(View.INVISIBLE);
                holder.add.setVisibility(View.VISIBLE);
            }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setSelected(!model.isSelected());
                if(model.isSelected()){
                    holder.added.setVisibility(View.VISIBLE);
                    holder.add.setVisibility(View.INVISIBLE);
                }else {
                    holder.added.setVisibility(View.INVISIBLE);
                    holder.add.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return filterArrayList.size();
    }

    MyFillter myFillter=null;
    @Override
    public Filter getFilter() {
        if(myFillter==null)
            myFillter=new MyFillter();

        return myFillter;
    }
    public void setSelectAll() {
        for (ContactsModel model:filterArrayList) {
            model.setSelected(true);
        }
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tv_personname)
        TextView tv_personname;
        @BindView(R.id.tv_personnumber)
        TextView tv_personnumber;
        @BindView(R.id.add)
        RelativeLayout add;
        @BindView(R.id.added)
        RelativeLayout added;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class MyFillter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults=new FilterResults();
            if(constraint.length()==0){
                filterResults.values=contactsModelArrayList;
                filterResults.count=contactsModelArrayList.size();
                return filterResults;
            }
            List<ContactsModel> models=new ArrayList<ContactsModel>();
            for (ContactsModel contactsModel:contactsModelArrayList){
                if(contactsModel.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                    models.add(contactsModel);
                }

            }

            filterResults.values=models;
            filterResults.count=models.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if(results.count>0){
                filterArrayList= (ArrayList<ContactsModel>) results.values;
                notifyDataSetChanged();
            }



        }
    }
}
