package com.android.shooshoo.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.FindContactsAdapter;
import com.android.shooshoo.models.ContactsModel;
import com.android.shooshoo.models.Follower;
import com.android.shooshoo.models.FollowerResult;
import com.android.shooshoo.presenters.InviteFriendsPresenter;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.BaseView;
import com.android.shooshoo.views.InviteFriendsView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;

public class InviteFriendActivity extends BaseActivity implements View.OnClickListener, InviteFriendsView {

    /** this is used to invite your friends to participate in the challenge i.e., created by u
     *   Here we fetch contacts data from  phone and show the list to invite
     */
    @BindView(R.id.btn_next)
    TextView btn_next;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindViews({R.id.button1,R.id.button2,R.id.button3,R.id.button4})
    List<Button> buttons;

    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.friends_list)
    RecyclerView friends_list;

    @BindView(R.id.edt_search)
    AppCompatEditText edt_search;
    @BindView(R.id.select_all)
    AppCompatTextView select_all;

    @BindView(R.id.tv_no_data)
    AppCompatTextView tv_no_data;

    @BindView(R.id.data_layer)
    LinearLayout data_layer;


    private FindContactsAdapter findContactsAdapter;
    private ArrayList<ContactsModel> contactsModelArrayList=new ArrayList<>();
    private HashMap<String,String> filterContacts_hmap;

    ConnectionDetector connectionDetector;
    UserSession userSession;
    ArrayList<Follower> followers=new ArrayList<>();
    InviteFriendsPresenter inviteFriendsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        ButterKnife.bind(this);
        title.setText("Invite Friends");
        setStage(2);
        select_all.setOnClickListener( this);
        iv_back.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        friends_list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        findContactsAdapter=new FindContactsAdapter(this,followers);
        friends_list.setAdapter(findContactsAdapter);
/*        if (checkPermission(READ_CONTACTS))
            showContacts();
        else
            requestPermission(READ_CONTACTS);*/

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                findContactsAdapter.getFilter().filter(text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        connectionDetector=new ConnectionDetector(this);
        userSession=new UserSession(this);
        inviteFriendsPresenter=new InviteFriendsPresenter();
        if(connectionDetector.isConnectingToInternet()){
            inviteFriendsPresenter.attachView(this);
            inviteFriendsPresenter.getFollowers(userSession.getUserId());
        }

    }

    private void sendNotification() {


        StringBuilder invites=new StringBuilder();
        for (Follower follower :followers) {
            if(follower.isSelected())
            if(invites.length()==0){
                invites.append(follower.getFromId());
            }else {
                invites.append(',').append(follower.getFromId());

            }

        }
         if(invites.length()>0) {
             if (connectionDetector.isConnectingToInternet())
                 inviteFriendsPresenter.sendInvitations(userSession.getJackpot().getChallengeId(), invites.toString());
         }else {
             onSuccessFullInvitation("");
         }
    }

    /**
     * setStage is for selection one of registration step
     * @param step is step of registration process of a challenge
     */
    private void setStage(int step) {
        for(int index=0;index<buttons.size();index++){
            if(index<=step){
                {
                    buttons.get(index).setBackgroundResource(R.drawable.selected);
//                    buttons.get(index).setText(String.valueOf(step+1));
                }
            }else buttons.get(index).setBackgroundResource(R.drawable.unselected);

        }
    }
    /*   private boolean checkPermission(String permission) {
         if (Build.VERSION.SDK_INT >= 23) {
             int result = ContextCompat.checkSelfPermission(this, permission);
             if (result == PackageManager.PERMISSION_GRANTED) {
                 return true;
             } else {
                 return false;
             }
         } else {
             return true;
         }
     }

        private void requestPermission(String permission) {
             if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                 Toast.makeText(this, "Get account permission allows us to get your email",
                         Toast.LENGTH_LONG).show();
             }
             ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSION_REQUEST_CODE);
         }
        public final static int PERMISSION_REQUEST_CODE=2;
         @Override
         public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
             switch (requestCode) {
                 case PERMISSION_REQUEST_CODE:
                     if(permissions[0].equalsIgnoreCase(READ_CONTACTS)){
                         if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                             showContacts();
                         else
                             showMessage("Please Accept the CONTACTS Permission to Load your Contacts");
                     }

                     break;
             }
         }
    private void showContacts()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
             showProgressIndicator(true);
                getContacts();
            }
        });
    }

    public void getContacts() {
        Cursor c = null;
        try {
            c = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            filterContacts_hmap=new HashMap<>();
            while (c.moveToNext()) {
                ContactsModel model=new ContactsModel();
                String contactID = c.getString(c
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                String contactName = c.getString(c
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phNumber = c.getString(c
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                String contact = phNumber.replace(" ", "");
                String contact1 = contact.replace("-", "");
                //String contact4 = contact1.replace("+", "");
                String contact2 = contact1.replace("(", "");
                String contact3 = contact2.replace(")", "");
                String number=contact3;

                if(!filterContacts_hmap.containsKey(contactID)){
                    filterContacts_hmap.put(contactID, contactID);
                    model.setId(contactID);
                    model.setName(contactName);
                    model.setNumber(number);
                    contactsModelArrayList.add(model);
                }
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        displayContacts();
    }
    private void displayContacts()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showProgressIndicator(false);
                findContactsAdapter.notifyDataSetChanged();
            }
        });
    }
*/
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                if (findContactsAdapter.getItemCount()>0)
                      sendNotification();
                else {
                    onSuccessFullInvitation("");
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.select_all:
                findContactsAdapter.setSelectAll();
                break;

        }

    }

    @Override
    public void onFriendsList(List<Follower> mFollowers) {
        if(mFollowers!=null){
            followers.addAll(mFollowers);
            findContactsAdapter=new FindContactsAdapter(InviteFriendActivity.this,followers);
            friends_list.setAdapter(findContactsAdapter);
            tv_no_data.setVisibility(View.GONE);
            data_layer.setVisibility(View.VISIBLE);
        }else {
            tv_no_data.setVisibility(View.VISIBLE);
            data_layer.setVisibility(View.GONE);
        }
    }
    @Override
    public void onSuccessFullInvitation(String message) {
        Intent intent=new Intent(InviteFriendActivity.this,JackpotOverviewActivity.class);
        startActivity(intent);
    }


}
