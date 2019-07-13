package com.android.shooshoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.LoginSuccess;
import com.android.shooshoo.presenters.UpdateUserInfoPresenter;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.adapter.CategoryChooseAdapter;
import com.android.shooshoo.models.CatResult;
import com.android.shooshoo.views.UpdateUserInfoView;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**{@link CategoryChooseActivity} is used to select Categories
 *  the user select at least 3 Categories from the list then
 *  he can complete the registration step 3 otherwise he can skip this process.
 */

public class CategoryChooseActivity extends BaseActivity implements UpdateUserInfoView,View.OnClickListener{


    @BindView(R.id.list_categories)
    RecyclerView recyclerView;

    @BindView(R.id.next_lay)
    AppCompatTextView next_lay;

    /***
     * button1,button2,button3,button3,button4 are used to show step of the registration. and tv_skip,iv_back are to represent back and skip buttons of layout.
     */
    @BindView(R.id.button1)
    Button button1;

    @BindView(R.id.button2)
    Button button2;

    @BindView(R.id.button3)
    Button button3;



    @BindView(R.id.tv_title)
    TextView tv_title;



    @BindView(R.id.iv_back)
    ImageView iv_back;

    CategoryChooseAdapter chooseAdapter;

    ConnectionDetector connectionDetector;
    UpdateUserInfoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_choose);
        ButterKnife.bind(this);
        connectionDetector=new ConnectionDetector(this);
        chooseAdapter=new CategoryChooseAdapter(this);
        presenter=new UpdateUserInfoPresenter();
        presenter.attachView(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(chooseAdapter);
        tv_title.setText("Choose Categories");
        next_lay.setOnClickListener(this);
        setState();
        iv_back.setOnClickListener(this);

        if(connectionDetector.isConnectingToInternet()){
            loadCategory(0);

        }else showMessage("Please Check Internet connection ");


    }

    private void loadCategory(int offset) {
        showProgressIndicator(true);
        RetrofitApis.Factory.create(this).getCategories("30",""+offset).enqueue(new Callback<CatResult>() {
            @Override
            public void onResponse(Call<CatResult> call, Response<CatResult> response) {
                showProgressIndicator(false);
                if(response.isSuccessful()){
                    CatResult catResult=response.body();
                    chooseAdapter.setCategories(catResult.getCategories());
                }
            }

            @Override
            public void onFailure(Call<CatResult> call, Throwable t) {
                showProgressIndicator(false);

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_lay:
                if(chooseAdapter.selectedSize()>=3) {
                    if (connectionDetector.isConnectingToInternet()) {
                        presenter.updateUserCat(userSession.getUserId(), chooseAdapter.getCats());
                        userSession.setCats(chooseAdapter.getCats());
                    } else showMessage("please check internet connection");
                }else showMessage("Please select at least 3 categories ");
                break;

            case R.id.iv_back:
                finish();
                break;

        }

    }
    //To set the step of the registration process
    private void setState() {
        button1.setBackgroundResource(R.drawable.unselected);
        button2.setBackgroundResource(R.drawable.selected);
        button3.setBackgroundResource(R.drawable.unselected);

    }

    @Override
    public void onUpdateUserInfo(ResponseBody responseBody) {
        JSONObject object=null;
        try {
            String result=responseBody.string();
            object=new JSONObject(result);
            Log.e("response",result);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(object!=null) {
            if (object.optString("status").equalsIgnoreCase("1")) {
                Intent intent = new Intent(CategoryChooseActivity.this, BrandChooseActivity.class);
                userSession.setCats(chooseAdapter.getCats());
                Log.e("cats", "" + chooseAdapter.getCats());
                startActivity(intent);
            }
            showMessage(object.optString("message"));
        }
    }

    @Override
    public void loginDetails(LoginSuccess loginSuccess) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
