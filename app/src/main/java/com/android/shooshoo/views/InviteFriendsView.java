package com.android.shooshoo.views;

import com.android.shooshoo.models.Follower;

import java.util.List;

public interface InviteFriendsView extends BaseView{
    void  onFriendsList(List<Follower> followers);
    void onSuccessFullInvitation(String message);

}
