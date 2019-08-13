package com.android.shooshoo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.shooshoo.R;

public class ChattingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
 if(viewType==0){
     View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_text_admin,null);
     return new ChatAdminHolder(view);
 }else if(viewType==2){
     View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_text_mine,null);
     return new ChatOthersHolder(view);
 }else {
     View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_text_others,null);
     return new ChatMineHolder(view);
 }

    }

    @Override
    public int getItemViewType(int position) {
        if(position%5==0)
            return 0;
        if(position%3==0)
               return 1;
            else
                return 2;

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder catViewHolder, final int i) {



    }

    @Override
    public int getItemCount() {
        return 16;
    }

    public class ChatAdminHolder extends RecyclerView.ViewHolder{
        TextView admin_date;

        public ChatAdminHolder(@NonNull View itemView) {
            super(itemView);
           admin_date= itemView.findViewById(R.id.admin_date);


        }
    }
    public class ChatOthersHolder extends RecyclerView.ViewHolder{
        TextView message_receive,time_receive;

        public ChatOthersHolder(@NonNull View itemView) {
            super(itemView);
            message_receive= itemView.findViewById(R.id.message_receive);
            time_receive= itemView.findViewById(R.id.time_receive);


        }
    }
    public class ChatMineHolder extends RecyclerView.ViewHolder{
        TextView message_sent,time_sent;

        public ChatMineHolder(@NonNull View itemView) {
            super(itemView);
            message_sent= itemView.findViewById(R.id.message_sent);
            time_sent= itemView.findViewById(R.id.time_sent);



        }
    }
}
