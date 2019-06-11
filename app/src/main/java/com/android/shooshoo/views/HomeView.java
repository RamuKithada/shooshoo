package com.android.shooshoo.views;

import com.android.shooshoo.models.Challenge;

import java.util.ArrayList;
import java.util.List;

public interface HomeView  extends BaseView{
    void onLoadSponsors(List<Challenge> challenges);
}
