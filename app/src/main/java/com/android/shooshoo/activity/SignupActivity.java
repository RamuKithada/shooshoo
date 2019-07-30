package com.android.shooshoo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.models.LoginSuccess;
import com.android.shooshoo.presenters.LoginPresenter;
import com.android.shooshoo.views.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link SignupActivity} is used to sign up the user into app.
 *  {@link LoginPresenter }
 * edt_user_email  is the input field of user name
 * edt_pws  is the input field of user password
 */
public class SignupActivity extends BaseActivity implements LoginView,View.OnClickListener{
    @BindView(R.id.edt_user_name)
    EditText edt_user_name;

    @BindView(R.id.edt_user_email)
    EditText edt_user_email;

    @BindView(R.id.edt_pws)
    EditText edt_pws;

    @BindView(R.id.btn_register)
    AppCompatTextView btn_register;

    @BindView(R.id.sign_in_layout)
    LinearLayout sign_in_layout;

    @BindView(R.id.iv_red_eye)
    ImageView iv_red_eye;

    @BindView(R.id.iv_pws_icon)
            ImageView iv_pws_icon;
    @BindView(R.id.iv_user_name_icon)
    ImageView iv_user_name_icon;

    @BindView(R.id.iv_email_icon)
    ImageView iv_email_icon;



    LoginPresenter loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
//        setFoucusChange(edt_user_name,R.id.user_name_line,iv_user_name_icon,new int[]{R.drawable.username_active,R.drawable.username_normal});
//        setFoucusChange(edt_user_email,R.id.user_mail_line,iv_email_icon,new int[]{R.drawable.email_active,R.drawable.email_normal});
//        setFoucusChange(edt_pws,R.id.pws_line,iv_pws_icon,new int[]{R.drawable.password_active,R.drawable.password_normal});
        btn_register.setOnClickListener(this);
        sign_in_layout.setOnClickListener(this);
        loginPresenter=new LoginPresenter();
        loginPresenter.attachView(this);
        iv_red_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                if (edt_pws.getTransformationMethod().getClass().getSimpleName() .equals("PasswordTransformationMethod")) {
                    edt_pws.setTransformationMethod(new SingleLineTransformationMethod());
                    iv_red_eye.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);
                }
                else {
                    edt_pws.setTransformationMethod(new PasswordTransformationMethod());
                    iv_red_eye.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                }

                edt_pws.setSelection(edt_pws.getText().length());
            }
        });

    }
    String active="#ffffff",inactive="#cccccc";
    public void setFoucusChange(EditText editText, int id, final ImageView imageView, final int[] res){
        final View view=findViewById(id);
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
            case R.id.btn_register:
                if(validateInput())
                    signup();

                break;
            case R.id.sign_in_layout:
                Intent loginIntent=new Intent(this,LoginActivity.class);
                startActivity(loginIntent);
                break;
        }
    }

    private void signup() {
        loginPresenter.signupUser(edt_user_name.getText().toString(),edt_pws.getText().toString(),edt_user_email.getText().toString());

    }

    private boolean validateInput() {
        String username=edt_user_name.getText().toString();
        String email=edt_user_email.getText().toString();
        String pws=edt_pws.getText().toString();

        if(!ApiUrls.validateString(username))
        {

            edt_user_name.setError("Enter User Name");
            edt_user_name.requestFocus();
            return false;
        }
        if(edt_user_name.getText().toString().length()<6){
            edt_user_name.setError("User Name is minimum 6 letters");
            edt_user_name.requestFocus();
            return false;

        }

        if(edt_user_name.getText().toString().length()>30){
            edt_user_name.setError("User Name is maximum 30 letters");
            edt_user_name.requestFocus();
            return false;
        }

        if(!ApiUrls.validateString(email))
        {

            edt_user_email.setError("Please enter Email");
            edt_user_email.requestFocus();
            return false;
        }

        if(edt_user_email.getText().toString().length()<6){
            edt_user_email.setError("Email  is minimum 6 letters");
            edt_user_email.requestFocus();
            return false;

        }


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {

            edt_user_email.setError("Enter valid Email");
            edt_user_email.requestFocus();
            return false;
        }
        if(!ApiUrls.validateString(pws))
        {

            edt_pws.setError("Enter Password");
            edt_pws.requestFocus();
            return false;
        }
        if(pws.length()<6||pws.length()>12)
        {
            edt_pws.setError("Password is minimum 6 and maximum 12 Characters");
            edt_pws.requestFocus();
            return false;
        }


        return true;
    }

    @Override
    public void loginDetails(LoginSuccess loginSuccess) {

        if(loginSuccess.getStatus()==1) {
            try {
                userSession.setUserId(loginSuccess.getUserInfo().getUserId());
                userSession.login();
                Intent intent = new Intent(this, ProfileFillingFormActivity.class);
                startActivity(intent);
                showMessage(loginSuccess.getMessage());
                finish();
            }catch (Exception e){
                e.printStackTrace();
            }

        }else if( loginSuccess.getMessage()!=null){
            showMessage(loginSuccess.getMessage());
        }
    }
}
