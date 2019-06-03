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

import com.android.shooshoo.R;
import com.android.shooshoo.activity.MyChallengesActivity;
import com.android.shooshoo.adapter.HomeBrandAdapter;
import com.android.shooshoo.adapter.HomeCategoryAdapter;
import com.android.shooshoo.adapter.JackpotChallengersAdapter;
import com.android.shooshoo.adapter.SponsorChallengersAdapter;
import com.android.shooshoo.models.ChallengeModel;
import com.android.shooshoo.utils.ClickListener;
import com.android.shooshoo.utils.RecyclerTouchListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    JackpotChallengersAdapter jackpotChallengersAdapter;
    JackpotChallengersAdapter sponsorChallengersAdapter;
    HomeBrandAdapter homeBrandAdapter;
    HomeCategoryAdapter homeCategoryAdapter;
    ArrayList<ChallengeModel> challengeModels=new ArrayList<ChallengeModel>();
    ArrayList<ChallengeModel> schallengeModels=new ArrayList<ChallengeModel>();
    String[] titles=new String[]{"Beard Challenge","Drink Challenge","Eating Challenge","Handstand Challenge","Hips Exercise Challenge",
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

    String[] brandnames=new String[]{"Adidas","Benz","Dmart","Flipkar","H & M","Nike","Pepsi","Puma","Vokes Wagon","Wallmart","puma"};
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


    public HomeFragment() {
        // Required empty public constructor
        for (int index=0;index<10;index++){
            ChallengeModel model=new ChallengeModel();
            model.setDescription(des[index]);
            model.setTitle(titles[index]);
            model.setImage(images[index]);
            challengeModels.add(model);
        }
        for (int index=0;index<sdes.length;index++){
            ChallengeModel model=new ChallengeModel();
            model.setDescription(sdes[index]);
            model.setTitle(stitles[index]);
            model.setImage(simages[index]);
            schallengeModels.add(model);
        }

        jackpotChallengersAdapter=new JackpotChallengersAdapter(challengeModels);

        sponsorChallengersAdapter=new JackpotChallengersAdapter(schallengeModels);
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
        setLayoutManager(sponsorList);
        setLayoutManager(brandsList);
        setLayoutManager(jackpotList);
        setLayoutManager(catList);
        brandsList.setAdapter(homeBrandAdapter);
        sponsorList.setAdapter(sponsorChallengersAdapter);
        jackpotList.setAdapter(jackpotChallengersAdapter);
        catList.setAdapter(homeCategoryAdapter);
        sponsorList.addOnItemTouchListener(new RecyclerTouchListener(getContext(), sponsorList, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(getActivity(), MyChallengesActivity.class);
                intent.putExtra("image",schallengeModels.get(position).getImage());
                intent.putExtra("name",schallengeModels.get(position).getTitle());
                intent.putExtra("des",schallengeModels.get(position).getDescription());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        jackpotList.addOnItemTouchListener(new RecyclerTouchListener(getContext(), jackpotList, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(getActivity(), MyChallengesActivity.class);
                intent.putExtra("image",challengeModels.get(position).getImage());
                intent.putExtra("name",challengeModels.get(position).getTitle());
                intent.putExtra("des",challengeModels.get(position).getDescription());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void setLayoutManager(RecyclerView brandsList) {
        LinearLayoutManager  manager= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        brandsList.setLayoutManager(manager);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getActivity(), MyChallengesActivity.class);
        startActivity(intent);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
