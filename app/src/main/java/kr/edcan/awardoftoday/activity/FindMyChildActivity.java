package kr.edcan.awardoftoday.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import kr.edcan.awardoftoday.R;
import kr.edcan.awardoftoday.data.User;
import kr.edcan.awardoftoday.utils.DeveloperService;
import kr.edcan.awardoftoday.utils.NetworkService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Junseok Oh on 2015-10-11.
 */
public class FindMyChildActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    ImageView no_result;
    LinearLayout resultLayout;
    NetworkService service;
    RestAdapter restAdapter;
    EditText search_edittext;
    ImageView search_button, next_button;
    TextView child_name, child_id;
    String targetApikey;
    boolean foundChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_my_child);
        setRest();
        setDefault();
    }

    private void setRest() {
        DeveloperService d = new DeveloperService(getApplicationContext());
        String END_POINT = d.getEndpoint();
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .build();
        service = restAdapter.create(NetworkService.class);
    }

    private void setDefault() {
        sharedPref = getSharedPreferences("AwardOfToday", 0);
        editor = sharedPref.edit();
        foundChild = false;
        resultLayout = (LinearLayout) findViewById(R.id.findbychild_search_layout);
        no_result = (ImageView) findViewById(R.id.findbychild_status_image);
        search_edittext = (EditText) findViewById(R.id.findmychild_edittext);
        search_button = (ImageView) findViewById(R.id.findbychild_btn_search);
        next_button = (ImageView) findViewById(R.id.findbychild_btn_next);
        child_name = (TextView) findViewById(R.id.findmychild_child_name);
        child_id = (TextView) findViewById(R.id.findmychild_child_id);
        search_button.setOnClickListener(this);
        next_button.setOnClickListener(this);
        no_result.setVisibility(View.VISIBLE);
        no_result.setImageResource(R.drawable.text_no_search_data);
        resultLayout.setVisibility(View.GONE);
        search_edittext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchQuery();
                }
                return false;
            }
        });
    }

    private void searchQuery() {
        String query = search_edittext.getText().toString().trim();
        if (query.equals("")) search_edittext.setError("검색어를 입력해주세요!");
        else {
            service.findChild(query, new Callback<User>() {
                @Override
                public void success(User user, Response response) {
                    no_result.setVisibility(View.GONE);
                    resultLayout.setVisibility(View.VISIBLE);
                    child_name.setText(user.name);
                    child_id.setText(user.id);
                    targetApikey = user.apikey;
                    foundChild = true;
                }

                @Override
                public void failure(RetrofitError error) {
                    no_result.setVisibility(View.VISIBLE);
                    no_result.setImageResource(R.drawable.search_failed);
                    resultLayout.setVisibility(View.GONE);
                    foundChild = false;
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        new MaterialDialog.Builder(FindMyChildActivity.this)
                .title("오늘의 어린이상!")
                .content("자녀 설정 작업이 아직 완료되지 않았습니다\n정말로 종료하시겠습니까?")
                .positiveText("확인")
                .negativeText("취소")
                .negativeColor(Color.parseColor("#8B8B8B"))
                .positiveColor(Color.parseColor("#F499B8"))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        finish();
                    }
                })
                .show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.findbychild_btn_search:
                searchQuery();
                break;
            case R.id.findbychild_btn_next :
                if(foundChild) {
                    new MaterialDialog.Builder(FindMyChildActivity.this)
                            .title("오늘의 어린이상!")
                            .content(child_name.getText().toString() + " 어린이를 자녀로 설정하시겠습니까?")
                            .positiveText("확인")
                            .negativeText("취소")
                            .negativeColor(Color.parseColor("#8B8B8B"))
                            .positiveColor(Color.parseColor("#F499B8"))
                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    super.onPositive(dialog);
                                    String name = sharedPref.getString("name", "");
                                    String apikey = sharedPref.getString("apikey", "");
                                    service.registerChild(name, apikey, child_name.getText().toString(), targetApikey, new Callback<User>() {
                                        @Override
                                        public void success(User user, Response response) {
                                            editor.putString("targetName", user.targetName);
                                            editor.putString("targetApikey", user.targetApikey);
                                            editor.commit();
                                            Toast.makeText(getApplicationContext(), user.targetName + " 자녀와 연결되었습니다!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            finish();
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {

                                        }
                                    });
                                }
                            })
                            .show();
                }
        }
    }
}
