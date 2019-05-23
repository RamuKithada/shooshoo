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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.FindContactsAdapter;
import com.android.shooshoo.models.ContactsModel;
import com.android.shooshoo.views.BaseView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

import static android.Manifest.permission.READ_CONTACTS;

public class InviteFriendActivity extends BaseActivity {
    @BindView(R.id.btn_next)
    TextView btn_next;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindViews({R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5})
    List<Button> buttons;

    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.friends_list)
    RecyclerView friends_list;

    @BindView(R.id.edt_search)
    EditText edt_search;

    private FindContactsAdapter findContactsAdapter;
    private ArrayList<ContactsModel> contactsModelArrayList=new ArrayList<>();
    private HashMap<String,String> filterContacts_hmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        ButterKnife.bind(this);
        title.setText("The Challenge");
        setStage(3);
        friends_list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        findContactsAdapter=new FindContactsAdapter(this,contactsModelArrayList);
        friends_list.setAdapter(findContactsAdapter);

        if (checkPermission(READ_CONTACTS))
              showContacts();
        else requestPermission(READ_CONTACTS);

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
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InviteFriendActivity.this,JackpotOverviewActivity.class);
                    startActivity(intent);
            }
        });

    }
    private void setStage(int i) {
        for(int index=0;index<buttons.size();index++){
            if(index==i){
                {
                    buttons.get(index).setBackgroundResource(R.drawable.selected);
                    buttons.get(index).setText(String.valueOf(i+1));
                }
            }else buttons.get(index).setBackgroundResource(R.drawable.unselected);

        }
    }
    private boolean checkPermission(String permission) {
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

}
