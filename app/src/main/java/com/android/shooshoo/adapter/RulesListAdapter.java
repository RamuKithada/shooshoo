package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.shooshoo.R;

import java.util.ArrayList;

public class RulesListAdapter extends RecyclerView.Adapter<RulesListAdapter.StringViewHolder> {
    Context mContext;
    ArrayList<String> names;
    public RulesListAdapter(Context context, ArrayList<String> objects) {
        this.mContext = context;
        this.names= objects;
    }

    @NonNull
    @Override
    public StringViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spinner_item,null);
        return new StringViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StringViewHolder stringViewHolder, int pos) {
            stringViewHolder.textView.setText(names.get(pos));
    }

    @Override
    public int getItemCount() {
        return names==null?0:names.size();
    }


    public class StringViewHolder extends RecyclerView.ViewHolder{
           TextView textView;
        public StringViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.nameTxt);

        }
    }

}