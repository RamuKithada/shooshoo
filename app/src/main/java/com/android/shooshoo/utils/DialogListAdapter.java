package com.android.shooshoo.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.shooshoo.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class DialogListAdapter extends RecyclerView.Adapter<DialogListAdapter.MyHolder> implements Filterable {
    /***
     * This the adapter to show the list in dialog
     */
    Context c;
    String[] lables;
    String[] filteredLables;




    /**
     *
     * @param c Context
     * @param strings list of the labels those are shown
     */
    public DialogListAdapter(Context c, String[] strings) {
        this.c = c;
        this.lables = strings;
        this.filteredLables=strings;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item,parent,false);
        MyHolder holder=new MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.nameTxt.setText(filteredLables[position]);
    }


    @Override
    public int getItemCount() {
        return filteredLables.length;
    }
   public int getRealPosition(int position){
        String string=filteredLables[position];
       for (int index=0;index<lables.length;index++) {
         if(string.equalsIgnoreCase(lables[index])){
             return index;
         }
       }



        return 0;
    }
 SearchViewFilter filter=null;
    @Override
    public Filter getFilter() {
        if(filter==null)
            filter=new SearchViewFilter();

        return filter;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView nameTxt;

        public MyHolder(View itemView) {
            super(itemView);
            nameTxt= (TextView) itemView.findViewById(R.id.nameTxt);
        }
    }

    public class SearchViewFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint!=null)
            Log.e("performFiltering",constraint.toString());
            ArrayList<String> strings=new ArrayList<>();
            for (String s:lables) {
               if(s.toLowerCase().contains(constraint.toString().toLowerCase())){
                   strings.add(s);
               }
            }
            FilterResults results=new FilterResults();
            results.values=strings;
            results.count=strings.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if(results.count>0)
            {
                filteredLables=new String[results.count];
                ArrayList<String> list=(ArrayList<String>) results.values;
                for (int index=0;index<list.size();index++) {
                    filteredLables[index]=list.get(index);
                }

            }
            else
                filteredLables=lables;
            notifyDataSetChanged();
        }
    }

}