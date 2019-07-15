package com.android.shooshoo.views;

import com.android.shooshoo.models.LoginSuccess;

public interface LoginView extends BaseView {

    void showMessage(int stringId);

    void showMessage(String message);
/** login details of a successful login
 * @param loginSuccess is user details  to store in preferences
 */
    void loginDetails(LoginSuccess loginSuccess);
//
//    void registrationDetails(LoginSuccess registrationSuccess);
//
//    void forgotPasswordDetails(ForResetResponse forResetResponse);
//
//    void resetPasswordDetails(ForResetResponse forResetResponse);
}