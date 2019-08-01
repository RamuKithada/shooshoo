package com.android.shooshoo.sponsor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.shooshoo.R;
import com.android.shooshoo.activity.BaseActivity;
import com.android.shooshoo.activity.CompanyProfileActivity;
import com.android.shooshoo.adapter.CompanyListAdapter;
import com.android.shooshoo.models.BrandsResult;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.models.UserInfo;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.PaginationScrollListener;
import com.android.shooshoo.utils.RetrofitApis;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.shooshoo.utils.ApiUrls.PROFILE_IMAGE_URL;

public class SponsorChallenge extends BaseActivity implements View.OnClickListener, CompanyListAdapter.SelectedChangeListener {

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

        CompanyListAdapter companyListAdapter;
        ConnectionDetector connectionDetector;

        @BindView(R.id.private_sponsor)
        LinearLayout privateChallenge;

        @BindView(R.id.profile_pic)
        ImageView profile_pic;

        @BindView(R.id.company_register)
        LinearLayout comapanyRegiser;

        @BindView(R.id.iv_back)
        ImageView iv_back;

        @BindView(R.id.btn_next)
        TextView btn_next;

        @BindView(R.id.authorized)
        TextView authorized;

        List<Company> brandList=new ArrayList<Company>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_challenge);
        ButterKnife.bind(this);
        privateChallenge.setOnClickListener(this);
        comapanyRegiser.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        connectionDetector=new ConnectionDetector(this);
        companyListAdapter=new CompanyListAdapter(this,brandList);
        companyListAdapter.setSelectedChangeListener(this);
        UserInfo userInfo=userSession.getUserInfo();
        if(userInfo!=null)
        Picasso.with(getContext()).load(PROFILE_IMAGE_URL+userInfo.getImage()).placeholder(R.drawable.giphy).error(R.drawable.error).into(profile_pic);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(companyListAdapter);
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
        if(connectionDetector.isConnectingToInternet())
            loadNextPage();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.private_sponsor:
                Intent privateSponsor=new Intent(this,SponsorChallengeFormActivity.class);
                privateSponsor.putExtra("privateSponsor",1);
                startActivity(privateSponsor);
                break;
            case R.id.company_register:
                startActivity(new Intent(this, CompanyProfileActivity.class));
                break;
            case R.id.iv_back:
                 finish();
                break;
            case R.id.btn_next:
                if(companyListAdapter.selectedSize()>0) {
                    Intent intent = new Intent(this, SponsorChallengeFormActivity.class);
                    intent.putExtra("privateSponsor", 0);
                    userSession.setSponsorIds(companyListAdapter.getBrandIds());
                    startActivity(intent);
                }
                break;
        }

    }

    private void loadNextPage() {
        if (currentPage < TOTAL_PAGES) {
            if (connectionDetector.isConnectingToInternet())
                loadData(userSession.getUserId(),currentPage);
            companyListAdapter.addLoadingFooter();
        }
        else
            isLastPage = true;
    }
    private void loadData(String ids,int offset) {
        showProgressIndicator(true);
        RetrofitApis.Factory.create(this).getUserBrands(ids,String.valueOf(LIMIT),String.valueOf(offset)).enqueue(new Callback<BrandsResult>() {
            @Override
            public void onResponse(Call<BrandsResult> call, Response<BrandsResult> response) {
                showProgressIndicator(false);
                isLoading=false;
                if(response.isSuccessful()){

                    BrandsResult brandsResult=response.body();
                    if(brandsResult.getStatus()==1) {
                        companyListAdapter.setBrands(brandsResult.getCompanies());
                        TOTAL_PAGES = brandsResult.getCount();
                    }
                    currentPage=companyListAdapter.getItemCount();
                    companyListAdapter.removeLoadingFooter();
                }
                if(companyListAdapter.getItemCount()>0){
                    authorized.setVisibility(View.VISIBLE);
                }else {
                    authorized.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<BrandsResult> call, Throwable t) {
                showProgressIndicator(false);
            }
        });
    }

    @Override
    public void onSelection(int selectedSize) {
        if(selectedSize>0){
            btn_next.setVisibility(View.VISIBLE);
        }else {
            btn_next.setVisibility(View.GONE);
        }

    }
}
