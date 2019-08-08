package com.android.shooshoo.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.shooshoo.R;
import com.android.shooshoo.activity.HomeSearchActivity;
import com.android.shooshoo.adapter.CompanyFollowAdapter;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.utils.UserSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrandSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrandSearchFragment extends Fragment implements TextWatcher ,CompanyFollowAdapter.FollowCompanyListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @BindView(R.id.list)
    RecyclerView list;
    CompanyFollowAdapter companyListAdapter;
    List<Company> companyList=new ArrayList<>();

    HomeSearchActivity homeSearchActivity;
    static BrandSearchFragment fragment=null;

    ConnectionDetector detector;
    UserSession userSession;


    public BrandSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilterSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BrandSearchFragment newInstance(String param1, String param2) {
        if(fragment==null) {
            fragment = new BrandSearchFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_brand_search, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this,view);
//        text.setText(mParam1);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        companyListAdapter=new CompanyFollowAdapter(getContext(),companyList);
        companyListAdapter.setFollowCompanyListener(this);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(companyListAdapter);
        if(homeSearchActivity!=null){
            if(companyList.isEmpty())
             homeSearchActivity.getHomeSearchPresenter().searchCompany("a");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof HomeSearchActivity){
            homeSearchActivity= (HomeSearchActivity) context;
        }
        detector=new ConnectionDetector(context);
        userSession=new UserSession(context);


    }

    @Override
    public void onDetach() {
        homeSearchActivity=null;
        super.onDetach();
    }



    public void onBrandSearchResult(List<Company> brands){
        companyList.clear();
        if(brands!=null)
            companyList.addAll(brands);
        if(getUserVisibleHint())
            companyListAdapter.notifyDataSetChanged();

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(getContext()!=null)
        if(isVisibleToUser)
            companyListAdapter.notifyDataSetChanged();
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(homeSearchActivity!=null){
            homeSearchActivity.getHomeSearchPresenter().searchCompany(s.toString());
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFollow(Company company) {
        company.setSelected(Math.abs(1-company.getSelected()));
        companyListAdapter.notifyDataSetChanged();
        if(detector.isConnectingToInternet())
        saveBrand(userSession.getUserInfo().getUserId(),company.getCompanyId());

    }
    public void saveBrand(String userId,String companyId){
        if(homeSearchActivity!=null)
            homeSearchActivity.showProgressIndicator(true);
        RetrofitApis.Factory.create(getContext()).saveBrand(userId,companyId)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(homeSearchActivity!=null)
                            homeSearchActivity.showProgressIndicator(false);
                        if(response.isSuccessful()){
                            if(homeSearchActivity!=null)
                            {
                                try {
                                    JSONObject object = new JSONObject(response.body().string());
                                    homeSearchActivity.showMessage(object.optString("message"));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if(homeSearchActivity!=null)
                        {
                            homeSearchActivity.showProgressIndicator(false);
                            homeSearchActivity.showMessage(t.getMessage());
                        }
                    }
                });

    }
}
