package com.android.shooshoo.fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.MyChallengesActivity;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllChallenges_Fragment extends Fragment implements BaseView {

    @BindView(R.id.rv_viewallchallenges)
    RecyclerView rv_viewallchallenges;
    private ViewallChallengesAdapter viewallChallengesAdapter;
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
    public static ViewAllChallenges_Fragment newInstance(String serviceType) {
        ViewAllChallenges_Fragment fragment = new ViewAllChallenges_Fragment();
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
            throw new RuntimeException("ViewAllChallenges_Fragment Must Implement Baseview");
        }
    }

    private void init(View view)
    {
        connectionDetector=new ConnectionDetector(getActivity());
      rv_viewallchallenges.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
      viewallChallengesAdapter=new ViewallChallengesAdapter(getActivity(),challengeArrayList);
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
                Intent intent=new Intent(getActivity(), MyChallengesActivity.class);
                intent.putExtra("challenge",challengeArrayList.get(position));
                intent.putExtra("type",1);
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
                        if(intentType.equalsIgnoreCase(ApiUrls.SPONSERS)) {
                            if (viewAllChallengesResponse.getSponsors() != null && viewAllChallengesResponse.getSponsors().size() > 0)
                                challengeArrayList.addAll(viewAllChallengesResponse.getSponsors());
                        }
                        else if(intentType.equalsIgnoreCase(ApiUrls.PRIVATE)) {
                            if (viewAllChallengesResponse.getPrivates() != null && viewAllChallengesResponse.getPrivates().size() > 0)
                                challengeArrayList.addAll(viewAllChallengesResponse.getPrivates());
                        }
                        else if(intentType.equalsIgnoreCase(ApiUrls.JACKPOTS))
                        {
                            if (viewAllChallengesResponse.getJackpots() != null && viewAllChallengesResponse.getJackpots().size() > 0)
                                challengeArrayList.addAll(viewAllChallengesResponse.getJackpots());
                        }
                        else if(intentType.equalsIgnoreCase(ApiUrls.ENTERED))
                        {
                            if (viewAllChallengesResponse.getEntered() != null && viewAllChallengesResponse.getEntered().size() > 0)
                                challengeArrayList.addAll(viewAllChallengesResponse.getEntered());
                        }
                        else if(intentType.equalsIgnoreCase(ApiUrls.SAVED))
                        {
                            if (viewAllChallengesResponse.getSaved() != null && viewAllChallengesResponse.getSaved().size() > 0)
                                challengeArrayList.addAll(viewAllChallengesResponse.getSaved());
                        }
                        else if(intentType.equalsIgnoreCase(ApiUrls.CREATED))
                        {
                            if (viewAllChallengesResponse.getCreated() != null && viewAllChallengesResponse.getCreated().size() > 0)
                                challengeArrayList.addAll(viewAllChallengesResponse.getCreated());
                        }
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
