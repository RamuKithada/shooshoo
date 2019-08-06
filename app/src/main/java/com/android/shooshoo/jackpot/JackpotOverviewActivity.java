package com.android.shooshoo.jackpot;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.shooshoo.R;
import com.android.shooshoo.activity.BaseActivity;
import com.android.shooshoo.activity.HomeActivity;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.ConnectionDetector;
import com.squareup.picasso.Picasso;

import java.util.List;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**{@link JackpotOverviewActivity} is the Payment screen in the Jackpot registration process.
 * This is the last step in  Jackpot registration process.
 * Here we show the challenge data is stored in preferences
 *
 *
 */
public class JackpotOverviewActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.btn_next)
    TextView btn_next;

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindViews({R.id.button1, R.id.button2, R.id.button3, R.id.button4})
    List<Button> buttons;

    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.challenge_title)
    TextView challenge_title;
    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.brand)
    TextView brand;
    @BindView(R.id.sub_title)
    TextView description;
    @BindView(R.id.time)
    TextView time;
    ConnectionDetector connectionDetector;


    @BindView(R.id.country_name)
    AppCompatTextView country_name;
    @BindView(R.id.city_name)
    AppCompatTextView city_name;
    @BindView(R.id.zip_code)
    AppCompatTextView zip_code;
    @BindView(R.id.categories)
    AppCompatTextView categories;

    @BindView(R.id.age_range)
    AppCompatTextView age_range;
    @BindView(R.id.gender)
    AppCompatTextView gender;

    @BindView(R.id.per_user)
    AppCompatTextView per_user;

    @BindView(R.id.audience_size)
    AppCompatTextView audience_size;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jackpot_overview);
        ButterKnife.bind(this);
        btn_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        setStage(3);
        title.setText("Summary");
        this.connectionDetector = new ConnectionDetector(this);

        Challenge jackpot = userSession.getJackpot();
        if (jackpot != null)
            setChallenge(jackpot);

    }

    private void setChallenge(Challenge challenge) {

            challenge_title.setText(challenge.getChallengeName());

            Picasso.with(this)
                    .load(ApiUrls.JACKPOT_BANNER_IMAGE_URL + challenge.getBannerImage())
                    .error(R.drawable.rose)
                    .placeholder(R.drawable.rose)
                    .into(imageView);
            time.setText(ApiUrls.getDurationTimeStamp2(challenge.getEndDate()+" "+challenge.getEndTime()));
            brand.setBackgroundColor(Color.parseColor("#549BC1"));
            brand.setText(challenge.getAmount());
            StringBuilder builder=new StringBuilder();
            if(challenge.getFirstName()!=null)
                builder.append(challenge.getFirstName());
            if(challenge.getLastName()!=null)
                builder.append(' ').append(challenge.getLastName());

            description.setText(builder.toString());
        country_name.setText(challenge.getCountryName());
        city_name.setText(challenge.getCityName());
        age_range.setText(challenge.getAgeStart() + " - " + challenge.getAgeEnd());
        StringBuilder builderCat=new StringBuilder();
        if(challenge.getCategoryNames()!=null)
        for (String name:challenge.getCategoryNames()) {
            if(builderCat.length()==0)
                builderCat.append(name);
                        else{
                builderCat.append(',').append(name);
                        }
        }
        categories.setText(builderCat.toString());
        zip_code.setText(challenge.getAudZipcode());
        gender.setText(challenge.getAudGender());
        per_user.setText(challenge.getAmount());
        audience_size.setText(userSession.getAudSize());


    }

    private void setStage(int i) {
        for (int index = 0; index < buttons.size(); index++) {
            if (index <= i) {
                buttons.get(index).setBackgroundResource(R.drawable.selected);
//                buttons.get(index).setText(String.valueOf(i + 1));
            } else
                buttons.get(index).setBackgroundResource(R.drawable.unselected);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                Intent homeIntent = new Intent(this, HomeActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
