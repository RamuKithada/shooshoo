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
import android.widget.TextView;
import com.android.shooshoo.R;
import com.android.shooshoo.models.LoginSuccess;
import com.android.shooshoo.presenters.UpdateUserInfoPresenter;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.PaginationScrollListener;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.adapter.BrandChooseAdapter;
import com.android.shooshoo.models.BrandsResult;
import com.android.shooshoo.views.UpdateUserInfoView;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**{@link BrandChooseActivity} is used to select brands of selected categories.
 * the user select at least 3 brands from the list then he can complete the registration.
 *
 *
 */
public class BrandChooseActivity extends BaseActivity implements UpdateUserInfoView, View.OnClickListener {

    // Index from which pagination should start (0 is 1st page in our case)
    private static final int PAGE_START = 0;

    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoading = false;

    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;

    // total no. of pages to load. Initial load is page 0, after which 2 more pages will load.
    private int TOTAL_PAGES = 1;
    // indicates the current page which Pagination is fetching.
    private int currentPage = PAGE_START;
    // indicates the current page which Pagination is fetching.
    private int LIMIT = 20;



    //this is list view used to show  list of  brands
    @BindView(R.id.list_categories)
    RecyclerView recyclerView;

    //after choosing user used to click next
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

    BrandChooseAdapter chooseAdapter;
    ConnectionDetector connectionDetector;//network checking by isConnectingToInternet method  of ConnectionDetector class
    UpdateUserInfoPresenter presenter;
    String cats="";
    //UpdateUserInfoPresenter is used to call the user in
    // updating services.It uses UpdateUserInfoView to update the service call response to ui

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_brand);
        ButterKnife.bind(this);
        connectionDetector=new ConnectionDetector(this);
        chooseAdapter=new BrandChooseAdapter(this);
        presenter=new UpdateUserInfoPresenter();
        presenter.attachView(this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(chooseAdapter);
        next_lay.setOnClickListener(this);
        tv_title.setText("YOUR Brands");
        setState();
        next_lay.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        recyclerView.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                loadNextPage();
            }

            @Override
            public int getTotalPageCount() { return TOTAL_PAGES; }

            @Override
            public boolean isLastPage() { return isLastPage; }

            @Override
            public boolean isLoading() { return isLoading; }
        });




        cats= userSession.getCats();
        if(connectionDetector.isConnectingToInternet())
        {
            isLoading=true;
            loadNextPage();
        }
        else
        showMessage("Please Check Your internet connection");


    }

    private void loadNextPage() {
        if (currentPage < TOTAL_PAGES) {
            if (connectionDetector.isConnectingToInternet())
                loadData(cats,currentPage);
            chooseAdapter.addLoadingFooter();
        }
        else
            isLastPage = true;
    }

    private void loadData(String ids,int offset) {
        showProgressIndicator(true);
        RetrofitApis.Factory.create(this).getBrands(ids,String.valueOf(LIMIT),String.valueOf(offset)).enqueue(new Callback<BrandsResult>() {
            @Override
            public void onResponse(Call<BrandsResult> call, Response<BrandsResult> response) {
                showProgressIndicator(false);
                isLoading=false;
                if(response.isSuccessful()){

                    BrandsResult brandsResult=response.body();
                    if(brandsResult.getStatus()==1) {
                        chooseAdapter.setBrands(brandsResult.getBrands());
                        TOTAL_PAGES = brandsResult.getCount();
                    }
                    currentPage=chooseAdapter.getItemCount();
                    chooseAdapter.removeLoadingFooter();
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
//                            Log.e("ids",chooseAdapter.getBrandIds());
                          presenter.updateUserBrand(userSession.getUserId(),chooseAdapter.getBrandIds());
                        }
                        else showMessage("please check internet connection");
                    } else {
                        if(chooseAdapter.getItemCount()==chooseAdapter.selectedSize())
                        {
                            presenter.updateUserBrand(userSession.getUserId(),chooseAdapter.getBrandIds());
                            break;
                        }
                        showMessage("Please select at least 3 Brands");
                    }
                }

                break;

            case R.id.iv_back:
                finish();
                break;

        }

    }


    private void setState() {
        button1.setBackgroundResource(R.drawable.unselected);
        button2.setBackgroundResource(R.drawable.unselected);
        button3.setBackgroundResource(R.drawable.selected);
    }

    @Override
    public void onUpdateUserInfo(ResponseBody responseBody) {
        try {
            String res=responseBody.string();
            Log.e("response",res);
            JSONObject object=new JSONObject(res);
            if(object.optString("status").equalsIgnoreCase("1")) {
                Intent homeIntent = new Intent(this, HomeActivity.class);
                startActivity(homeIntent);
                finishAffinity();
            }else {
                showMessage(object.getString("message"));
            }
        }catch (Exception e){
            e.printStackTrace();
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
