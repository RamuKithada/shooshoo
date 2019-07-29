package com.android.shooshoo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.CategoryWiseChallengerActivity;
import com.android.shooshoo.activity.MyChallengesActivity;
import com.android.shooshoo.activity.ViewAllChallengesActivity;
import com.android.shooshoo.adapter.ChallengesMainListAdapter;
import com.android.shooshoo.adapter.HomeBrandAdapter;
import com.android.shooshoo.adapter.HomeCategoryAdapter;
import com.android.shooshoo.adapter.JackpotChallengersAdapter;
import com.android.shooshoo.adapter.SponsorChallengersAdapter;
import com.android.shooshoo.models.Brand;
import com.android.shooshoo.models.Category;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.ChallengeModel;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.models.HomeResponse;
import com.android.shooshoo.presenters.HomePresenter;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.ClickListener;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.RecyclerTouchListener;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.HomeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.shooshoo.fragment.ViewAllChallenges_Fragment.SERVICE_TYPE;

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
    @BindView(R.id.sponsor_viewall)
    AppCompatTextView sponsor_viewall;

    @BindView(R.id.jackpot_viewall)
    AppCompatTextView jackpot_viewall;

    @BindView(R.id.category_viewall)
    AppCompatTextView category_viewall;

    @BindView(R.id.private_sponsor_viewall)
    AppCompatTextView private_sponsor_viewall;


    @BindView(R.id.sponsor_layout)
    RelativeLayout sponsor_layout;

    @BindView(R.id.jackpot_layout)
    RelativeLayout jackpot_layout;

    @BindView(R.id.category_layout)
    RelativeLayout category_layout;

    @BindView(R.id.private_sponsor_layout)
    RelativeLayout private_sponsor_layout;
    @BindView(R.id.brands_list)
    RecyclerView brandsList;
    @BindView(R.id.sponsor_challengers_list)
    RecyclerView sponsorList;
    @BindView(R.id.jackpot_challengers_list)
    RecyclerView jackpotList;
    @BindView(R.id.private_sponsor_challengers_list)
    RecyclerView privateList;
    @BindView(R.id.category_challengers_list)
    RecyclerView catList;
    @BindView(R.id.extra_list)
    RecyclerView extralist;







    private HomeView mListener;
    //Jackpot challenge List adapter
    JackpotChallengersAdapter jackpotChallengersAdapter;

    //Sponsor challenge List adapter
    SponsorChallengersAdapter sponsorChallengersAdapter;

    //Private Sponsor challenge List adapter
    SponsorChallengersAdapter privateSponsorChallengersAdapter;

    //Brand  List adapter to show brand list view
    HomeBrandAdapter homeBrandAdapter;

    //Category  List adapter to show Category list view
    HomeCategoryAdapter homeCategoryAdapter;

    //  sponsor challengeModels is used to hold jackpot challenges list
    ArrayList<Challenge> sponsorChallenges=new ArrayList<Challenge>();

    //  jackpoy challengeModels is used to hold Sponsor challenges list
    ArrayList<Challenge> jackpotChallenges=new ArrayList<Challenge>();
    ArrayList<Challenge> privateChallenges=new ArrayList<Challenge>();

    ArrayList<Company> brands=new ArrayList<Company>();
    ArrayList<Category> categories=new ArrayList<Category>();


    HomePresenter homePresenter;
    ConnectionDetector connectionDetector;
    UserSession userSession;


    public HomeFragment() {

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
         View view=inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
         return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        extralist.setLayoutManager(new LinearLayoutManager(getContext()));
        extralist.setNestedScrollingEnabled(false);
        extralist.setAdapter(new ChallengesMainListAdapter(getContext()));
        sponsor_viewall.setOnClickListener(this);
        jackpot_viewall.setOnClickListener(this);
        category_viewall.setOnClickListener(this);
        private_sponsor_viewall.setOnClickListener(this);

        setLayoutManager(sponsorList);
        setLayoutManager(brandsList);
        setLayoutManager(jackpotList);
        setLayoutManager(catList);
        setLayoutManager(privateList);
        jackpotChallengersAdapter=new  JackpotChallengersAdapter(getContext(),jackpotChallenges);
        sponsorChallengersAdapter=new SponsorChallengersAdapter(getContext(),sponsorChallenges);
        privateSponsorChallengersAdapter=new SponsorChallengersAdapter(getContext(),privateChallenges);
        //new SponsorChallengersAdapter(getContext(),null);
        homeBrandAdapter=new HomeBrandAdapter(getContext(),brands,0);
        homeCategoryAdapter=new HomeCategoryAdapter(getContext(),categories);
        brandsList.setAdapter(homeBrandAdapter);
        sponsorList.setAdapter(sponsorChallengersAdapter);
        jackpotList.setAdapter(jackpotChallengersAdapter);
        privateList.setAdapter(privateSponsorChallengersAdapter);
        catList.setAdapter(homeCategoryAdapter);
        /**
         * to get item from the sponsor list when selected one item.
         */

        sponsorList.addOnItemTouchListener(new RecyclerTouchListener(getContext(), sponsorList, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(getActivity(), MyChallengesActivity.class);
                intent.putExtra("challenge",sponsorChallenges.get(position));
                intent.putExtra("type",1);
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
                intent.putExtra("challenge",jackpotChallenges.get(position));
                intent.putExtra("type",2);
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
        homePresenter.loadHome(userSession.getUserId());
        else
            showMessage("No Internet Connection");
    }

    private void setLayoutManager(RecyclerView brandsList) {
        LinearLayoutManager  manager= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        brandsList.setLayoutManager(manager);
        brandsList.setNestedScrollingEnabled(false);
        brandsList.setHasFixedSize(true);
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
        connectionDetector=new ConnectionDetector(context);
        userSession=new UserSession(context);

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

         intent=new Intent(getActivity(), ViewAllChallengesActivity.class);
               intent.putExtra(SERVICE_TYPE, ApiUrls.SPONSERS);
                intent.putExtra("title","Sponsor Challenges");
        startActivity(intent);
        break;
            case R.id.jackpot_viewall:
                 intent=new Intent(getActivity(),  ViewAllChallengesActivity.class);
                intent.putExtra(SERVICE_TYPE,ApiUrls.JACKPOTS);
                intent.putExtra("title","Jackpot Challenges");
                startActivity(intent);
                break;
            case R.id.category_viewall:
                 intent=new Intent(getActivity(), CategoryWiseChallengerActivity.class);
                intent.putExtra("title","My Categories");
                intent.putExtra("catId",3);
                startActivity(intent);
                break;
            case R.id.private_sponsor_viewall:
                 intent=new Intent(getActivity(), ViewAllChallengesActivity.class);
                intent.putExtra(SERVICE_TYPE,ApiUrls.PRIVATE);
                intent.putExtra("title","Private Challenges");
                startActivity(intent);
                break;
        }

    }

    /**
     *
     * @param response are the data fron server to show list of sponsor challenges
     *
     */

    @Override
    public void onLoadService(HomeResponse response) {
        if(mListener!=null)
        mListener.onLoadService(response);
        if(response.getStatus()==1) {
            if(response.getSponsorChallenges()!=null) {
                sponsorChallenges.addAll(response.getSponsorChallenges());
                sponsorChallengersAdapter.notifyDataSetChanged();
            }else {
                sponsor_layout.setVisibility(View.GONE);

            }
            if(response.getPrivateChallenges()!=null) {
                privateChallenges.addAll(response.getPrivateChallenges());
                privateSponsorChallengersAdapter.notifyDataSetChanged();
            }else
                private_sponsor_layout.setVisibility(View.GONE);


        if(response.getJackpotsChallenges()!=null) {
            jackpotChallenges.addAll(response.getJackpotsChallenges());
            jackpotChallengersAdapter.notifyDataSetChanged();
        }else
            jackpot_layout.setVisibility(View.GONE);

            if(response.getBrands()!=null)
            brands.addAll(response.getBrands());
            homeBrandAdapter.notifyDataSetChanged();
            if(response.getCategories()!=null)
            {
            categories.addAll(response.getCategories());
            homeCategoryAdapter.notifyDataSetChanged();
            }else
                {
                category_layout.setVisibility(View.GONE);
            }

        }
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
