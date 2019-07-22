package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Language;

import java.util.ArrayList;
import java.util.List;

public class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.RegionsHolder> {

    List<Language> languages =new ArrayList<Language>();

    Context context;

    public LanguagesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RegionsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.region_item,null);
        return new RegionsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegionsHolder regionsHolder, int pos) {
        regionsHolder.appCompatTextView.setText(languages.get(pos).getName());
    }

    @Override
    public int getItemCount() {

        if(languages ==null)
            return 0;
        return languages.size();
    }

    public List<Language> selectedLanguages() {
        return languages;
    }

    public class RegionsHolder extends RecyclerView.ViewHolder{
       AppCompatEditText appCompatTextView;

       public RegionsHolder(@NonNull View itemView) {
           super(itemView);
           appCompatTextView=itemView.findViewById(R.id.text);

       }
   }

   public void add(Language language){
        if(language==null)
            return;
        if(languages ==null)
            languages =new ArrayList<Language>();

            languages.add(language);
            notifyDataSetChanged();

   }



}
