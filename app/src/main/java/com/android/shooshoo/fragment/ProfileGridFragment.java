package com.android.shooshoo.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.GridAdapter;
import com.android.shooshoo.models.Feed;
import com.android.shooshoo.presenters.ProfileGridFeeds;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.PaginationScrollListener;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.ProfileGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileGridFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 *  This is used to show the user posts list .
 */
public class ProfileGridFragment extends Fragment implements ProfileGridView {

    // Index from which pagination should start (0 is 1st page in our case)
    private static final int PAGE_START = 0;

    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoading = false;

    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;

    // total no. of pages to load. Initial load is page 0, after which 2 more pages will load.
    private int TOTAL_PAGES = 3;
    // indicates the current page which Pagination is fetching.
    private int currentPage = PAGE_START;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView textview;
    RecyclerView list;

    ArrayList<Feed> feeds=new ArrayList<>();
    GridAdapter adapter;
    LinearLayoutManager layoutManager;
    /**
     * feedsPresenter is used to load the data;
     */
    ProfileGridFeeds feedsPresenter;
    public Dialog dialog;
    public UserSession userSession;
    ConnectionDetector connectionDetector;
    private int limit=12;


    public ProfileGridFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GridFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileGridFragment newInstance(String param1, String param2) {
        ProfileGridFragment fragment = new ProfileGridFragment();
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
            endPoint = getArguments().getString(ARG_PARAM1);
            userId = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grid, container, false);
    }
    String endPoint,userId;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textview=view.findViewById(R.id.textview);
         endPoint =getArguments().getString(ARG_PARAM1);
         userId = getArguments().getString(ARG_PARAM2);
        textview.setText(endPoint);
        list=view.findViewById(R.id.list);
        dialog = new Dialog(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.loading);
        dialog.setCancelable(false);
        userSession=new UserSession(getContext());
        connectionDetector=new ConnectionDetector(getContext());
        layoutManager = new LinearLayoutManager(getContext());
        SnapHelper snapHelper = new PagerSnapHelper();
        list.setLayoutManager(layoutManager);
        adapter = new GridAdapter(getContext(), feeds);
        list.setAdapter(adapter);
//        snapHelper.attachToRecyclerView(list);
        feedsPresenter=new ProfileGridFeeds();
        feedsPresenter.attachView(this);
        list.addOnScrollListener(new PaginationScrollListener(layoutManager) {
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
            feedsPresenter.loadFeeds(endPoint,userId,limit,0);
        else
            showMessage("Check Internet connection");
    }


    @Override
    public void onPosts(List<Feed> feeds,int count) {
        if(feeds!=null) {
            this.feeds.addAll(feeds);
            adapter.notifyDataSetChanged();
        }
        TOTAL_PAGES=(int)count/6;
        isLoading=false;
        adapter.removeLoadingFooter();

    }
    private void loadNextPage() {
        if (currentPage <= TOTAL_PAGES) {
            if (connectionDetector.isConnectingToInternet())
                feedsPresenter.loadFeeds(endPoint,userId,limit,feeds.size());
            else
                showMessage("Check Internet connection");
            adapter.addLoadingFooter();

        }
        else
            isLastPage = true;
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


/*
    @Override
    public void onClick(View view, Feed feed) {

    }

    @Override
    public void onView(Feed feed) {

    }*/
}
