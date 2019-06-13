package com.android.shooshoo.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.CategoryWiseChallengerActivity;
import com.android.shooshoo.activity.MyChallengesActivity;
import com.android.shooshoo.adapter.HomeBrandAdapter;
import com.android.shooshoo.adapter.HomeCategoryAdapter;
import com.android.shooshoo.adapter.JackpotChallengersAdapter;
import com.android.shooshoo.adapter.SponsorChallengersAdapter;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.ChallengeModel;
import com.android.shooshoo.presenters.HomePresenter;
import com.android.shooshoo.utils.ClickListener;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.RecyclerTouchListener;
import com.android.shooshoo.views.HomeView;
import com.android.shooshoo.views.SponsorChallengeView;

import java.util.ArrayList;
import java.util.List;

/**
 * This is fragment to present Home tab view
 *
 */
public class HomeFragment extends Fragment implements View.OnClickListener,HomeView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * sponsor_viewall,jackpot_viewall,category_viewall are view buttons in respective  categories sponsor challenges,jackpot challenges,categories
     *
     */
    RelativeLayout sponsor_viewall,jackpot_viewall,category_viewalll;

    private HomeView mListener;
    //Jackpot challenge List adapter
    JackpotChallengersAdapter jackpotChallengersAdapter;

    //Sponsor challenge List adapter
    SponsorChallengersAdapter sponsorChallengersAdapter;

    //Brand  List adapter to show brand list view
    HomeBrandAdapter homeBrandAdapter;

    //Category  List adapter to show Category list view
    HomeCategoryAdapter homeCategoryAdapter;

    //  challengeModels is used to hold jackpot challenges list
    ArrayList<Challenge> sponsorChallenges=new ArrayList<Challenge>();

    //  schallengeModels is used to hold Sponsor challenges list
    ArrayList<ChallengeModel> schallengeModels=new ArrayList<ChallengeModel>();


    String[] titles=new String[]{"Beard \nChallenge","Drink Challenge","Eating Challenge","Handstand Challenge","Hips Exercise Challenge",
            "Ice Skating Challenge","Laugh Challenge","Pullups Challenge","Running Challenge","Yoga Challenge"};
    int[] images=new int[]{R.drawable.beard_challange,R.drawable.drink_challange,R.drawable.eating_challange,R.drawable.handstand_challange,
    R.drawable.hips_excersize_chalange,R.drawable.iceskating_challange,R.drawable.laugh_challange,R.drawable.pullup_challange,R.drawable.running_challange
    ,R.drawable.yoga_challange};
    String[] des=new String[]{"Large Beard","Drink 2 Liters coke","Eating 2 Biryani","1 hour Handstand ","100 HipsUps",
            "1 kilometer Ice Skating in 2 minutes","Laugh loud ","30 Pullups in 5minutes","2k Running in 90sec","5 hours Yoga"};
    String[] stitles=new String[]{"BlackFly ","Closeup smile ","Dance music ","Drink Challenge","Holiday Challenge",
            "Hotel Challenge","Ice Bucket Challenge","Swimming Challenge","World music Contest","Young Challenge"};
    int[] simages=new int[]{R.drawable.blackfly_challange,R.drawable.closeup_smile_challange,R.drawable.dance_music_challange,R.drawable.drinks_challange,
            R.drawable.holiday_challange,R.drawable.hotel_challange,R.drawable.icebucket_challange,R.drawable.swimmimg_challange,R.drawable.world_music_contest
            ,R.drawable.young_challange};
    String[] sdes=new String[]{"BlackFly bird capture","Closeup smile ads","Dance music to Puma","Drink  Coke ads","Holiday Trip flight",
            "Hotel Banjara","Ice Bucket Challenge","World Swimming Day","World music Day","Young India "};
    int[] brandimgs=new int[]{R.drawable.adidas,R.drawable.benz,R.drawable.dmart,R.drawable.flipkar,
            R.drawable.hm,R.drawable.nike,R.drawable.pepsi,R.drawable.puma,R.drawable.vokes_wagon,R.drawable.wallmart,R.drawable.puma};

    String[] brandnames=new String[]{"Adidas","Benz","Dmart","Flipkar","H & M","Nike","Pepsi","Puma","Vokes Wagon","Wallmart","Puma"};
    int[] catimgs=new int[]{
            R.drawable.animals,R.drawable.art,R.drawable.cars,R.drawable.comics,
            R.drawable.electronics,R.drawable.fitness,R.drawable.games,R.drawable.humor,R.drawable.movie,R.drawable.shopping,
            R.drawable.style,R.drawable.travel
    };
    String[] catNames=new String[]{
            "Animals","Art","Cars","Comics","Electronics",
            "Fitness","Games","Humor","Movies","Shopping",
            "Style","Travel"
    };

    HomePresenter homePresenter;
    ConnectionDetector connectionDetector;

    public HomeFragment() {
     /*   for (int index=0;index<10;index++){
            ChallengeModel model=new ChallengeModel();
            model.setDescription(des[index]);
            model.setTitle(titles[index]);
            model.setImage(images[index]);
            challengeModels.add(model);
        }*/
        for (int index=0;index<sdes.length;index++){
            ChallengeModel model=new ChallengeModel();
            model.setDescription(sdes[index]);
            model.setTitle(stitles[index]);
            model.setImage(simages[index]);
            schallengeModels.add(model);
        }

        jackpotChallengersAdapter=new  JackpotChallengersAdapter(schallengeModels);

        sponsorChallengersAdapter=new SponsorChallengersAdapter(getContext(),sponsorChallenges);
        //new SponsorChallengersAdapter(getContext(),null);
        homeBrandAdapter=new HomeBrandAdapter(brandimgs,brandnames);
        homeCategoryAdapter=new HomeCategoryAdapter(catimgs,catNames);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView brandsList=view.findViewById(R.id.brands_list);
        RecyclerView sponsorList=view.findViewById(R.id.sponsor_challengers_list);
        RecyclerView jackpotList=view.findViewById(R.id.jackpot_challengers_list);
        RecyclerView catList=view.findViewById(R.id.category_challengers_list);
        sponsor_viewall=view.findViewById(R.id.sponsor_viewall);
        jackpot_viewall=view.findViewById(R.id.jackpot_viewall);
        category_viewalll=view.findViewById(R.id.category_viewall);
        sponsor_viewall.setOnClickListener(this);
        jackpot_viewall.setOnClickListener(this);
        category_viewalll.setOnClickListener(this);
        setLayoutManager(sponsorList);
        setLayoutManager(brandsList);
        setLayoutManager(jackpotList);
        setLayoutManager(catList);
        brandsList.setAdapter(homeBrandAdapter);
        sponsorList.setAdapter(sponsorChallengersAdapter);
        jackpotList.setAdapter(jackpotChallengersAdapter);
        catList.setAdapter(homeCategoryAdapter);
        /**
         * to get item from the sponsor list when selected one item.
         */

        sponsorList.addOnItemTouchListener(new RecyclerTouchListener(getContext(), sponsorList, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(getActivity(), MyChallengesActivity.class);
                intent.putExtra("challenge",sponsorChallenges.get(position));
//                intent.putExtra("image",schallengeModels.get(position).getImage());
//                intent.putExtra("name",schallengeModels.get(position).getTitle());
//                intent.putExtra("des",schallengeModels.get(position).getDescription());
//                // to  open Challenge details  activity
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        /**
         * to get item from the jackpot challenges list when selected one item.
         * onItemclick listener for Recyclerview.
         */
        jackpotList.addOnItemTouchListener(new RecyclerTouchListener(getContext(), jackpotList, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(getActivity(), MyChallengesActivity.class);

//                intent.putExtra("image",challengeModels.get(position).getImage());
//                intent.putExtra("name",challengeModels.get(position).getTitle());
//                intent.putExtra("des",challengeModels.get(position).getDescription());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        homePresenter.attachView(this);
        if(connectionDetector.isConnectingToInternet())
        homePresenter.loadSponsor();
        else
            showMessage("No Internet Connection");
    }

    private void setLayoutManager(RecyclerView brandsList) {
        LinearLayoutManager  manager= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        brandsList.setLayoutManager(manager);
    }


    /**
     *
     * @param context is used to interact with Home activity
     *                Home Activity must implement OnFragmentInteractionListener
     *                else it throws Runtime error
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeView) {
            mListener = (HomeView) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HomeView");
        }
        homePresenter=new HomePresenter();

        connectionDetector=new ConnectionDetector(getActivity());

    }

    @Override
    public void onDetach() {
        homePresenter.detachView();
        mListener = null;
        super.onDetach();
    }

    /**
     *
     * onClick is used to click functionality of view all buttons
     * @param v  is one of the sponsor_viewall,jackpot_viewall,category_viewall
     */
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.sponsor_viewall:
         intent=new Intent(getActivity(), CategoryWiseChallengerActivity.class);
         intent.putExtra("title","Sponsor Challenges");
         intent.putExtra("catId",1);
        startActivity(intent);
        break;
            case R.id.jackpot_viewall:
                 intent=new Intent(getActivity(), CategoryWiseChallengerActivity.class);
                intent.putExtra("title","Jackpot Challenges");
                intent.putExtra("catId",2);
                startActivity(intent);
                break;
            case R.id.category_viewall:
                 intent=new Intent(getActivity(), CategoryWiseChallengerActivity.class);
                intent.putExtra("title","My Categories");
                intent.putExtra("catId",3);
                startActivity(intent);
                break;
        }

    }

    /**
     *
     * @param challenges are the data fron server to show list of sponsor challenges
     *
     */

    @Override
    public void onLoadSponsors(List<Challenge> challenges) {
    mListener.onLoadSponsors(challenges);
        sponsorChallenges.addAll(challenges);
        sponsorChallengersAdapter.notifyDataSetChanged();



    }

    @Override
    public void showMessage(int stringId) {
mListener.showMessage(stringId);
    }

    @Override
    public void showMessage(String message) {
mListener.showMessage(message);
    }

    @Override
    public void showProgressIndicator(Boolean show) {
mListener.showProgressIndicator(show);
    }


}
