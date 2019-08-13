package com.android.shooshoo.fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.shooshoo.R;
import com.android.shooshoo.activity.HomeSearchActivity;
import com.android.shooshoo.activity.participate.MyChallengesActivity;
import com.android.shooshoo.adapter.ViewallChallengesAdapter;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.utils.ClickListener;
import com.android.shooshoo.utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChallengeSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChallengeSearchFragment extends Fragment implements TextWatcher {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    HomeSearchActivity homeSearchActivity;
    static ChallengeSearchFragment fragment;

    ViewallChallengesAdapter viewallChallengesAdapter;
    ArrayList<Challenge> arrayList=new ArrayList<Challenge>();

    public ChallengeSearchFragment() {
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
    public static ChallengeSearchFragment newInstance(String param1, String param2) {
        if(fragment==null) {
            fragment = new ChallengeSearchFragment();
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
        View view=inflater.inflate(R.layout.fragment_challenge_search, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this,view);
        viewallChallengesAdapter=new ViewallChallengesAdapter(getActivity(),arrayList);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_list.setAdapter(viewallChallengesAdapter);
        rv_list.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rv_list, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(getContext(), MyChallengesActivity.class);
                intent.putExtra("challenge",arrayList.get(position));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        if(homeSearchActivity!=null)
            if(arrayList.isEmpty())
            homeSearchActivity.getHomeSearchPresenter().searchChallenges("a");
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
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(getContext()!=null)
            if(isVisibleToUser)
                viewallChallengesAdapter.notifyDataSetChanged();
        super.setUserVisibleHint(isVisibleToUser);
    }
    public void onChallengeSearchResult(List<Challenge> challenges){
        arrayList.clear();
        if(challenges!=null)
      arrayList.addAll(challenges);
        if(getUserVisibleHint())
            viewallChallengesAdapter.notifyDataSetChanged();

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(homeSearchActivity!=null)
             homeSearchActivity.getHomeSearchPresenter().searchChallenges(s.toString());

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
