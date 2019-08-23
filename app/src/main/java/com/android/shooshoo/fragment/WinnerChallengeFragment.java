package com.android.shooshoo.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.participate.MyChallengesActivity;
import com.android.shooshoo.activity.sponsor.SponsorChallenge;
import com.android.shooshoo.activity.winners.WinnersListActivity;
import com.android.shooshoo.adapter.SponsorChallengersAdapter;
import com.android.shooshoo.adapter.ViewallChallengesAdapter;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.ViewAllChallengesResponse;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.ClickListener;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.RecyclerTouchListener;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.BaseView;
import com.android.shooshoo.views.ChallengesView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WinnerChallengeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WinnerChallengeFragment extends Fragment implements BaseView{

    @BindView(R.id.rv_viewallchallenges)
    RecyclerView rv_viewallchallenges;
    private SponsorChallengersAdapter viewallChallengesAdapter;
    private BaseView baseView;
    private ConnectionDetector connectionDetector;
    private int totalcount;
    private ArrayList<Challenge> challengeArrayList=new ArrayList<>();
    private String intentType="created";
    public static final String SERVICE_TYPE="serviceType";
    UserSession userSession;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_view_all_challenges, container, false);
        ButterKnife.bind(this,view);
        intentType=getArguments().getString(SERVICE_TYPE);
        init(view);
        return view;
    }
    public static WinnerChallengeFragment newInstance(String serviceType) {
        WinnerChallengeFragment fragment = new WinnerChallengeFragment();
        Bundle args = new Bundle();
        args.putString(SERVICE_TYPE, serviceType);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userSession=new UserSession(context);
        if(context instanceof BaseView)

            baseView= (BaseView) context;
        else
        {
            throw new RuntimeException("WinnerChallengeFragment must Implement Baseview");
        }
    }

    private void init(View view)
    {
        connectionDetector=new ConnectionDetector(getActivity());
        rv_viewallchallenges.setLayoutManager(new GridLayoutManager(getActivity(),2,LinearLayoutManager.VERTICAL,false));
        viewallChallengesAdapter=new SponsorChallengersAdapter(getActivity(),challengeArrayList);
        rv_viewallchallenges.setAdapter(viewallChallengesAdapter);

        if(connectionDetector.isConnectingToInternet())
            setDataToList(userSession.getUserId(),"20","0");
        else
            showMessage("Please check internet connection");

        rv_viewallchallenges.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = ((LinearLayoutManager)recyclerView.getLayoutManager());
                int pos = layoutManager.findLastCompletelyVisibleItemPosition();
                int numItems = recyclerView.getAdapter().getItemCount();
                //Log.e("pos"+pos,"numitems "+numItems);
                if(pos>0 && totalcount!=numItems) {
                    if ((pos + 1) >= numItems) {
                        if (connectionDetector.isConnectingToInternet()) {
                            setDataToList(userSession.getUserId(),"20", "" + numItems);
                        } else
                            showMessage("Please check internet connection");
                    }
                }
            }
        });

        rv_viewallchallenges.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rv_viewallchallenges, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(getActivity(), WinnersListActivity.class);
                intent.putExtra("challenge",challengeArrayList.get(position));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void setDataToList(String userid,String limit,String offset)
    {
        RetrofitApis retrofitApis=RetrofitApis.Factory.create(getActivity());
        Call<ViewAllChallengesResponse> call=retrofitApis.viewAllChallenges(intentType,userid,limit,offset);
        showProgressIndicator(true);
        call.enqueue(new Callback<ViewAllChallengesResponse>() {
            @Override
            public void onResponse(Call<ViewAllChallengesResponse> call, Response<ViewAllChallengesResponse> response) {
                showProgressIndicator(false);
                if(response.isSuccessful()){
                    ViewAllChallengesResponse viewAllChallengesResponse=response.body();
                    if(viewAllChallengesResponse.getStatus()==1)
                    {
                        totalcount=viewAllChallengesResponse.getCount();

                        if (viewAllChallengesResponse.getChallenges() != null && viewAllChallengesResponse.getChallenges().size() > 0)
                            challengeArrayList.addAll(viewAllChallengesResponse.getChallenges());
                    }
                    viewallChallengesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ViewAllChallengesResponse> call, Throwable t) {
                showProgressIndicator(false);
            }
        });
    }

    @Override
    public void showMessage(int stringId) {
        baseView.showMessage(stringId);
    }

    @Override
    public void showMessage(String message) {
        baseView.showMessage(message);
    }

    @Override
    public void showProgressIndicator(Boolean show) {
        baseView.showProgressIndicator(show);
    }
}
