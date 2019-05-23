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
import java.util.ArrayList;
public class PriceWorthAdapter extends RecyclerView.Adapter<PriceWorthAdapter.PriceViewHolder> {

    private LayoutInflater inflater;
    public static ArrayList<EditModel> editModelArrayList;



    public PriceWorthAdapter(Context ctx, ArrayList<EditModel> editModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.editModelArrayList = editModelArrayList;
    }

    @Override
    public PriceWorthAdapter.PriceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.price_worth, parent, false);
        PriceViewHolder holder = new PriceViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final PriceWorthAdapter.PriceViewHolder holder, final int position) {
        holder.editText.setText(editModelArrayList.get(position).getEditTextValue());
        Log.d("print","yes");
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editModelArrayList.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return editModelArrayList.size();
    }

    class PriceViewHolder extends RecyclerView.ViewHolder{

        protected EditText editText;
        TextView priceTitle;
        ImageView close;

        public PriceViewHolder(View itemView) {
            super(itemView);

            editText = (EditText) itemView.findViewById(R.id.edt_price_worth);
            priceTitle=itemView.findViewById(R.id.tv_price_title);
            close=itemView.findViewById(R.id.iv_close);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    editModelArrayList.get(getAdapterPosition()).setEditTextValue(editText.getText().toString());


                }

                @Override
                public void afterTextChanged(Editable editable) {


                }
            });

        }

    }

}
