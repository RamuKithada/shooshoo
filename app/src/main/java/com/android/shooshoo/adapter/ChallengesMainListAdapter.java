package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.shooshoo.R;
import com.android.shooshoo.models.ChallengeModel;

import java.util.ArrayList;

public class ChallengesMainListAdapter extends RecyclerView.Adapter<ChallengesMainListAdapter.MainListHolder> {

     Context context;

    public ChallengesMainListAdapter(Context context) {
        this.context = context;
        for (int index=0;index<10;index++){
            ChallengeModel model=new ChallengeModel();
            model.setDescription(des[index]);
            model.setTitle(titles[index]);
            model.setImage(images[index]);
            challengeModels.add(model);
        }
    }

    ArrayList<ChallengeModel> challengeModels=new ArrayList<ChallengeModel>();
    String[] titles=new String[]{"Beard Challenge","Drink Challenge","Eating Challenge","Handstand Challenge","Hips Exercise Challenge",
            "Ice Skating Challenge","Laugh Challenge","Pullups Challenge","Running Challenge","Yoga Challenge"};
    int[] images=new int[]{R.drawable.beard_challange,R.drawable.drink_challange,R.drawable.eating_challange,R.drawable.handstand_challange,
            R.drawable.hips_excersize_chalange,R.drawable.iceskating_challange,R.drawable.laugh_challange,R.drawable.pullup_challange,R.drawable.running_challange
            ,R.drawable.yoga_challange};
    String[] des=new String[]{"Large Beard","Drink 2 Liters coke","Eating 2 Biryani","1 hour Handstand ","100 HipsUps",
            "1 kilometer Ice Skating in 2 minutes","Laugh loud ","30 Pullups in 5minutes","2k Running in 90sec","5 hours Yoga"};




    @NonNull
    @Override
    public MainListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_of_list_item,viewGroup,false);
        return new MainListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainListHolder mainListHolder, int i) {
        ViewAllChallengersAdapter viewAllChallengersAdapter=new ViewAllChallengersAdapter(context,challengeModels);
        mainListHolder.rv_list.setAdapter(viewAllChallengersAdapter);
    }

    @Override
    public int getItemCount() {
        return 16;
    }

    public class MainListHolder extends RecyclerView.ViewHolder{
       RecyclerView rv_list;
       AppCompatTextView tv_title,tv_all;

     public MainListHolder(@NonNull View itemView) {
         super(itemView);
         rv_list=itemView.findViewById(R.id.list);
         rv_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
         tv_title=itemView.findViewById(R.id.tv_title);
         tv_all=itemView.findViewById(R.id.tv_all);
     }
 }
}
