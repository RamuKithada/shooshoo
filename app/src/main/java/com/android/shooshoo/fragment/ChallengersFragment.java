package com.android.shooshoo.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.CreateSponsorChallengeActivity;
import com.android.shooshoo.adapter.ChallengerViewPagerAdapter;


public class ChallengersFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChallengersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChallengersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChallengersFragment newInstance(String param1, String param2) {
        ChallengersFragment fragment = new ChallengersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challengers, container, false);
    }
     int currentPosition=0;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TabLayout  tabLayout=view.findViewById(R.id.tab_layout);
        final ViewPager viewPager=view.findViewById(R.id.view_pager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabLayout.setTabTextColors(Color.parseColor("#CCCCCC"), Color.parseColor("#ffffff"));
        ChallengerViewPagerAdapter challengerViewPagerAdapter=new ChallengerViewPagerAdapter(getActivity(),getChildFragmentManager(),new String[]{"Latest Challenges","Past Challenges"});
        viewPager.setAdapter(challengerViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        TextView tv_sponsor_challenge=view.findViewById(R.id.tv_sponsor_challenge);
        TextView tv_jackpot_challenge=view.findViewById(R.id.tv_jackpot_challenge);

        tv_sponsor_challenge.setOnClickListener(this);
        tv_jackpot_challenge.setOnClickListener(this);
//


    }




    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getActivity(), CreateSponsorChallengeActivity.class);
        switch (v.getId()) {
            case R.id.tv_sponsor_challenge:
                intent.putExtra("challenge_type",1);
                startActivity(intent);
                break;
            case R.id.tv_jackpot_challenge:
                intent.putExtra("challenge_type",2);
                startActivity(intent);
                break;

        }
    }
}
