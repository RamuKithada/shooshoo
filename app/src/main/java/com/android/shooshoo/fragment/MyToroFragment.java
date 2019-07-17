package com.android.shooshoo.fragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.FullVideoAdapter;
import com.android.shooshoo.models.ChallengeFeeds;
import com.android.shooshoo.models.Feed;
import com.android.shooshoo.presenters.FeedsPresenter;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.PaginationScrollListener;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.FeedsView;

import java.util.ArrayList;
import java.util.List;

import im.ene.toro.widget.Container;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyToroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyToroFragment extends Fragment implements FeedsView  {
    // TODO: Pagination  constants and variables are declared here
    // Index from which pagination should start (0 is 1st page in our case)
    private static final int PAGE_START = 0;

    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoading = false;

    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;

    // total no. of pages to load. Initial load is page 0, after which 2 more pages will load.
    private int TOTAL_PAGES = 1;
    // indicates the current page which Pagination is fetching.
    private int currentPage = PAGE_START;

    //pagination limit for one service call
    private int limit=12;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView name;
    ArrayList<Feed> feeds=new ArrayList<>();
    Container container;
    FullVideoAdapter adapter;
    LinearLayoutManager layoutManager;
    /**
     * feedsPresenter is used to load the data;
     */
    FeedsPresenter feedsPresenter;
    public Dialog dialog;
    public UserSession userSession;
    ConnectionDetector connectionDetector;
    FeedsView feedsView;
FullVideoAdapter.FeedClickListener clickListener=new FullVideoAdapter.FeedClickListener() {
    @Override
    public void onClick(View view, Feed feed) {

    }

    @Override
    public void onView(Feed feed) {

    }
};
    public MyToroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyToroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyToroFragment newInstance(String param1, String param2) {
        MyToroFragment fragment = new MyToroFragment();
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
        return inflater.inflate(R.layout.fragment_toro, container, false);
    }
    String string;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            string=getArguments().getString(ARG_PARAM1);
            name=view.findViewById(R.id.name);
            name.setText(string);
        dialog = new Dialog(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.loading);
        dialog.setCancelable(false);
        userSession=new UserSession(getContext());
        connectionDetector=new ConnectionDetector(getContext());
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        SnapHelper snapHelper = new PagerSnapHelper();
        container=view.findViewById(R.id.player_container);
        container.setLayoutManager(layoutManager);
        snapHelper.attachToRecyclerView(container);
        adapter = new FullVideoAdapter(getContext(), feeds,clickListener);
        container.setAdapter(adapter);
        container.setCacheManager(adapter);
        feedsPresenter=new FeedsPresenter();
        feedsPresenter.attachView(this);
        container.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                loadNextPage();
            }

            @Override
            public int getTotalPageCount() { return TOTAL_PAGES; }

            @Override
            public boolean isLastPage() { return isLastPage; }

            @Override
            public boolean isLoading() { return isLoading; }
        });


        if(feeds.isEmpty())
        if(connectionDetector.isConnectingToInternet())
               feedsPresenter.loadFeeds(string,limit,0);
        else
            showMessage("Check Internet connection");
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(container!=null)
            if (isVisibleToUser) {
                container.setAdapter(adapter);
            }else {
                container.setAdapter(null);
            }
        super.setUserVisibleHint(isVisibleToUser);
    }
    @Override
    public void onFeedsLoaded(ChallengeFeeds feeds) {

    }

    @Override
    public void onFeedsLoaded(List<Feed> feeds,int count) {
        if(feeds!=null)
        this.feeds.addAll(feeds);
        adapter.removeLoadingFooter();
        TOTAL_PAGES=count;
        adapter.notifyDataSetChanged();
        isLoading=false;
    }

    @Override
    public void onFeedLike(int status, String message) {

    }
    @Override
    public void onFollowed(int status, String message) {

    }

    @Override
    public void showMessage(int stringId) {
showMessage(getContext().getString(stringId));
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressIndicator(Boolean show) {
        if(dialog!=null)
            if(show){
                if(!dialog.isShowing()){
                    dialog.show();
                }
            }else {
                if(dialog.isShowing()){
                    dialog.dismiss();
                }

            }


    }




    public void loadNextPage() {
        Log.e("currentPage-->"+currentPage,"TOTAL_PAGES-->"+TOTAL_PAGES);
        if (currentPage <= TOTAL_PAGES) {
            if (connectionDetector.isConnectingToInternet())
                feedsPresenter.loadFeeds(string,limit,feeds.size());
            else
                showMessage("Check Internet connection");
            adapter.addLoadingFooter();
        }
        else
            isLastPage = true;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FeedsView){
            this.feedsView=(FeedsView) context;
        }else throw new RuntimeException(""+context.toString()+" is must be implemented by FeedView");

        if (context instanceof FullVideoAdapter.FeedClickListener){
            this.clickListener=(FullVideoAdapter.FeedClickListener) context;
        }else throw new RuntimeException(""+context.toString()+" is must be implemented by FullVideoAdapter$FeedClickListener");
    }
}
