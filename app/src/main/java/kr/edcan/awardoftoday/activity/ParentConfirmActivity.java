package kr.edcan.awardoftoday.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import kr.edcan.awardoftoday.R;
import kr.edcan.awardoftoday.utils.DeveloperService;
import kr.edcan.awardoftoday.utils.NetworkService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ParentConfirmActivity extends Activity implements View.OnClickListener {

    Intent intent;
    public String articleTitle, articleContent, articleKey;
    String END_POINT;
    boolean isConfirm;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    NetworkService service;
    RelativeLayout confirm, cancel;
    RestAdapter restAdapter;
    String apikey, targetApikey, token;
    TextView title, content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_confirm);
        setRest();
        setDefault();
    }

    private void setDefault() {
        intent = getIntent();
        sharedPreferences = getSharedPreferences("AwardOfToday", 0);
        editor = sharedPreferences.edit();
        articleTitle = intent.getStringExtra("title");
        articleContent = intent.getStringExtra("content");
        articleKey = intent.getStringExtra("articlekey");
        title = (TextView) findViewById(R.id.parent_alert_title);
        content = (TextView) findViewById(R.id.parent_alert_content);
        confirm = (RelativeLayout) findViewById(R.id.parent_alert_footer_left);
        cancel = (RelativeLayout) findViewById(R.id.parent_alert_footer_right);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        title.setText(articleTitle);
        content.setText(articleContent);
        apikey = sharedPreferences.getString("apikey", "");
        targetApikey = sharedPreferences.getString("targetApikey", "");
        token = sharedPreferences.getString("token", "");

    }

    private void setRest() {
        DeveloperService d = new DeveloperService(getApplicationContext());
        END_POINT = d.getEndpoint();
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .build();
        service = restAdapter.create(NetworkService.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.parent_alert_footer_left:
                isConfirm = false;
                break;
            case R.id.parent_alert_footer_right:
                isConfirm = true;
                break;
        }
        service.configureArticle(targetApikey, articleKey, isConfirm, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Toast.makeText(getApplicationContext(), "처리가 완료되었습니다!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
//    intent.putExtra("title", articleTitle);
//    intent.putExtra("content", articleContent);
//    intent.putExtra("articlekey", articleKey);
}
