package com.android.shooshoo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.UserProfileActivity;
import com.android.shooshoo.adapter.WinnersListAdapter;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.Winner;
import com.android.shooshoo.presenters.WinnersPresenter;
import com.android.shooshoo.utils.ClickListener;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.RecyclerTouchListener;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.WinnersListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.android.shooshoo.utils.ApiUrls.PROFILE_IMAGE_URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WinnersListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WinnersListFragment extends Fragment implements WinnersListView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    WinnersListAdapter listAdapter;
    @BindView(R.id.winner_list)
    RecyclerView winnersList;
    List<Winner> winners=new ArrayList<Winner>();
    List<Winner> topWinners=new ArrayList<Winner>();
    @BindView(R.id.first_winner)
    LinearLayout first_winner;
    @BindView(R.id.first_winner_image)
    CircleImageView first_winner_image;
    @BindView(R.id.first_winner_name)
    TextView first_winner_name;
    @BindView(R.id.first_winner_prize)
    TextView first_winner_prize;

    @BindView(R.id.second_winner)
    LinearLayout second_winner;
    @BindView(R.id.second_winner_image)
    CircleImageView second_winner_image;
    @BindView(R.id.second_winner_name)
    TextView second_winner_name;
    @BindView(R.id.second_winner_prize)
    TextView second_winner_prize;

    @BindView(R.id.third_winner)
    LinearLayout third_winner;
    @BindView(R.id.third_winner_image)
    CircleImageView third_winner_image;
    @BindView(R.id.third_winner_name)
    TextView third_winner_name;
    @BindView(R.id.third_winner_prize)
    TextView third_winner_prize;

    ConnectionDetector connectionDetector;
    UserSession userSession;
    WinnersPresenter winnersPresenter;


    public WinnersListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WinnersListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WinnersListFragment newInstance(String param1, String param2) {
        WinnersListFragment fragment = new WinnersListFragment();
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
        View view=inflater.inflate(R.layout.winners_list_layout, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listAdapter=new WinnersListAdapter(getContext(),winners);
        winnersList.setAdapter(listAdapter);
        winnersList.addOnItemTouchListener(new RecyclerTouchListener(getContext(), winnersList, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                openProfile(listAdapter.getItem(position).getUserId(),"0");
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        winnersPresenter=new WinnersPresenter();
        winnersPresenter.attachView(this);
        connectionDetector=new ConnectionDetector(getContext());

        if(connectionDetector.isConnectingToInternet()){
            topWinners.clear();
            winners.clear();
            Challenge challenge=new Challenge();
            challenge.setType("jackpot");
            challenge.setChallengeId("97");
            winnersPresenter.getWinnersListForTheChallenge(challenge);
            topWinners.clear();
            winners.clear();

        }
    }

    @Override
    public void onWinnersListresult(List<Winner> winners) {
        if(winners!=null){
            if(winners.size()>3){
                        topWinners=winners.subList(0,3);
                try {
                    List<Winner> remainingList=winners.subList(3,winners.size());
                    if(remainingList!=null)
                        this.winners.addAll(remainingList);
                }catch (Exception e){

                }

                listAdapter.notifyDataSetChanged();
            }else {
                topWinners=winners;
            }

            if(topWinners.size()>=1){
                final Winner first=topWinners.get(0);
                first_winner.setVisibility(View.VISIBLE);
                first_winner_name.setText(first.getUserName());
                first_winner_prize.setText(first.getViews()+" Views ");
                Picasso.with(getContext()).load(PROFILE_IMAGE_URL+first.getImage()).error(R.drawable.error).into(first_winner_image);

                first_winner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        openProfile(first.getUserId(),"0");

                    }
                });

            }  if(topWinners.size()>=2){
                final Winner second=topWinners.get(1);
                second_winner.setVisibility(View.VISIBLE);
                second_winner_name.setText(second.getUserName());
                second_winner_prize.setText(second.getViews()+" Views ");
                Picasso.with(getContext()).load(PROFILE_IMAGE_URL+second.getImage()).error(R.drawable.error).into(second_winner_image);

                second_winner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        openProfile(second.getUserId(),"0");

                    }
                });

            }  if(topWinners.size()>=3){
                final Winner third=topWinners.get(2);
                third_winner.setVisibility(View.VISIBLE);
                third_winner_name.setText(third.getUserName());
                third_winner_prize.setText(third.getViews()+" Views ");
                Picasso.with(getContext()).load(PROFILE_IMAGE_URL+third.getImage()).error(R.drawable.error).into(third_winner_image);

                third_winner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        openProfile(third.getUserId(),"0");

                    }
                });

            }

        }


    }

    @Override
    public void showMessage(int stringId) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showProgressIndicator(Boolean show) {

    }
    public void openProfile(String userId,String status)
    {
        Intent userProfileIntent=new Intent(getActivity(), UserProfileActivity.class);
        userProfileIntent.putExtra("userId",userId);
        userProfileIntent.putExtra("follow",status);
        startActivity(userProfileIntent);
    }
}
