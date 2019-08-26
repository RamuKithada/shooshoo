package com.android.shooshoo.tutorials;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeHelpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeHelpFragment extends Fragment {

    @BindView(R.id.full_lay)
    RelativeLayout full_lay;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tutorial_text_1)
    TextView tutorial_text_1;

    @BindView(R.id.bottom_card)
    CardView bottom_card;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int layout,inner;


    public HomeHelpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param resource Parameter 1.
     * @return A new instance of fragment HomeHelpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeHelpFragment newInstance(int resource,int inner) {
        HomeHelpFragment fragment = new HomeHelpFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, resource);
        args.putInt(ARG_PARAM2, inner);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            layout = getArguments().getInt(ARG_PARAM1);
            inner = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        switch (inner){
            case 0:
                title.setText(getString(R.string.discover));
                tutorial_text_1.setText(R.string.help_home);
                bottom_card.setVisibility(View.VISIBLE);
                break;
            case 1:
                title.setText(getString(R.string.search_home_help_title));
                tutorial_text_1.setText(R.string.search_help_text);
                bottom_card.setVisibility(View.GONE);
                break;
            case 2:
                title.setText(getString(R.string.challenge_help_title));
                tutorial_text_1.setText(R.string.challenge_help_text);
                bottom_card.setVisibility(View.GONE);
                break;
            case 3:
                title.setText(getString(R.string.winners_help_title));
                tutorial_text_1.setText(R.string.winners_help_text);
                bottom_card.setVisibility(View.GONE);
                break;
            case 4:
                title.setText(getString(R.string.feed_help_title));
                tutorial_text_1.setText(R.string.feed_help_text);
                bottom_card.setVisibility(View.GONE);
                break;
            case 5 :
                title.setText(getString(R.string.radar_help_title));
                tutorial_text_1.setText(R.string.radar_help_text);
                bottom_card.setVisibility(View.GONE);
                break;
        }
        full_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(homeActivity!=null)
                    homeActivity.hideHelp();
            }
        });
    }
BaseActivity homeActivity=null;
    @Override
    public void onAttach(Context context) {
          super.onAttach(context);
        if(context instanceof BaseActivity)
            homeActivity = (BaseActivity) context;
    }
}
