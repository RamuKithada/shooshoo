package com.android.shooshoo.views;

import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.models.User;

import java.util.List;

public interface SearchView extends BaseView{
    void onUserSearchResult(List<User> users);
    void onChallengeSearchResult(List<Challenge> challenges);
    void onBrandSearchResult(List<Company> brands);
    void onFilterSearchResult(List<User> users);

}
