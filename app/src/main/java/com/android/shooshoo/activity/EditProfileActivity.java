package com.android.shooshoo.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.registration.ChangePasswordActivity;
import com.android.shooshoo.adapter.ProfileViewPagerAdapter;
import com.android.shooshoo.fragment.NotificationSettingFragment;
import com.android.shooshoo.fragment.ProfileSettingFragment;
import com.android.shooshoo.fragment.ProfileVisibilityFragment;
import com.android.shooshoo.utils.FirebaseService;
import com.android.shooshoo.utils.FragmentListDialogListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener, FragmentListDialogListener {
    /**
     * {@link EditProfileActivity} is called when the user clicked on profile edit button on the profile screen
     *mDrawerLayout is side slide menu
     */
    DrawerLayout mDrawerLayout;
    ImageView iv_chat;
    ImageView iv_back;
    @BindView(R.id.navigation_home)
    LinearLayout navigation_home;
    @BindView(R.id.navigation_challengers)
    LinearLayout navigation_challengers;
    @BindView(R.id.navigation_feed)
    LinearLayout navigation_feed;
    @BindView(R.id.navigation_winners)
    LinearLayout navigation_winners;
    @BindView(R.id.navigation_radar)
    LinearLayout navigation_radar;

    @BindView(R.id.profile_item)
            TextView profile_item;
    @BindView(R.id.visibility_item)
            TextView visibility_item;
    @BindView(R.id.notification_item)
            TextView notification_item;
    @BindView(R.id.change_pws_item)
            TextView change_pws_item;
    @BindView(R.id.legal_item)
            TextView legal_item;
    @BindView(R.id.license_item)
            TextView license_item;
    @BindView(R.id.user_agreement_item)
            TextView user_agreement_item;
    @BindView(R.id.terms_of_use_item)
            TextView terms_of_use_item;
    @BindView(R.id.logout_item)
            TextView logout_item;

    @BindView(R.id.tv_title)
    AppCompatTextView tv_title;


//    TabLayout tabLayout;
//    ViewPager viewPager;

    Fragment currentFragment;
    ProfileViewPagerAdapter profileViewPagerAdapter;
    private View.OnClickListener bottomNavigationOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(EditProfileActivity.this,HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            switch (v.getId()) {
                case R.id.navigation_home:
                    intent.putExtra("icon",0);
                    break;
                case R.id.navigation_challengers:
                    intent.putExtra("icon",1);
                    break;
                case R.id.navigation_feed:
                    intent.putExtra("icon",2);
                    break;
                case R.id.navigation_winners:
                    intent.putExtra("icon",3);
                    break;
                case R.id.navigation_radar:
                    intent.putExtra("icon",4);
                    break;
            }
            startActivity(intent);
            finish();

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        iv_chat=(ImageView)findViewById(R.id.iv_chat);
        iv_back=(ImageView)findViewById(R.id.iv_back);


       /*  viewPager=findViewById(R.id.view_pager);
        viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });
         profileViewPagerAdapter=new ProfileViewPagerAdapter(this,getSupportFragmentManager(),new String[]{"Profile Settings","Profile Visibility"});
        viewPager.setAdapter(profileViewPagerAdapter);*/

//        tabLayout.setupWithViewPager(viewPager);
        iv_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.END);
                }
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        navigation_home.setOnClickListener(bottomNavigationOnClickListener);
        navigation_challengers.setOnClickListener(bottomNavigationOnClickListener);
        navigation_feed.setOnClickListener(bottomNavigationOnClickListener);
        navigation_winners.setOnClickListener(bottomNavigationOnClickListener);
        navigation_radar.setOnClickListener(bottomNavigationOnClickListener);
        profile_item.setOnClickListener(this);
    visibility_item.setOnClickListener(this);
    legal_item.setOnClickListener(this);
    license_item.setOnClickListener(this);
    user_agreement_item.setOnClickListener(this);
    terms_of_use_item.setOnClickListener(this);
    logout_item.setOnClickListener(this);
    change_pws_item.setOnClickListener(this);
    notification_item.setOnClickListener(this);
        profileSettingFragment=ProfileSettingFragment.newInstance("hai","sai");
        getSupportFragmentManager().beginTransaction().add(R.id.profile_fragment_container,profileSettingFragment).commit();
    }


    @Override
    public void onEditView(int view, int pos) {
       if(profileSettingFragment!=null)
               profileSettingFragment.onEditView(view,pos);
    }
    ProfileSettingFragment profileSettingFragment;
    ProfileVisibilityFragment visibilityFragment;
    NotificationSettingFragment notificationSettingFragment;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profile_item:
                tv_title.setText("Profile settings");
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                 if(profileSettingFragment==null)
                     profileSettingFragment=ProfileSettingFragment.newInstance("hai","sai");

                 if(profileSettingFragment.isAdded())
                     ft.show(profileSettingFragment);
                 else
                     ft.add(R.id.profile_fragment_container,profileSettingFragment);
                if(visibilityFragment!=null)
                if(visibilityFragment.isAdded()){
                    ft.hide(visibilityFragment);
                }
                if(notificationSettingFragment!=null)
                if(notificationSettingFragment.isAdded()){
                    ft.hide(notificationSettingFragment);
                }

                 ft.commit();

                break;

            case R.id.visibility_item:
                tv_title.setText("Visibility");
                FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                if(visibilityFragment==null)
                    visibilityFragment=ProfileVisibilityFragment.newInstance("hai","sai");

                if(visibilityFragment.isAdded())
                {
                    ft1.show(visibilityFragment);
                }
                else
                     ft1.add(R.id.profile_fragment_container,visibilityFragment);

                if(profileSettingFragment!=null)
                    if(profileSettingFragment.isAdded()){
                        ft1.hide(profileSettingFragment);
                    }
                if(notificationSettingFragment!=null)
                    if(notificationSettingFragment.isAdded()){
                        ft1.hide(notificationSettingFragment);
                    }
                ft1.commit();

                break;

            case R.id.legal_item:
                break;
            case R.id.license_item:
                break;
            case R.id.user_agreement_item:
                break;
            case R.id.logout_item:
                userSession.logout();

            new SignOutTask().execute();


                Intent intent=new Intent(this,SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finishAffinity();
                break;
            case R.id.change_pws_item:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            case R.id.terms_of_use_item:

                break;
                case R.id.notification_item:
                    tv_title.setText("notifications");
                    FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                    if(notificationSettingFragment==null)
                        notificationSettingFragment= NotificationSettingFragment.newInstance("hai","sai");

                    if(notificationSettingFragment.isAdded())
                    {
                        ft2.show(notificationSettingFragment);
                    }
                    else
                        ft2.add(R.id.profile_fragment_container,notificationSettingFragment);

                    if(visibilityFragment!=null)
                        if(visibilityFragment.isAdded()){
                            ft2.hide(visibilityFragment);
                        }

                    if(profileSettingFragment!=null)
                        if(profileSettingFragment.isAdded()){
                            ft2.hide(profileSettingFragment);
                        }

                    ft2.commit();

                break;


        }
        mDrawerLayout.closeDrawer(GravityCompat.END);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    class SignOutTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                FirebaseInstanceId.getInstance().deleteInstanceId();
                } catch (IOException e) {
            e.printStackTrace();
        }

            return null;
        }
    }

}
