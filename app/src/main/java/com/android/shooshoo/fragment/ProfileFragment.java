package com.android.shooshoo.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.shooshoo.adapter.FeedProfileGridFragmentPagerAdapter;
import com.android.shooshoo.adapter.ImageListAdapter;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.models.ImagesModel;
import com.android.shooshoo.R;
import com.android.shooshoo.adapter.ProfileBrandAdapter;
import com.android.shooshoo.models.Brand;
import com.android.shooshoo.models.UserInfo;
import com.android.shooshoo.presenters.ProfilePresenter;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.BaseView;
import com.android.shooshoo.views.ProfileView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.android.shooshoo.utils.ApiUrls.PROFILE_IMAGE_URL;

/**
 *
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 * this is Profile screen to show settings options
 */

public class ProfileFragment extends Fragment implements ProfileView,View.OnClickListener, ViewPager.OnPageChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //Here we declare layouts
    @BindView(R.id.rv_list_brand)
    RecyclerView brandRecyclerView;


    private ImageListAdapter imageListAdapter;
    private ArrayList<ImagesModel> imagesModelArrayList=new ArrayList<>();
    ProfileBrandAdapter profileBrandAdapter;

    ConnectionDetector connectionDetector;
    ProfilePresenter presenter;
    BaseView baseView;
    List<Company> brandList=new ArrayList<Company>();

    @BindView(R.id.profile_name)
    TextView profile_name;

    @BindView(R.id.followers_count)
    TextView followers_count;

    @BindView(R.id.iv_profile_pic)
    CircleImageView iv_profile_pic;

    @BindView(R.id.new_tab)
    LinearLayout new_tab;

    @BindView(R.id.best_tab)
    LinearLayout best_tab;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.edit_description)
     TextView edit_description;

    @BindView(R.id.profile_description)
    TextView profile_description;

    @BindView(R.id.et_description)
    EditText et_description;



    UserSession userSession;



    View.OnClickListener tabsOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.new_tab:
                    viewPager.setCurrentItem(0,true);
                    break;
                case R.id.best_tab:
                    viewPager.setCurrentItem(1,true);
                    break;
            }
        }
    };

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
        ButterKnife.bind(this,view);
        brandRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        profileBrandAdapter=new ProfileBrandAdapter(getContext(),brandList);
        brandRecyclerView.setAdapter(profileBrandAdapter);
        presenter=new ProfilePresenter();
        presenter.attachView(this);
        FeedProfileGridFragmentPagerAdapter feedViewPagerAdapter=new FeedProfileGridFragmentPagerAdapter(getContext(),getChildFragmentManager(),new String[]{"new","popular"},userSession.getUserId());
        viewPager.setAdapter(feedViewPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        best_tab.setOnClickListener(tabsOnClickListener);
        new_tab.setOnClickListener(tabsOnClickListener);
        if(connectionDetector.isConnectingToInternet()){
            presenter.loadProfile(mParam1);
        }
        edit_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView view=(TextView)v;
                if(view.getText().toString().equalsIgnoreCase("edit")){
                    view.setText("Save");
                    et_description.setVisibility(View.VISIBLE);
                    et_description.setText(profile_description.getText().toString());
                    profile_description.setVisibility(View.GONE);

                }else if(view.getText().toString().equalsIgnoreCase("save")){
                    view.setText("Edit");

                    profile_description.setVisibility(View.VISIBLE);
                    profile_description.setText(et_description.getText().toString());
                    et_description.setVisibility(View.GONE);
                    presenter.saveDescription(userSession.getUserId(),et_description.getText().toString());
                }

            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        connectionDetector=new ConnectionDetector(context);
        userSession=new UserSession(context);
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
            followers_count.setText(userInfo.getFollowers());
            profile_description.setText(userInfo.getDescription());//ApiUrls.getAge(userInfo.getDob()));
            Picasso.with(getContext()).load(PROFILE_IMAGE_URL+userInfo.getImage()).error(R.drawable.profile_1).into(iv_profile_pic);
        }
    }

    @Override
    public void onBrands(List<Company> brands) {
        if(brands!=null)
        {
            brandList.addAll(brands);
            profileBrandAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i){
            case 0:
                ((TextView) new_tab.getChildAt(0)).setTextColor(Color.parseColor("#F31F68"));
                new_tab.getChildAt(1).setBackgroundColor(Color.parseColor("#F31F68"));
                ((TextView) best_tab.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
                best_tab.getChildAt(1).setBackgroundColor(Color.parseColor("#85868A"));
                break;
            case 1:
                ((TextView) best_tab.getChildAt(0)).setTextColor(Color.parseColor("#F31F68"));
                best_tab.getChildAt(1).setBackgroundColor(Color.parseColor("#F31F68"));
                ((TextView) new_tab.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
                new_tab.getChildAt(1).setBackgroundColor(Color.parseColor("#85868A"));
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

}
