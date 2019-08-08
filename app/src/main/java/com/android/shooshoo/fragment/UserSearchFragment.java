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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.shooshoo.R;
import com.android.shooshoo.activity.HomeSearchActivity;
import com.android.shooshoo.adapter.UserFollowAdapter;
import com.android.shooshoo.models.User;
import com.android.shooshoo.models.UserSearchResponse;
import com.android.shooshoo.presenters.FeedsPresenter;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.BaseView;
import com.android.shooshoo.views.FeedsView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserSearchFragment extends Fragment implements TextWatcher,UserFollowAdapter.FollowUserListner{
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

    ConnectionDetector connectionDetector;
    UserSession userSession;


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
        userFollowAdapter.setFollowUserListner(this);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(userFollowAdapter);
        if(homeSearchActivity!=null){
            if(users.isEmpty())
            homeSearchActivity.getHomeSearchPresenter().searchUsers("a");
        }
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
        connectionDetector=new ConnectionDetector(context);
        userSession=new UserSession(context);

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
        if(getContext()!=null)
        if(isVisibleToUser)
            userFollowAdapter.notifyDataSetChanged();
        super.setUserVisibleHint(isVisibleToUser);
    }

    User user;
    @Override
    public void onFollow(User user) {
        this.user=user;
        followUser(userSession.getUserInfo().getUserId(),user.getUserId());
    }

    /**
     * Follow the user watch
     * @param userId,profileId
     */
    public void followUser(String userId,String profileId){
        Log.e("userid :"+userId,"feedId : "+profileId);
        if(homeSearchActivity!=null){
            homeSearchActivity.showProgressIndicator(true);
            RetrofitApis.Factory.create(getContext()).followUser(userId,profileId).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(homeSearchActivity!=null)
                        homeSearchActivity.showProgressIndicator(false);

                    if(response.isSuccessful()){
                        try {
                            String res=  response.body().string();
                            JSONObject object=new JSONObject(res);
                            String msg=object.optString("message");
                            int status=object.optInt("status");
                            if(status==1){
                                if(user!=null){
                                user.setSelected(!user.isSelected());
                                userFollowAdapter.notifyDataSetChanged();
                            }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {

                    }


                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if(homeSearchActivity!=null)
                    {
                        homeSearchActivity.showProgressIndicator(false);
                        homeSearchActivity.showMessage(t.getMessage());
                    }

                }
            });


        }

    }


}
