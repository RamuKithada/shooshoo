package com.android.shooshoo.activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
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
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.views.LoginView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginView {
@BindView(R.id.edt_user_name)
    EditText edt_user_name;

    @BindView(R.id.edt_pws)
    EditText edt_pws;

    @BindView(R.id.iv_red_eye)
    ImageView iv_red_eye;

    @BindView(R.id.user_name_line)
    View user_name_line;

    @BindView(R.id.pws_line)
    View pws_line;

    @BindView(R.id.btn_forget_pws)
    TextView btn_forget_pws;

    @BindView(R.id.sign_up_layout)
    LinearLayout sign_up_layout;

    @BindView(R.id.btn_login)
    Button btn_login;

    @BindView(R.id.user_name_layout)
    LinearLayout user_name_layout;

    @BindView(R.id.pws_layout)
    LinearLayout pws_layout;
    @BindView(R.id.iv_user_icon)
    ImageView iv_user_icon;

    @BindView(R.id.iv_pws_icon)
    ImageView iv_pws_icon;



    String active="#FFFFFF",inactive="#CCCCCC";

    LoginPresenter loginPresenter;
    ConnectionDetector connectionDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter=new LoginPresenter();
        loginPresenter.attachView(this);
        connectionDetector=new ConnectionDetector(this);
        edt_user_name.setOnFocusChangeListener( new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus)
                    {
                        user_name_line.setBackgroundColor(Color.parseColor(active));
                        iv_user_icon.setImageResource(R.drawable.username_active);
                    }
                    else
                    {
                        user_name_line.setBackgroundColor(Color.parseColor(inactive));
                        iv_user_icon.setImageResource(R.drawable.username_normal);
                    }

            }
        });
        edt_pws.setOnFocusChangeListener( new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    pws_line.setBackgroundColor(Color.parseColor(active));
                    iv_pws_icon.setImageResource(R.drawable.password_active);
                }
                else
                {
                    pws_line.setBackgroundColor(Color.parseColor(inactive));
                    iv_pws_icon.setImageResource(R.drawable.password_normal);
                }

            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput())
                    login();

            }
        });
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
        sign_up_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(signupIntent);
                finish();

            }
        });
        btn_forget_pws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent forgetPwsIntent=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(forgetPwsIntent);

            }
        });


    }

    private void login() {
        if(connectionDetector.isConnectingToInternet())
           loginPresenter.loginUser(edt_user_name.getText().toString(),edt_pws.getText().toString());
        else showMessage("Please Check internet connection !");

    }

    private boolean validateInput() {
          String name=edt_user_name.getText().toString();
          String pws=edt_pws.getText().toString();
          if(!ApiUrls.validateString(name)){
              edt_user_name.requestFocus();
              edt_user_name.setError("Enter User name");
              return false;
          }
        if(!ApiUrls.validateString(pws)){
            edt_pws.requestFocus();
            edt_pws.setError("Enter Password");
            return false;
        }else if(pws.length()<6){
            edt_pws.setError("Password is too short" );
            return false;
        }


        return true;
    }

    @Override
    public void loginDetails(LoginSuccess loginSuccess) {
        if(loginSuccess.getStatus()==1){
            try {
                userSession.setUserId(loginSuccess.getUserInfo().getUserId());
                userSession.login();
                Intent signupIntent=new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(signupIntent);
                showMessage(loginSuccess.getMessage());
                finish();
            }catch (Exception e){
                e.printStackTrace();
            }

        }else
            showMessage(loginSuccess.getMessage());

    }
}
