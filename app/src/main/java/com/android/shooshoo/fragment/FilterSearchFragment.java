package com.android.shooshoo.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.HomeSearchActivity;
import com.android.shooshoo.models.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 * Here we write code for filter screen for search action
 */
public class FilterSearchFragment extends Fragment implements TextWatcher {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    HomeSearchActivity homeSearchActivity;

    static FilterSearchFragment fragment =null;
    public FilterSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilterSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilterSearchFragment newInstance(String param1, String param2) {
        if(fragment==null) {
            fragment = new FilterSearchFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_filter_search, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof HomeSearchActivity){
            homeSearchActivity= (HomeSearchActivity) context;
        }

    }
    @Override
    public void onDetach() {
        homeSearchActivity=null;
        super.onDetach();

    }
    public void onFilterSearchResult(List<User> users) {


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.e("FilterSearchFragment",""+s);
    }

    @Override
    public void afterTextChanged(Editable s) {
//       if(homeSearchActivity!=null)
//           homeSearchActivity.getHomeSearchPresenter().searchChallenges();
    }
}
