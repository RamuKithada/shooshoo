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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.SupportNowActivity;
import com.android.shooshoo.adapter.ProfileBrandAdapter;
import com.android.shooshoo.adapter.ProfileFeedsAdapter;
import com.android.shooshoo.models.Brand;
import com.android.shooshoo.models.Post;
import com.android.shooshoo.models.UserBankDetails;
import com.android.shooshoo.models.UserInfo;
import com.android.shooshoo.presenters.ProfilePresenter;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.BaseView;
import com.android.shooshoo.views.ProfileView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.android.shooshoo.utils.ApiUrls.PROFILE_IMAGE_URL;

/**
 *
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 * this is Profile screen to show settings options
 */

public class ProfileFragment extends Fragment implements ProfileView,View.OnClickListener{
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
    TextView profile_name,profile_quotes;
    //,profile_status,profile_age;
//    LinearLayout show_hide;
//    ImageView toggleBtn;
//    ImageView support_now;
    CircleImageView iv_profile_pic;
    ConnectionDetector connectionDetector;
    ProfilePresenter presenter;
    BaseView baseView;
    List<Brand> brandList=new ArrayList<Brand>();
    List<Post> posts=new ArrayList<Post>();
//    int[] images=new int[]{R.drawable.food_context1,R.drawable.food_context2,R.drawable.food_context3,R.drawable.food_context4,R.drawable.food_context5};
//    int[] brandimgs=new int[]{R.drawable.adidas,R.drawable.benz,R.drawable.dmart,R.drawable.flipkar,
//            R.drawable.hm,R.drawable.nike,R.drawable.pepsi,R.drawable.puma,R.drawable.vokes_wagon,R.drawable.wallmart,R.drawable.puma};

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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        brandRecyclerView=view.findViewById(R.id.rv_list_brand);
        feedsRecyclerView=view.findViewById(R.id.rv_list_feed);
//        show_hide=view.findViewById(R.id.show_hide_lay);
//        toggleBtn=view.findViewById(R.id.toggle_btn);
//        toggleBtn.setOnClickListener(this);
//        support_now=view.findViewById(R.id.support_now);
        iv_profile_pic=view.findViewById(R.id.iv_profile_pic);
        profile_name=view.findViewById(R.id.profile_name);
        profile_quotes=view.findViewById(R.id.profile_description);
//        profile_status=view.findViewById(R.id.profile_status);
//        profile_age=view.findViewById(R.id.profile_age);
//        support_now.setOnClickListener(this);
        brandRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        feedsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        profileBrandAdapter=new ProfileBrandAdapter(getContext(),brandList);
        profileFeedsAdapter=new ProfileFeedsAdapter(getContext(),posts);
        brandRecyclerView.setAdapter(profileBrandAdapter);
        feedsRecyclerView.setAdapter(profileFeedsAdapter);
        presenter=new ProfilePresenter();

        presenter.attachView(this);
        if(connectionDetector.isConnectingToInternet()){
            presenter.loadProfile(mParam1);
        }




    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        connectionDetector=new ConnectionDetector(context);
        if(context instanceof BaseView){
            baseView= (BaseView) context;
        }else throw new RuntimeException(context.toString()+" Must implement "+BaseView.class.getSimpleName());

    }

    @Override
    public void onDetach() {
        super.onDetach();
        baseView=null;
        presenter.detachView();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.support_now:
//                Intent intent=new Intent(getActivity(), SupportNowActivity.class);
////                startActivity(intent);
                break;
        }

    }

    @Override
    public void showMessage(int stringId) {
        if(baseView!=null)
            baseView.showMessage(stringId);

    }

    @Override
    public void showMessage(String message) {
        if(baseView!=null)
            baseView.showMessage(message);
    }

    @Override
    public void showProgressIndicator(Boolean show) {
        if(baseView!=null)
            baseView.showProgressIndicator(show);
    }
    UserInfo userInfo;
    @Override
    public void onProfileData(UserInfo userInfo) {
        if(userInfo!=null) {
            this.userInfo=userInfo;
            profile_name.setText(userInfo.getUserName());
            profile_quotes.setText(userInfo.getDob());//ApiUrls.getAge(userInfo.getDob()));
            Picasso.with(getContext()).load(PROFILE_IMAGE_URL+userInfo.getImage()).error(R.drawable.profile_1).into(iv_profile_pic);
        }
    }

    @Override
    public void onBrands(List<Brand> brands) {
        if(brands!=null)
        {
            brandList.addAll(brands);
            profileBrandAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onPosts(List<Post> posts) {
        if(posts!=null)
        {
            this.posts.addAll(posts);
            profileFeedsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBankDetails(UserBankDetails bankDetails) {

    }
}
