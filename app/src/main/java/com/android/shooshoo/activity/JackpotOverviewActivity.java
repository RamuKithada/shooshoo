package com.android.shooshoo.activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.shooshoo.R;
import com.android.shooshoo.models.GameMaster;
import com.android.shooshoo.utils.ConnectionDetector;
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
    @BindView(R.id.tv_challenge_name)
    TextView tv_challenge_name;
    ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jackpot_overview);
        ButterKnife.bind(this);
        btn_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        setStage(4);
        title.setText("Payment");
        this.connectionDetector = new ConnectionDetector(this);

        GameMaster gameMaster = this.userSession.getGameMaster();
        if (gameMaster != null) {
            this.tv_challenge_name.setText(gameMaster.getChallengeName());
//            this.tv_start_time.setText(gameMaster.getStartDate());
//            this.tv_end_time.setText(gameMaster.getEndDate());
        }
    }

    private void setStage(int i) {
        for (int index = 0; index < buttons.size(); index++) {
            if (index == i) {
                buttons.get(index).setBackgroundResource(R.drawable.selected);
                buttons.get(index).setText(String.valueOf(i + 1));
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
