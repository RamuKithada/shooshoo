package com.android.shooshoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.shooshoo.R;
import com.android.shooshoo.presenters.UpdateUserInfoPresenter;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.adapter.BrandChooseAdapter;
import com.android.shooshoo.models.BrandsResult;
import com.android.shooshoo.views.UpdateUserInfoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandChooseActivity extends BaseActivity implements UpdateUserInfoView, View.OnClickListener {
    @BindView(R.id.list_categories)
    RecyclerView recyclerView;

    @BindView(R.id.next_lay)
    RelativeLayout next_lay;


    @BindView(R.id.button1)
    Button button1;

    @BindView(R.id.button2)
    Button button2;

    @BindView(R.id.button3)
    Button button3;

    @BindView(R.id.button4)
    Button button4;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_skip)
    TextView tv_skip;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    BrandChooseAdapter chooseAdapter;
    ConnectionDetector connectionDetector;
    UpdateUserInfoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_brand);
        ButterKnife.bind(this);
        connectionDetector=new ConnectionDetector(this);
        chooseAdapter=new BrandChooseAdapter(this);
        presenter=new UpdateUserInfoPresenter();
        presenter.attachView(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(chooseAdapter);
        next_lay.setOnClickListener(this);
        tv_title.setText("Choose Brands");
        setState();
        next_lay.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_skip.setOnClickListener(this);
       String cats= userSession.getCats();
        loadData(cats);


    }

    private void loadData(String ids) {
        showProgressIndicator(true);
        RetrofitApis.Factory.create(this).getBrands(ids).enqueue(new Callback<BrandsResult>() {
            @Override
            public void onResponse(Call<BrandsResult> call, Response<BrandsResult> response) {
                showProgressIndicator(false);
                if(response.isSuccessful()){
                    BrandsResult brandsResult=response.body();
                    chooseAdapter.setBrands(brandsResult.getBrands());

                }
            }

            @Override
            public void onFailure(Call<BrandsResult> call, Throwable t) {
                showProgressIndicator(false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_lay:
                if(chooseAdapter.getItemCount()>=3) {
                    if (chooseAdapter.selectedSize() >= 3) {
                        if(connectionDetector.isConnectingToInternet())
                        {
                          presenter.updateUserBrand(userSession.getUserId(),chooseAdapter.getBrandIds());
                        }
                        else showMessage("please check internet connection");
                    } else {
                        showMessage("Please select at least 3 Brands");
                    }
                }

                break;
            case R.id.tv_skip:
                Intent homeIntent=new Intent(this,HomeActivity.class);
                startActivity(homeIntent);
                break;
            case R.id.iv_back:
                finish();
                break;

        }

    }


    private void setState() {
        button1.setBackgroundResource(R.drawable.unselected);
        button2.setBackgroundResource(R.drawable.unselected);
        button3.setBackgroundResource(R.drawable.unselected);
        button4.setBackgroundResource(R.drawable. selected);
    }

    @Override
    public void onUpdateUserInfo(ResponseBody responseBody) {
        try {
            Log.e("response",responseBody.string());
            Intent homeIntent=new Intent(this,HomeActivity.class);
            startActivity(homeIntent);
            finishAffinity();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
