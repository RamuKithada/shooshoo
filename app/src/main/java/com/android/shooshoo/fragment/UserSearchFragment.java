package com.android.shooshoo.fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.shooshoo.R;
import com.android.shooshoo.activity.HomeSearchActivity;
import com.android.shooshoo.adapter.UserFollowAdapter;
import com.android.shooshoo.models.User;
import com.android.shooshoo.models.UserSearchResponse;
import com.android.shooshoo.utils.RetrofitApis;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserSearchFragment extends Fragment implements TextWatcher {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   @BindView(R.id.list)
    RecyclerView list;

    HomeSearchActivity homeSearchActivity;
   UserFollowAdapter userFollowAdapter;

    ArrayList<User> users=new ArrayList<>();

    static  UserSearchFragment fragment=null;

    public UserSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilterSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserSearchFragment newInstance(String param1, String param2) {
        if (fragment == null){
            fragment = new UserSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
    }
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
        View view=inflater.inflate(R.layout.fragment_user_search, container, false);
        // Inflate the layout for this fragment

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        userFollowAdapter=new UserFollowAdapter(getContext(),users);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(userFollowAdapter);
//        searchUsers("ram");
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
////        searchUsers(s.toString());
//        if(getContext() instanceof HomeSearchActivity){
//            homeSearchActivity= (HomeSearchActivity) getContext();
//        }
          if(homeSearchActivity!=null){
              homeSearchActivity.getHomeSearchPresenter().searchUsers(s.toString());
              }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof HomeSearchActivity){
            homeSearchActivity= (HomeSearchActivity) context;
        }

    }

    @Override
    public void onDetach() {
        homeSearchActivity=null;
        super.onDetach();

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    public void onUserSearchResult(List<User> users) {
        this.users.clear();
        if(users!=null)
            this.users.addAll(users);
        if(getUserVisibleHint())
             userFollowAdapter.notifyDataSetChanged();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser)
            userFollowAdapter.notifyDataSetChanged();
        super.setUserVisibleHint(isVisibleToUser);
    }

//    RetrofitApis retrofitApis;
//    public void searchUsers(String searchKey){
//        if(retrofitApis==null)
//            retrofitApis=RetrofitApis.Factory.create(getContext());
//        retrofitApis.searchUsers(searchKey,"users").enqueue(new Callback<UserSearchResponse>() {
//            @Override
//            public void onResponse(Call<UserSearchResponse> call, Response<UserSearchResponse> response) {
//
//                if(response.isSuccessful()){
//                    UserSearchResponse followerResult=response.body();
//                     onUserSearchResult(followerResult.getUsers());
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<UserSearchResponse> call, Throwable t) {
//
//            }
//        });
//
//    }
}
