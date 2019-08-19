package com.android.shooshoo.fragment;


import android.content.Context;
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
import com.android.shooshoo.adapter.ChatContactAdapter;
import com.android.shooshoo.adapter.RecentChatMsgAdapter;
import com.android.shooshoo.models.Notification;
import com.android.shooshoo.presenters.NotificationPresenter;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.PaginationScrollListener;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.HomeView;
import com.android.shooshoo.views.NotificationView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Use the {@link ChatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 * this is Chats screen to show notifications list when use click in chat icon in top menu
 */
public class ChatsFragment extends Fragment implements NotificationView {



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


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * contact_list is used to show the frind of the challenge.
     * and
     * getNotificationList is used to show the list recent notifications received on his application.
     */
    private RecyclerView contactList,notificatonList;
    private HomeView mListener;

    /**
     * this fetch data from server .
     */
    private NotificationPresenter notificationPresenter;
    private ConnectionDetector connectionDetector;
    private UserSession userSession;
    RecentChatMsgAdapter recentChatMsgAdapter;
    List<Notification> notifications=new ArrayList<>();

     public ChatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatsFragment newInstance(String param1, String param2) {
        ChatsFragment fragment = new ChatsFragment();
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
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactList=view.findViewById(R.id.contact_list);
        notificatonList=view.findViewById(R.id.chat_notification_list);
        contactList.setAdapter(new ChatContactAdapter(getActivity()));
        recentChatMsgAdapter=new RecentChatMsgAdapter(notifications,getContext());
        notificatonList.setAdapter(recentChatMsgAdapter);
        /***
         *  here we one the pagination for the load more items when scroll is come to end of the list.
         */
        notificatonList.addOnScrollListener(new PaginationScrollListener((LinearLayoutManager) notificatonList.getLayoutManager()) {
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
        if(connectionDetector.isConnectingToInternet())
           loadNextPage();
    }

    private void loadNextPage() {
        if (currentPage < TOTAL_PAGES) {
            if (connectionDetector.isConnectingToInternet())
                notificationPresenter.getNotificationList(userSession.getUserId(), 100, currentPage);
            recentChatMsgAdapter.addLoadingFooter();
        }
        else
            isLastPage = true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeView) {
            mListener = (HomeView) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HomeView");
        }
        notificationPresenter=new NotificationPresenter();
        notificationPresenter.attachView(this);
        connectionDetector =new ConnectionDetector(context);
        userSession=new UserSession(context);
    }

    @Override
    public void onNotificationList(List<Notification> list,int count) {
        recentChatMsgAdapter.addNotifications(list);
        isLoading=false;
        recentChatMsgAdapter.removeLoadingFooter();
        TOTAL_PAGES=count;
        currentPage=recentChatMsgAdapter.getItemCount();

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

    @Override
    public void onDetach() {
        notificationPresenter.detachView();
        super.onDetach();

    }
}
