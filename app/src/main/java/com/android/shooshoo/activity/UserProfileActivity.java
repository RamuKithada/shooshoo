package com.android.shooshoo.activity;

import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.FeedProfileGridFragmentPagerAdapter;
import com.android.shooshoo.adapter.ProfileBrandAdapter;
import com.android.shooshoo.models.Brand;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.models.UserInfo;
import com.android.shooshoo.presenters.ProfilePresenter;
import com.android.shooshoo.utils.BottomNavigationBehavior;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.ProfileView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.followers_count)
    TextView followers_count;
    @BindView(R.id.tv_follow)
    TextView tv_follow;

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
    List<Company> brandList=new ArrayList<Company>();
    String userId=null,follow;


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
        follow=getIntent().getStringExtra("follow");
        if(follow.equalsIgnoreCase("1")){
            tv_follow.setText("Follow");
        }else {
            tv_follow.setText("Unfollow");
        }
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        brandRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        profileBrandAdapter=new ProfileBrandAdapter(getContext(),brandList);
        brandRecyclerView.setAdapter(profileBrandAdapter);
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

        tv_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followUser();

            }
        });



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
            profile_quotes.setText(userInfo.getDescription());
            followers_count.setText(userInfo.getFollowers());
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
    public void followUser(){

     showProgressIndicator(true);
        RetrofitApis  retrofitApis=RetrofitApis.Factory.create(this);
              retrofitApis.followUser(userSession.getUserId(),userId).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                  showProgressIndicator(false);

                    if(response.isSuccessful()){
                        try {
                            String res=  response.body().string();
                            JSONObject object=new JSONObject(res);
                            String msg=object.optString("message");
                            int status=object.optInt("status");
                            if(status==1){
                                toogleFollow();
                            }
                            showMessage(msg);

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
                   showProgressIndicator(false);
                      showMessage(t.getMessage());


                }
            });


        }

    private void toogleFollow() {
        if(tv_follow.getText().toString().equalsIgnoreCase("follow")){
            tv_follow.setText("Unfollow");
        }else {
            tv_follow.setText("Follow");
        }

    }


}
