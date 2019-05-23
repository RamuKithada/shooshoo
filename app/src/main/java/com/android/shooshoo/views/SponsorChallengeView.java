package com.android.shooshoo.views;

import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.Company;

public interface SponsorChallengeView extends BaseView{
    void onCompanyRegister(Company company);
    void onChallengeResponse(Challenge company);
}
