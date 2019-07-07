package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.EditModel;
import com.android.shooshoo.models.Winner;
import com.android.shooshoo.utils.ApiUrls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.android.shooshoo.utils.ApiUrls.PROFILE_IMAGE_URL;

public class WinnersListAdapter extends RecyclerView.Adapter<WinnersListAdapter.WinnerViewHolder> {

Context context;
List<Winner> winners;

    public WinnersListAdapter(Context ctx,List<Winner> winners){
        this.winners=winners;
        this.context=ctx;

    }

    @Override
    public WinnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.winners_listitem, parent, false);
        WinnerViewHolder holder = new WinnerViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final WinnerViewHolder holder, final int position) {
                holder.rank.setText((position+3)+"th");
                holder.prizeMoney.setText(1000+"E Cash");
                Winner winner=winners.get(position);
        Picasso.with(context).load(PROFILE_IMAGE_URL+winner.getImage()).error(R.drawable.profile_1).into(holder.image);
        holder.name.setText(winner.getUserName());

    }

    @Override
    public int getItemCount() {
        if(winners==null)return 0;

        return winners.size();
    }

    class WinnerViewHolder extends RecyclerView.ViewHolder{

        TextView name,rank,prizeMoney;
        ImageView image;

        public WinnerViewHolder(View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            rank=itemView.findViewById(R.id.rank);
            prizeMoney=itemView.findViewById(R.id.prize_money);
            image=itemView.findViewById(R.id.image);



        }

    }

}
