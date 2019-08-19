package com.android.shooshoo.fragment;

import android.app.Dialog;
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
import com.android.shooshoo.models.NotificationSettings;
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
 * Use the {@link NotificationSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 * this is Profile screen to show general profile visibility to others settings options
 */
public class NotificationSettingFragment extends Fragment  implements  CompoundButton.OnCheckedChangeListener, BaseView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @BindView(R.id.switch_follows)
    SwitchCompat switch_follows;
    @BindView(R.id.switch_likes)
    SwitchCompat switch_likes;
    @BindView(R.id.switch_comments)
    SwitchCompat switch_comments;
    @BindView(R.id.switch_messages)
    SwitchCompat switch_messages;
    @BindView(R.id.switch_cat)
    SwitchCompat switch_cat;
    @BindView(R.id.switch_radar)
    SwitchCompat switch_radar;
    @BindView(R.id.switch_cfollows)
    SwitchCompat switch_cfollows;
    @BindView(R.id.switch_invitation)
    SwitchCompat switch_invitation;
    @BindView(R.id.switch_people)
    SwitchCompat switch_people;
    UserSession userSession;
    ConnectionDetector connectionDetector;

    RetrofitApis retrofitApis;

    public Dialog dialog;




    public NotificationSettingFragment() {
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
    public static NotificationSettingFragment newInstance(String param1, String param2) {
        NotificationSettingFragment fragment = new NotificationSettingFragment();
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
        return inflater.inflate(R.layout.fragment_notification_setting, container, false);
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
        NotificationSettings visibility=userSession.getNotification();
        setVisibility(visibility);// setting the previous values that set by a user are restoring here.
    }



    private void setVisibility(NotificationSettings visibility) {

        if(visibility!=null){
            if(visibility.getCFollows()!=null&&!visibility.getCFollows().isEmpty())
                switch_cfollows.setChecked(visibility.getCFollows().equalsIgnoreCase("1"));
            if(visibility.getPLikes()!=null&&!visibility.getPLikes().isEmpty())
                switch_likes.setChecked(visibility.getPLikes().equalsIgnoreCase("1"));
            if(visibility.getPMessages()!=null&&!visibility.getPMessages().isEmpty())
                switch_messages.setChecked(visibility.getPMessages().equalsIgnoreCase("1"));
            if(visibility.getCCategory()!=null&&!visibility.getCCategory().isEmpty())
                switch_cat.setChecked(visibility.getCCategory().equalsIgnoreCase("1"));
            if(visibility.getCRadar()!=null&&!visibility.getCRadar().isEmpty())
                switch_radar.setChecked(visibility.getCRadar().equalsIgnoreCase("1"));
            if(visibility.getPFollows()!=null&&!visibility.getPFollows().isEmpty())
                switch_follows.setChecked(visibility.getPFollows().equalsIgnoreCase("1"));
            if(visibility.getCInvitation()!=null&&!visibility.getCInvitation().isEmpty())
                switch_invitation.setChecked(visibility.getCInvitation().equalsIgnoreCase("1"));
            if(visibility.getRPeople()!=null&&!visibility.getRPeople().isEmpty())
                switch_people.setChecked(visibility.getRPeople().equalsIgnoreCase("1"));
            if(visibility.getPComments()!=null&&!visibility.getPComments().isEmpty())
                switch_comments.setChecked(visibility.getPComments().equalsIgnoreCase("1"));
        }

        switch_cfollows.setOnCheckedChangeListener(this);
        switch_likes.setOnCheckedChangeListener(this);
        switch_messages.setOnCheckedChangeListener(this);
        switch_radar.setOnCheckedChangeListener(this);
        switch_follows.setOnCheckedChangeListener(this);
        switch_invitation.setOnCheckedChangeListener(this);
        switch_people.setOnCheckedChangeListener(this);
        switch_comments.setOnCheckedChangeListener(this);
        switch_cat.setOnCheckedChangeListener(this);




    }
    private void storeChangesInServer(){
        if(retrofitApis==null)
            retrofitApis=RetrofitApis.Factory.create(getContext());
        showProgressIndicator(true);
        retrofitApis.changeNotification(userSession.getUserId(),String.valueOf(switch_follows.isChecked()?1:0)
                ,String.valueOf(switch_likes.isChecked()?1:0),String.valueOf(switch_comments.isChecked()?1:0)
                ,String.valueOf(switch_messages.isChecked()?1:0),String.valueOf(switch_cat.isChecked()?1:0)
                ,String.valueOf(switch_radar.isChecked()?1:0),String.valueOf(switch_cfollows.isChecked()?1:0)
                ,String.valueOf(switch_invitation.isChecked()?1:0),String.valueOf(switch_people.isChecked()?1:0)).enqueue(new Callback<LoginSuccess>() {
            @Override
            public void onResponse(Call<LoginSuccess> call, Response<LoginSuccess> response) {
                showProgressIndicator(false);
                if(response.isSuccessful()){
                    LoginSuccess success=response.body();
                    userSession.setNotification(success.getNotificationSettings());
                }
            }

            @Override
            public void onFailure(Call<LoginSuccess> call, Throwable t) {
                showProgressIndicator(false);

            }
        });


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(connectionDetector.isConnectingToInternet())
            storeChangesInServer();
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
