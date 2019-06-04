package com.android.shooshoo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.LoginSuccess;
import com.android.shooshoo.presenters.LoginPresenter;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.views.LoginView;

public class ForgetPasswordActivity extends BaseActivity implements LoginView {
LoginPresenter loginPresenter;
EditText email;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        loginPresenter=new LoginPresenter();
        loginPresenter.attachView(this);
    ImageView imageView=findViewById(R.id.iv_back);
    imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
        Button btn_forget_pws=findViewById(R.id.btn_forget_pws);
        email=findViewById(R.id.edt_user_email);
        btn_forget_pws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailid=email.getText().toString();
                if(ApiUrls.validateString(emailid))
                {
                    if(Patterns.EMAIL_ADDRESS.matcher(emailid).matches())
                    loginPresenter.forgetPassword(email.getText().toString());
                    else {
                        email.requestFocus();
                        email.setError("Enter valid email");
                    }
                }
                else{
                    email.requestFocus();
                    email.setError("Enter email here");
                }

            }
        });
    }

    @Override
    public void loginDetails(LoginSuccess loginSuccess) {
        showMessage(loginSuccess.getMessage());
        if(loginSuccess.getStatus()==1){
            finish();

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.detachView();
    }
}
