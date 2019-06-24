package com.android.shooshoo.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.RulesListAdapter;

import java.util.ArrayList;

/***
 * This id the dialog to show the List of Items Context must implement FragmentListDialogListener
 */
public class RuleListFragmentDialog extends DialogFragment {

    ArrayList<String> names;
    RecyclerView rv;
    RulesListAdapter adapter;
    public RuleListFragmentDialog() {

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.dialog_rules,container);
             names= getArguments().getStringArrayList("list");

        rv= (RecyclerView) rootView.findViewById(R.id.rv_rules);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        //ADAPTER
        adapter=new RulesListAdapter(this.getActivity(),names);
        rv.setAdapter(adapter);
        getDialog().setCanceledOnTouchOutside(true);
        return rootView;
    }
}