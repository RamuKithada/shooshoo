package com.android.shooshoo.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.SupportNowActivity;
import com.android.shooshoo.adapter.ProfileBrandAdapter;
import com.android.shooshoo.adapter.ProfileFeedsAdapter;


public class ProfileFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //Here we declare layouts
    RecyclerView brandRecyclerView;
    RecyclerView feedsRecyclerView;
    ProfileBrandAdapter profileBrandAdapter;
    ProfileFeedsAdapter profileFeedsAdapter;
    LinearLayout show_hide;
    ImageView toggleBtn;
    ImageView support_now;
    int[] images=new int[]{R.drawable.food_context1,R.drawable.food_context2,R.drawable.food_context3,R.drawable.food_context4,R.drawable.food_context5};
    int[] brandimgs=new int[]{R.drawable.adidas,R.drawable.benz,R.drawable.dmart,R.drawable.flipkar,
            R.drawable.hm,R.drawable.nike,R.drawable.pepsi,R.drawable.puma,R.drawable.vokes_wagon,R.drawable.wallmart,R.drawable.puma};

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
       /* Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     /*   if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        brandRecyclerView=view.findViewById(R.id.rv_list_brand);
        feedsRecyclerView=view.findViewById(R.id.rv_list_feed);
        show_hide=view.findViewById(R.id.show_hide_lay);
        toggleBtn=view.findViewById(R.id.toggle_btn);

        toggleBtn.setOnClickListener(this);
        support_now=view.findViewById(R.id.support_now);
        support_now.setOnClickListener(this);
        brandRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        feedsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        profileBrandAdapter=new ProfileBrandAdapter(brandimgs);
        profileFeedsAdapter=new ProfileFeedsAdapter(images);
        brandRecyclerView.setAdapter(profileBrandAdapter);
        feedsRecyclerView.setAdapter(profileFeedsAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toggle_btn:
                if(show_hide.getVisibility()==View.VISIBLE){
                    show_hide.setVisibility(View.GONE);
                    toggleBtn.setImageResource(R.drawable.plus_collaps);
                }else {
                    show_hide.setVisibility(View.VISIBLE);
                    toggleBtn.setImageResource(R.drawable.minus_add);
                }
                break;
            case R.id.support_now:
                Intent intent=new Intent(getActivity(), SupportNowActivity.class);
                startActivity(intent);
                break;
        }

    }
}
