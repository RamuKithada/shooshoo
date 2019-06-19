package com.android.shooshoo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.MyChallengesActivity;
import com.android.shooshoo.adapter.SponsorChallengersAdapter;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.ChallengeResponse;
import com.android.shooshoo.utils.ClickListener;
import com.android.shooshoo.utils.RecyclerTouchListener;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.BaseView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/***
 * Challenges list to show Challenges in gridview in ViewPager of the {@link ChallengersFragment}
 */

public class ChallengeListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    public SponsorChallengersAdapter adapter=null;
    List<Challenge> challenges=new ArrayList<Challenge>();
    RecyclerView recyclerView;
//    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChallengeListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ChallengeListFragment newInstance(int columnCount) {
        ChallengeListFragment fragment = new ChallengeListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        adapter=  new SponsorChallengersAdapter(getContext(),challenges);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenge_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = view.getContext();
         recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(adapter);
        String endpoint=null;
   if(mColumnCount==0){
       endpoint="latest";
   }else if(mColumnCount==1){
       endpoint="past";
   }else if(mColumnCount==2){
       endpoint="past";
   }

            Log.e("mColumnCount",""+mColumnCount);
   baseView.showProgressIndicator(true);
        RetrofitApis.Factory.create(getContext()).getChallenges(endpoint).enqueue(new Callback<ChallengeResponse>() {
            @Override
            public void onResponse(Call<ChallengeResponse> call, Response<ChallengeResponse> response) {
                baseView.showProgressIndicator(false);
                if(response.isSuccessful()){
                   ChallengeResponse challengeResponse=response.body();
                   if(challengeResponse.getStatus()==1){
                       if(mColumnCount==0){
                             adapter.setChallenges(challengeResponse.getLatest());
                       }else if(mColumnCount==1){
                             adapter.setChallenges(challengeResponse.getPast());
                       }
                       recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
                           @Override
                           public void onClick(View view, int position) {
//                               Toast.makeText(getContext(),""+position,Toast.LENGTH_SHORT).show();
                               challenges=adapter.getChallenges();
                               if(challenges!=null) {
                                   Intent intent = new Intent(getActivity(), MyChallengesActivity.class);
                                   intent.putExtra("challenge",challenges.get(position));
                                   startActivity(intent);
                               }
                           }

                           @Override
                           public void onLongClick(View view, int position) {

                           }
                       }));
                   }
                }
            }

            @Override
            public void onFailure(Call<ChallengeResponse> call, Throwable t) {
                baseView.showProgressIndicator(false);

            }
        });

    }
BaseView baseView=null;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BaseView) {
            baseView = (BaseView) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement BaseView");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }


}
