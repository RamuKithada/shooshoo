package com.android.shooshoo.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.shooshoo.R;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.BaseView;

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

    @Override
    public void showMessage(int stringId) {
        showMessage(getString(stringId));

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }

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
}
