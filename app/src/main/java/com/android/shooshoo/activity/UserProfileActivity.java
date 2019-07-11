package com.android.shooshoo.activity;

import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.FeedProfileGridFragmentPagerAdapter;
import com.android.shooshoo.adapter.ProfileBrandAdapter;
import com.android.shooshoo.models.Brand;
import com.android.shooshoo.models.UserInfo;
import com.android.shooshoo.presenters.ProfilePresenter;
import com.android.shooshoo.utils.BottomNavigationBehavior;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.ProfileView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.android.shooshoo.utils.ApiUrls.PROFILE_IMAGE_URL;

public class UserProfileActivity extends BaseActivity implements ProfileView,View.OnClickListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rv_list_brand)
    RecyclerView brandRecyclerView;



    @BindView(R.id.bottom_view)
    RelativeLayout bottomNavigation;

    ProfileBrandAdapter profileBrandAdapter;
    //    ProfileFeedsAdapter profileFeedsAdapter;
    @BindView(R.id.profile_name)
    TextView profile_name;

    @BindView(R.id.profile_description)
    TextView profile_quotes;

    @BindView(R.id.iv_profile_pic)
    CircleImageView iv_profile_pic;

    @BindView(R.id.new_tab)
    LinearLayout new_tab;

    @BindView(R.id.best_tab)
    LinearLayout best_tab;
     @BindView(R.id.view_pager)
     ViewPager viewPager;

    ConnectionDetector connectionDetector;
    UserSession userSession;
    ProfilePresenter presenter;
    List<Brand> brandList=new ArrayList<Brand>();
    String userId=null;


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





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        iv_back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
        connectionDetector=new ConnectionDetector(this);
        userSession=new UserSession(this);
        userId=getIntent().getStringExtra("userId");
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        brandRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        profileBrandAdapter=new ProfileBrandAdapter(getContext(),brandList);
        presenter=new ProfilePresenter();
        presenter.attachView(this);
        best_tab.setOnClickListener(tabsOnClickListener);
        new_tab.setOnClickListener(tabsOnClickListener);
        FeedProfileGridFragmentPagerAdapter feedViewPagerAdapter=new FeedProfileGridFragmentPagerAdapter(this,getSupportFragmentManager(),new String[]{"new","popular"},userId);
        viewPager.setAdapter(feedViewPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        if(connectionDetector.isConnectingToInternet()){
            presenter.loadProfile(userId);
        }



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
