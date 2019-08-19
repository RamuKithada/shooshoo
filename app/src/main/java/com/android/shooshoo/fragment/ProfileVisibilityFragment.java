package com.android.shooshoo.fragment;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.shooshoo.R;
import com.android.shooshoo.models.LoginSuccess;
import com.android.shooshoo.models.Visibility;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.BaseView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * Use the {@link ProfileVisibilityFragment#newInstance} factory method to
 * create an instance of this fragment.
 * this is Profile screen to show general profile visibility to others settings options
 */
public class ProfileVisibilityFragment extends Fragment implements  CompoundButton.OnCheckedChangeListener, BaseView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @BindView(R.id.switch_profile_visits)
    SwitchCompat switch_profile_visits;
    @BindView(R.id.switch_rewards)
    SwitchCompat switch_rewards;
    @BindView(R.id.switch_friends)
    SwitchCompat switch_friends;
    @BindView(R.id.switch_show_me_radar)
    SwitchCompat switch_show_me_radar;
    @BindView(R.id.switch_country)
    SwitchCompat switch_country;
    @BindView(R.id.switch_city)
    SwitchCompat switch_city;
    @BindView(R.id.switch_cat)
    SwitchCompat switch_cat;
    @BindView(R.id.switch_age)
    SwitchCompat switch_age;
    UserSession userSession;
    ConnectionDetector connectionDetector;

    RetrofitApis retrofitApis;

    public Dialog dialog;

    public ProfileVisibilityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileVisibilityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileVisibilityFragment newInstance(String param1, String param2) {
        ProfileVisibilityFragment fragment = new ProfileVisibilityFragment();
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
        return inflater.inflate(R.layout.fragment_profile_visibility, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        dialog = new Dialog(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.loading);
        dialog.setCancelable(false);
        userSession=new UserSession(getContext());
        connectionDetector=new ConnectionDetector(getActivity());
          Visibility visibility=userSession.getVisibility();
           setVisibility(visibility);// setting the previous values that set by a user are restoring here.
    }
    private void serviceCall(){
        if(retrofitApis==null)
            retrofitApis=RetrofitApis.Factory.create(getContext());
        showProgressIndicator(true);
        retrofitApis.changeVisibility(userSession.getUserId(),String.valueOf(switch_profile_visits.isChecked()?1:0)
                ,String.valueOf(switch_rewards.isChecked()?1:0),String.valueOf(switch_friends.isChecked()?1:0)
                ,String.valueOf(switch_show_me_radar.isChecked()?1:0),String.valueOf(switch_country.isChecked()?1:0)
                ,String.valueOf(switch_city.isChecked()?1:0),String.valueOf(switch_cat.isChecked()?1:0)
                ,String.valueOf(switch_age.isChecked()?1:0)).enqueue(new Callback<LoginSuccess>() {
            @Override
            public void onResponse(Call<LoginSuccess> call, Response<LoginSuccess> response) {
                showProgressIndicator(false);
                if(response.isSuccessful()){
                    LoginSuccess success=response.body();
//                    setVisibility(success.getVisibility());
                    userSession.setVisibility(success.getVisibility());
                }
            }

            @Override
            public void onFailure(Call<LoginSuccess> call, Throwable t) {
                showProgressIndicator(false);

            }
        });


    }

    /**
     * Set the  values to all toggle buttons
     * @param visibility setting values
     */
    private void setVisibility(Visibility visibility) {
        if(visibility!=null){
            if(visibility.getProfileVisits()!=null&&!visibility.getProfileVisits().isEmpty())
                switch_profile_visits.setChecked(visibility.getProfileVisits().equalsIgnoreCase("1"));
            if(visibility.getShowmeRadar()!=null&&!visibility.getShowmeRadar().isEmpty())
                switch_show_me_radar.setChecked(visibility.getShowmeRadar().equalsIgnoreCase("1"));
            if(visibility.getRewards()!=null&&!visibility.getRewards().isEmpty())
                switch_rewards.setChecked(visibility.getRewards().equalsIgnoreCase("1"));
            if(visibility.getFriends()!=null&&!visibility.getFriends().isEmpty())
                switch_friends.setChecked(visibility.getFriends().equalsIgnoreCase("1"));
            if(visibility.getCountry()!=null&&!visibility.getCountry().isEmpty())
                switch_country.setChecked(visibility.getCountry().equalsIgnoreCase("1"));
            if(visibility.getCity()!=null&&!visibility.getCity().isEmpty())
                switch_city.setChecked(visibility.getCity().equalsIgnoreCase("1"));
            if(visibility.getAge()!=null&&!visibility.getAge().isEmpty())
                switch_age.setChecked(visibility.getAge().equalsIgnoreCase("1"));
            if(visibility.getCategories()!=null&&!visibility.getCategories().isEmpty())
                switch_cat.setChecked(visibility.getCategories().equalsIgnoreCase("1"));
        }

        switch_profile_visits.setOnCheckedChangeListener(this);
        switch_show_me_radar.setOnCheckedChangeListener(this);
        switch_rewards.setOnCheckedChangeListener(this);
        switch_friends.setOnCheckedChangeListener(this);
        switch_country.setOnCheckedChangeListener(this);
        switch_city.setOnCheckedChangeListener(this);
        switch_age.setOnCheckedChangeListener(this);
        switch_cat.setOnCheckedChangeListener(this);

    }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(connectionDetector.isConnectingToInternet())
              serviceCall();
        else
              showMessage("Please check your internet connection");

    }

    @Override
    public void showMessage(int stringId) {
        showMessage(getContext().getString(stringId));
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

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
}
