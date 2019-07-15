package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.shooshoo.R;
import com.android.shooshoo.models.CashModel;
import com.android.shooshoo.models.PrizeBaseModel;
import com.android.shooshoo.models.PrizeModel;

import java.util.ArrayList;
import java.util.List;

public class PrizeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int PRODUCT_PRIZE=0;
    public static final int CASH_PRIZE=1;
    List<PrizeBaseModel> modelList=new ArrayList<PrizeBaseModel>();
    Context  context;
    LayoutInflater inflater;
    TextWatcher textWatcher;

    public PrizeListAdapter(Context context, List<PrizeBaseModel> modelList) {
        this.modelList = modelList;
        this.context = context;
        this.inflater=LayoutInflater.from(context);
    }

    public void setTextWatcher(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }

    @Override
    public int getItemViewType(int position) {
        if(modelList.get(position) instanceof CashModel)
            return CASH_PRIZE;
        else
            return PRODUCT_PRIZE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        View view=null;
        if(type==CASH_PRIZE){
            view = inflater.inflate(R.layout.cash_item, parent, false);
            return new CashViewHolder(view);
        }else if(type==PRODUCT_PRIZE){
            view = inflater.inflate(R.layout.prize_item, parent, false);
            return new PrizeViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int pos) {
        Log.e("onBindViewHolder","onBindViewHolder");
        if(viewHolder!=null){
            Log.e("Not Null","");
            if(getItemViewType(pos)==CASH_PRIZE){
                // TODO Here do the Cash Prize data binding
                final CashModel cashModel= (CashModel) modelList.get(pos);
                CashViewHolder holder= (CashViewHolder) viewHolder;
                holder.et_cash.setText(cashModel.getType());
                holder.et_cash_amount.setText(""+cashModel.getAmount());
                holder.et_cash_currency.setText(cashModel.getCurrency());

            }else if(getItemViewType(pos)==PRODUCT_PRIZE){
                // TODO Here do the Product Prize data binding
                PrizeViewHolder holder= (PrizeViewHolder) viewHolder;
                final PrizeModel prizeModel= (PrizeModel) modelList.get(pos);
                holder.et_prize_product.setText(prizeModel.getType());
                holder.et_prize_name.setText(prizeModel.getName());
                holder.et_prize_worth.setText(""+prizeModel.getPrizeAmount());
                holder.et_prize_number.setText(""+prizeModel.getNumber());




            }






        }else {
            Log.e("Null","");
        }
    }

    @Override
    public int getItemCount() {
        if(modelList==null)
            return 0;
        else
            return modelList.size();
    }
    public void addItem(PrizeBaseModel prizeBaseModel){
        if(prizeBaseModel!=null)
        {
            modelList.add(getItemCount(),prizeBaseModel);
            notifyDataSetChanged();
        }

    }

    public class PrizeViewHolder extends RecyclerView.ViewHolder{

        EditText et_prize_product,et_prize_name,et_prize_worth,et_prize_number;

        public PrizeViewHolder(@NonNull View itemView) {
            super(itemView);
            et_prize_product= itemView.findViewById(R.id.et_prize_product);
            et_prize_name=  itemView.findViewById(R.id.et_prize_name);
            et_prize_worth= itemView.findViewById(R.id.et_prize_worth);
            et_prize_number=itemView.findViewById(R.id.et_prize_number);


        }
    }

    public class CashViewHolder extends RecyclerView.ViewHolder{

        EditText et_cash,et_cash_amount,et_cash_currency;

        public CashViewHolder(@NonNull View itemView) {
            super(itemView);
            et_cash= itemView.findViewById(R.id.et_cash);
            et_cash_amount=  itemView.findViewById(R.id.et_cash_amount);
            et_cash_currency= itemView.findViewById(R.id.et_cash_currency);


        }
    }
}
