package com.android.shooshoo.views;

import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.Company;

/**{@link SponsorChallengeView} is used by the {@link com.android.shooshoo.presenters.SponsorChallengePresenter} to interact with activity
 * that is implements the {@link SponsorChallengeView}
 *
 */
public interface SponsorChallengeView extends BaseView{
    /**it is called when the company registration is successfully completed
     *
     * @param company is result of company .It has details of Created Company
     */
    void onCompanyRegister(Company company);
    /**it is called when the Sponsor challenge registration is successfully completed
     *  @param challenge is result of challenge .It has details of Created Sponsor challenge
     */
    void onChallengeResponse(Challenge challenge);
}
