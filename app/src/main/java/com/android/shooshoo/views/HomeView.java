package com.android.shooshoo.views;

import com.android.shooshoo.models.Challenge;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link HomeView} is implement by Home Activity the load challenges
 * and used by {@link com.android.shooshoo.presenters.HomePresenter}
 */
public interface HomeView  extends BaseView{

    /**we will get all list of sponsor challenges
 */   void onLoadSponsors(List<Challenge> challenges);
}
