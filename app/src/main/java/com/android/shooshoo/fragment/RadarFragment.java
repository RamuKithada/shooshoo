package com.android.shooshoo.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.RadarActivity;
import com.android.shooshoo.adapter.InviteFriendsAdapter;
import com.android.shooshoo.adapter.ViewAllChallengersAdapter;
import com.android.shooshoo.models.ChallengeModel;
import com.android.shooshoo.models.CircleEntity;
import com.android.shooshoo.models.Follower;
import com.android.shooshoo.utils.OnRadarListener;
import com.android.shooshoo.utils.RadarView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RadarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RadarFragment extends Fragment implements OnRadarListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    ViewAllChallengersAdapter viewAllChallengersAdapter;
    @BindView(R.id.radarView)
    RadarView radarView;
    @BindView(R.id.seekbar)
    AppCompatSeekBar seekBar;

    @BindView(R.id.miles_lable)
    TextView milesLabel;

    @BindView(R.id.local_user_list)
    RecyclerView local_user_list;

    @BindView(R.id.suggestions_list)
    RecyclerView suggestions_list;

    @BindView(R.id.view_all_challenges)
    RelativeLayout view_all_challenges;

    @BindView(R.id.view_all_suggestions)
    RelativeLayout view_all_suggestions;

    @BindView(R.id.challenge_list)
    RecyclerView challenge_list;

    InviteFriendsAdapter inviteFriendsAdapter;
    private ArrayList<Follower> contactsModelArrayList=new ArrayList<Follower>();
    public RadarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RadarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RadarFragment newInstance(String param1, String param2) {
        RadarFragment fragment = new RadarFragment();
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
        return inflater.inflate(R.layout.fragment_radar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        radarView.setOnRadarListener(this);
        inviteFriendsAdapter=new InviteFriendsAdapter(getContext(),contactsModelArrayList);
        local_user_list.setAdapter(inviteFriendsAdapter);
        suggestions_list.setAdapter(inviteFriendsAdapter);
        view_all_challenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), RadarActivity.class));

            }
        });
        view_all_suggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                radarView.setScale(50-progress);
                milesLabel.setText(progress+" mi");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        ArrayList<PointF> pointFS=new ArrayList<>();
        pointFS.add(new PointF(0,0));
        pointFS.add(new PointF(5,-10));
        pointFS.add(new PointF(-360,360));
        pointFS.add(new PointF(30,-80));
        pointFS.add(new PointF(150,-70));
        pointFS.add(new PointF(20,-40));
        pointFS.add(new PointF(-50,-30));
        pointFS.add(new PointF(-140,60));
        pointFS.add(new PointF(80,50));
        pointFS.add(new PointF(25,10));
        pointFS.add(new PointF(-66,80));
        pointFS.add(new PointF(44,50));
        pointFS.add(new PointF(-10,10));
        ArrayList<CircleEntity> circleEntities=new ArrayList<CircleEntity>();
        for (int i=0;i<pointFS.size();i++)
        {
            circleEntities.add(new CircleEntity(pointFS.get(i),i%2));
        }
        radarView.setPointFS(circleEntities);
        challenge_list.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        ArrayList<ChallengeModel> challengeModels=new ArrayList<ChallengeModel>();
        String[] titles=new String[]{"Beard Challenge","Drink Challenge","Eating Challenge","Handstand Challenge","Hips Exercise Challenge",
                "Ice Skating Challenge","Laugh Challenge","Pullups Challenge","Running Challenge","Yoga Challenge"};
        int[] images=new int[]{R.drawable.beard_challange,R.drawable.drink_challange,R.drawable.eating_challange,R.drawable.handstand_challange,
                R.drawable.hips_excersize_chalange,R.drawable.iceskating_challange,R.drawable.laugh_challange,R.drawable.pullup_challange,R.drawable.running_challange
                ,R.drawable.yoga_challange};
        String[] des=new String[]{"Large Beard","Drink 2 Liters coke","Eating 2 Biryani","1 hour Handstand ","100 HipsUps",
                "1 kilometer Ice Skating in 2 minutes","Laugh loud ","30 Pullups in 5minutes","2k Running in 90sec","5 hours Yoga"};
        for (int index=0;index<10;index++){
            ChallengeModel model=new ChallengeModel();
            model.setDescription(des[index]);
            model.setTitle(titles[index]);
            model.setImage(images[index]);
            challengeModels.add(model);

            Follower follower=new Follower();
            follower.setUserName("Dummy");
            follower.setImage("");
            follower.setFromId("");
            contactsModelArrayList.add(follower);

        }
        viewAllChallengersAdapter=new ViewAllChallengersAdapter(getActivity(),challengeModels);
        challenge_list.setAdapter(viewAllChallengersAdapter);
    }

    @Override
    public void onItemClicked(float x, float y,CircleEntity entity) {
        if(entity.getType()==0) {
            if (popupWindow != null)
                if (popupWindow.isShowing())
                    popupWindow.dismiss();
            showDialog((int) x, (int) y);
        }else if(entity.getType()==1){
          local_user_list.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void dismissPopup() {
        local_user_list.setVisibility(View.GONE);
        if(popupWindow!=null)
            if(popupWindow.isShowing())
                popupWindow.dismiss();
    }

    PopupWindow popupWindow;
    private void showDialog(int x, int y) {
        //instantiate the popup.xml layout file
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.popup_dialog,null);
        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //instantiate popup window
        popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //display the popup window
        popupWindow.showAsDropDown(radarView, x, -(radarView.getHeight()-y));

    }

}
