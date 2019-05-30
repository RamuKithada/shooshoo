package com.android.shooshoo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.shooshoo.R;
import com.android.shooshoo.utils.ApiUrls;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.edt_old_pws)
    EditText edt_old_pws;
    @BindView(R.id.edt_new_pws)
    EditText edt_new_pws;
    @BindView(R.id.edt_cnf_pws)
    EditText edt_cnf_pws;

    @BindView(R.id.btn_change)
    Button btn_change;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        btn_change.setOnClickListener(this);
        iv_back.setOnClickListener(this);

        setFoucusChange(edt_old_pws,R.id.old_pws_line,R.id.iv_old_pws_icon,new int[]{R.drawable.password_active,R.drawable.password_normal});
        setFoucusChange(edt_new_pws,R.id.new_pws_line,R.id.iv_new_pws_icon,new int[]{R.drawable.password_active,R.drawable.password_normal});
        setFoucusChange(edt_cnf_pws,R.id.cnf_pws_line,R.id.iv_cnf_pws_icon,new int[]{R.drawable.password_active,R.drawable.password_normal});
    }

    String active="#ffffff",inactive="#cccccc";
    public void setFoucusChange(EditText editText, int id, final int imageid, final int[] res){
        final View view=findViewById(id);
        final ImageView imageView=findViewById(imageid);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    view.setBackgroundColor(Color.parseColor(active));
                    imageView.setImageResource(res[0]);
                }
                else
                {
                    view.setBackgroundColor(Color.parseColor(inactive));
                    imageView.setImageResource(res[1]);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_change:
                if(validateInput())
                    changePawssord();

                break;

            case R.id.iv_back:
                finish();
                break;

        }
    }

    private void changePawssord() {
    }

    private boolean validateInput() {
        String oldpws=edt_old_pws.getText().toString();
        String newpws=edt_new_pws.getText().toString();
        String cnfpws=edt_cnf_pws.getText().toString();

        if(!ApiUrls.validateString(oldpws))
        {

            edt_old_pws.setError("Enter Old Password");
            edt_old_pws.requestFocus();
            return false;
        }
       

        if(oldpws.length()<6||oldpws.length()>12){
            edt_old_pws.setError("Password is minimum 6 and maximum 12 Characters");
            edt_old_pws.requestFocus();
            return false;
        }

        if(!ApiUrls.validateString(newpws))
        {

            edt_new_pws.setError("Enter New Password");
            edt_new_pws.requestFocus();
            return false;
        }

        if(newpws.length()<6||newpws.length()>12)
        {

            edt_new_pws.setError("Password is minimum 6 and maximum 12 Characters");
            edt_new_pws.requestFocus();
            return false;

        }



        if(!ApiUrls.validateString(cnfpws))
        {

            edt_cnf_pws.setError("Enter Conform Password");
            edt_cnf_pws.requestFocus();
            return false;
        }
        if(cnfpws.length()<6||cnfpws.length()>12)
        {
            edt_cnf_pws.setError("Password is minimum 6 and maximum 12 Characters");
            edt_cnf_pws.requestFocus();
            return false;
        }


        return true;
    }

}
