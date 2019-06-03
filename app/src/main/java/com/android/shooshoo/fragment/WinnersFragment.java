package com.android.shooshoo.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.WinnersMyChallengersAdapter;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.ChallengeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WinnersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WinnersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView myChallengesList;
    WinnersMyChallengersAdapter myChallengersAdapter;
    List<Challenge> challenges=new ArrayList<Challenge>();
    ArrayList<ChallengeModel> schallengeModels=new ArrayList<ChallengeModel>();


    public WinnersFragment() {
        // Required empty public constructor
        String[] stitles=new String[]{"BlackFly ","Closeup smile ","Dance music ","Drink Challenge","Holiday Challenge",
                "Hotel Challenge","Ice Bucket Challenge","Swimming Challenge","World music Contest","Young Challenge"};
        int[] simages=new int[]{R.drawable.blackfly_challange,R.drawable.closeup_smile_challange,R.drawable.dance_music_challange,R.drawable.drinks_challange,
                R.drawable.holiday_challange,R.drawable.hotel_challange,R.drawable.icebucket_challange,R.drawable.swimmimg_challange,R.drawable.world_music_contest
                ,R.drawable.young_challange};
        String[] sdes=new String[]{"BlackFly bird capture","Closeup smile ads","Dance music to Puma","Drink  Coke ads","Holiday Trip flight",
                "Hotel Banjara","Ice Bucket Challenge","World Swimming Day","World music Day","Young India "};

        for (int index=0;index<sdes.length;index++){
            ChallengeModel model=new ChallengeModel();
            model.setDescription(sdes[index]);
            model.setTitle(stitles[index]);
            model.setImage(simages[index]);
            schallengeModels.add(model);
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WinnersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WinnersFragment newInstance(String param1, String param2) {
        WinnersFragment fragment = new WinnersFragment();
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
        return inflater.inflate(R.layout.fragment_winners, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myChallengesList =view.findViewById(R.id.my_challenges_list);
        myChallengesList.setLayoutManager(new GridLayoutManager(getActivity(),2));
        myChallengersAdapter=new WinnersMyChallengersAdapter(getActivity(),schallengeModels);
        myChallengesList.setAdapter(myChallengersAdapter);

    }
}
