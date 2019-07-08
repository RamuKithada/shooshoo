package com.android.shooshoo.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.CreateSponsorChallengeActivity;
import com.android.shooshoo.activity.SponsorChallenge;
import com.android.shooshoo.adapter.ChallengerViewPagerAdapter;
import com.android.shooshoo.adapter.ChallengesMainListAdapter;
import com.android.shooshoo.adapter.WinnersListAdapter;
import com.android.shooshoo.adapter.WinnersMyChallengersAdapter;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.presenters.ChallengesPresenter;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.BaseView;
import com.android.shooshoo.views.ChallengesView;

import java.util.ArrayList;
import java.util.List;

/**Use the {@link ChallengersFragment#newInstance} factory method to
 * create an instance of this fragment.
 * To show the Challenges screen in Home Activity.This is the Challenges Tab result view
 */

public class ChallengersFragment extends Fragment implements View.OnClickListener, ChallengesView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView list_entered,list_created,list_saved;
    WinnersMyChallengersAdapter enteredListAdapter,createdListAdapter,savedListAdapter;
    List<Challenge> entered=new ArrayList<Challenge>(),created=new ArrayList<Challenge>(),saved=new ArrayList<Challenge>();
    UserSession userSession;
    ConnectionDetector connectionDetector;
    private BaseView mListener;

    ChallengesPresenter challengesPresenter;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list_entered=view.findViewById(R.id.list_entered);
        list_created=view.findViewById(R.id.list_created);
        list_saved=view.findViewById(R.id.list_saved);
        createdListAdapter=new WinnersMyChallengersAdapter(getContext(),created);
        enteredListAdapter=new WinnersMyChallengersAdapter(getContext(),entered);
        savedListAdapter=new WinnersMyChallengersAdapter(getContext(),saved);
        list_created.setAdapter(createdListAdapter);
        list_saved.setAdapter(savedListAdapter);
        list_entered.setAdapter(enteredListAdapter);
        AppCompatTextView tv_sponsor_challenge=view.findViewById(R.id.tv_sponsor_challenge);
        AppCompatTextView tv_jackpot_challenge=view.findViewById(R.id.tv_jackpot_challenge);
        tv_sponsor_challenge.setOnClickListener(this);
        tv_jackpot_challenge.setOnClickListener(this);
        if(connectionDetector.isConnectingToInternet())
        {
            challengesPresenter.getChallenges(userSession.getUserId());
        }  else
            showMessage("No Internet Connection");
//


    }




    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_sponsor_challenge:
                Intent intentSponsor=new Intent(getActivity(), SponsorChallenge.class);
                startActivity(intentSponsor);
                break;
            case R.id.tv_jackpot_challenge:
                Intent intentJackpot=new Intent(getActivity(), CreateSponsorChallengeActivity.class);
                intentJackpot.putExtra("challenge_type",2);
                startActivity(intentJackpot);
                break;

        }
    }

    @Override
    public void onSavedChallenges(List<Challenge> challengeList) {
        saved.addAll(challengeList);
        savedListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEnteredChallenges(List<Challenge> challengeList) {
        entered.addAll(challengeList);
        enteredListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreatedChallenges(List<Challenge> challengeList) {
        created.addAll(challengeList);
        createdListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseView) {
            mListener = (BaseView) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HomeView");
        }
        challengesPresenter=new ChallengesPresenter();
        challengesPresenter.attachView(this);
        connectionDetector=new ConnectionDetector(context);
        userSession=new UserSession(context);

    }

    @Override
    public void onDetach() {
        challengesPresenter.detachView();
        mListener = null;
        super.onDetach();
    }



    @Override
    public void showMessage(int stringId) {
        if(mListener!=null)
            mListener.showMessage(stringId);
    }

    @Override
    public void showMessage(String message) {
        if(mListener!=null)
            mListener.showMessage(message);
    }

    @Override
    public void showProgressIndicator(Boolean show) {
        if(mListener!=null)
            mListener.showProgressIndicator(show);
    }
}
