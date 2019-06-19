package com.android.shooshoo.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.shooshoo.R;

/***
 * This id the dialog to show the List of Items Context must implement FragmentListDialogListener
 */
public class CustomListFragmentDialog extends DialogFragment {

    String[] names;
    RecyclerView rv;
    SearchView search_view;
    DialogListAdapter adapter;
    FragmentListDialogListener clickListener;

    public CustomListFragmentDialog() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof FragmentListDialogListener){
            this.clickListener=(FragmentListDialogListener) context;
        }else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentListDialogListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fraglayout,container);
             names= getArguments().getStringArray("list");
             final int viewid=getArguments().getInt("view");

        //RECYCER

        search_view=rootView.findViewById(R.id.search_view);
        if(names.length>10) {
            search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    Log.e("onQueryTextChange", "" + s);
                    adapter.getFilter().filter(s);
                    return true;
                }
            });
        }else
            search_view.setVisibility(View.GONE);

        rv= (RecyclerView) rootView.findViewById(R.id.list);
        rv.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rv, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(clickListener!=null)
                {
                     clickListener.onEditView(viewid,adapter.getRealPosition(position));
                    dismiss();
                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        //ADAPTER
        adapter=new DialogListAdapter(this.getActivity(),names);
        rv.setAdapter(adapter);

        this.getDialog().setTitle("TV Shows");

        return rootView;
    }
}