package com.android.shooshoo.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.android.shooshoo.R;
import com.android.shooshoo.tutorials.HomeHelpFragment;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.BaseView;
/**
 * Base activity is activity for all the activities used in this application
 * this used for code reusability
 * {@link #dialog} is to show whenever we call service to load data fro activities.
 *
 */
public class BaseActivity extends AppCompatActivity implements BaseView {
    public Dialog dialog;
    public UserSession userSession;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.loading);
        dialog.setCancelable(false);
        userSession=new UserSession(this);

    }

    @Override
    public Context getContext() {
        return this;
    }

    /**
     *
     * @param stringId is used to show the message to user by using string resources
     */
    @Override
    public void showMessage(int stringId) {
        showMessage(getString(stringId));

    }


    /**
     *
     * @param message is used to show the message to user
     */
    @Override
    public void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }

    /**
     *
     * @param show is used to whether we need to show the dialog  user or not
     */

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
    HomeHelpFragment homeHelpFragment;
    public void showHelp(int layout,int inner) {
        if(homeHelpFragment==null){
            homeHelpFragment=HomeHelpFragment.newInstance(layout,inner);
        }

        if(!homeHelpFragment.isAdded())
        {
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.show,R.anim.hide);
            fragmentTransaction.add(R.id.help_container,homeHelpFragment,"help");
            fragmentTransaction.commit();

        }


    }
    public void hideHelp(){
        if(homeHelpFragment!=null){
            if(homeHelpFragment.isAdded()) {
                FragmentTransaction  fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.show,R.anim.hide);
                fragmentTransaction.remove(homeHelpFragment);
                fragmentTransaction.commit();
                homeHelpFragment=null;
            }
        }

    }
}
