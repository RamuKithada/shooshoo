package com.android.shooshoo.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.WinnersListFragmentPagerAdapter;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.UserSession;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WinnersUserWiseFragment#newInstance} factory method to
 * create an instance of this fragment.
 * This is used to show the List Challenges that are completed
 */
public class WinnersUserWiseFragment extends Fragment implements ViewPager.OnPageChangeListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ConnectionDetector connectionDetector;
    UserSession userSession;


    @BindView(R.id.best_lay)
    LinearLayout best_lay;

    @BindView(R.id.me_lay)
    LinearLayout me_lay;

    @BindView(R.id.friends_lay)
    LinearLayout friends_lay;


    @BindView(R.id.view_pager)
    ViewPager viewPager;

    public WinnersUserWiseFragment() {

    }
    View.OnClickListener winnersListListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {

                switch (v.getId()){
                    case R.id.best_lay:
                        if (viewPager != null) {
                            viewPager.setCurrentItem(0,true);
                        }
                        break;
                    case R.id.friends_lay:
                        if (viewPager != null) {
                            viewPager.setCurrentItem(1,true);
                        }
                        break;
                    case R.id.me_lay:
                        if (viewPager != null) {
                            viewPager.setCurrentItem(2,true);
                        }
                        break;

                }

        }
    };

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WinnersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WinnersUserWiseFragment newInstance(String param1, String param2) {
        WinnersUserWiseFragment fragment = new WinnersUserWiseFragment();
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
        return inflater.inflate(R.layout.fragment_winners_user_wise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        best_lay.setOnClickListener(winnersListListener);
        me_lay.setOnClickListener(winnersListListener);
        friends_lay.setOnClickListener(winnersListListener);
        WinnersListFragmentPagerAdapter pagerAdapter=new WinnersListFragmentPagerAdapter(getActivity(),getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);

    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        onListPageSelected(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
    public void onListPageSelected(int pos) {
        ((TextView) best_lay.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
        best_lay.getChildAt(1).setBackgroundColor(Color.parseColor("#85868A"));

        ((TextView) friends_lay.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
        friends_lay.getChildAt(1).setBackgroundColor(Color.parseColor("#85868A"));


        ((TextView) me_lay.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
        me_lay.getChildAt(1).setBackgroundColor(Color.parseColor("#85868A"));

        switch (pos){
            case 0:
                ((TextView) best_lay.getChildAt(0)).setTextColor(Color.parseColor("#F31F68"));
                best_lay.getChildAt(1).setBackgroundColor(Color.parseColor("#F31F68"));

                break;
            case 1:
                ((TextView) friends_lay.getChildAt(0)).setTextColor(Color.parseColor("#F31F68"));
                friends_lay.getChildAt(1).setBackgroundColor(Color.parseColor("#F31F68"));
                break;
            case 2:
                ((TextView) me_lay.getChildAt(0)).setTextColor(Color.parseColor("#F31F68"));
                me_lay.getChildAt(1).setBackgroundColor(Color.parseColor("#F31F68"));
                break;


        }
    }



}
