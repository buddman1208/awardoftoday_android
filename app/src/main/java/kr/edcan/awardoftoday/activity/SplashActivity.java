package kr.edcan.awardoftoday.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private String API_KEY, userid, userpwd, username;
    private static final int SELECT_KEY = 1111;
    public String END_POINT;
    NetworkService service;
    RestAdapter restAdapter;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    ImageView logo, login, register;
    EditText user_id, user_pwd, user_name;
    RelativeLayout login_layout, register_layout;
    boolean isLogining, isRegistering, isTypeSelected, isParent;
    Intent Select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setRest();
        setDefault();
    }

    private void setDefault() {
        sharedPref = getSharedPreferences("AwardOfToday", 0);
        editor = sharedPref.edit();
        API_KEY = sharedPref.getString("API_KEY", "");
        logo = (ImageView) findViewById(R.id.logo);
        login = (ImageView) findViewById(R.id.btn_login);
        register = (ImageView) findViewById(R.id.register_button);
        user_id = (EditText) findViewById(R.id.user_id_input);
        user_pwd = (EditText) findViewById(R.id.user_pw_input);
        user_name = (EditText) findViewById(R.id.user_name_input);
        login_layout = (RelativeLayout) findViewById(R.id.login_layout);
        register_layout = (RelativeLayout) findViewById(R.id.register_layout);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        if (API_KEY.trim().equals("")) setMainLayout();
        else startMain();
    }

    private void startMain() {
        service.loginValidate(API_KEY, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "세션이 만료되었습니다", Toast.LENGTH_SHORT).show();
                setMainLayout();
            }
        });
    }

    private void setMainLayout() {
        logo.setVisibility(View.VISIBLE);
        user_id.setVisibility(View.GONE);
        user_pwd.setVisibility(View.GONE);
        user_name.setVisibility(View.GONE);
        login_layout.setVisibility(View.VISIBLE);
        register_layout.setVisibility(View.VISIBLE);
        isLogining = isRegistering = isTypeSelected = false;
    }

    private void setLogin() {
        logo.setVisibility(View.GONE);
        user_id.setVisibility(View.VISIBLE);
        user_pwd.setVisibility(View.VISIBLE);
        user_name.setVisibility(View.GONE);
        login_layout.setVisibility(View.VISIBLE);
        register_layout.setVisibility(View.GONE);
        isLogining = true;
        isRegistering = isTypeSelected = false;
    }

    private void setRegistering() {
        logo.setVisibility(View.GONE);
        user_id.setVisibility(View.VISIBLE);
        user_pwd.setVisibility(View.VISIBLE);
        user_name.setVisibility(View.VISIBLE);
        login_layout.setVisibility(View.GONE);
        register_layout.setVisibility(View.VISIBLE);
        isRegistering = true;
        isLogining = false;
    }

    private void setRest() {
        DeveloperService d = new DeveloperService(getApplicationContext());
        END_POINT = d.getEndpoint();
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .build();
        service = restAdapter.create(NetworkService.class);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (!isLogining) setLogin();
                else {
                    userid = user_id.getText().toString().trim();
                    userpwd = user_pwd.getText().toString().trim();
                    if (userid.equals("")) user_id.setError("ID를 입력해주세요");
                    else if (userpwd.equals("")) user_pwd.setError("비밀번호를 입력해주세요!");
                    else {
                        service.userLogin(user_id.getText().toString(), user_pwd.getText().toString(), new Callback<User>() {
                            @Override
                            public void success(User user, Response response) {
                                Log.e("name", user.name);
                                editor.putString("id", user.id);
                                editor.putString("name", user.name);
                                editor.putString("apikey", user.apikey);
                                editor.putBoolean("isParent", user.isParent);
                                editor.commit();
                                if (user.targetApikey == null) {
                                    startActivity(new Intent(getApplicationContext(), TutorialActivity.class));
                                    finish();
                                } else {
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                if (error.getResponse().getStatus() == 400)
                                    Toast.makeText(SplashActivity.this, "아이디 혹은 비밀번호가 잘못되었습니다", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                break;
            case R.id.register_button:
                if (!isRegistering && !isTypeSelected) {
                    Select = new Intent(getApplicationContext(), SelectIsParentActivity.class);
                    startActivityForResult(Select, SELECT_KEY);
                    setRegistering();
                } else {
                    userid = user_id.getText().toString().trim();
                    userpwd = user_pwd.getText().toString().trim();
                    username = user_name.getText().toString().trim();
                    if (userid.equals("")) user_id.setError("ID를 입력해주세요!");
                    else if (userpwd.equals("")) user_pwd.setError("비밀번호를 입력해주세요!");
                    else if (username.equals("")) user_name.setError("사용자 이름을 입력해주세요");
                    else {
                        new MaterialDialog.Builder(SplashActivity.this)
                                .title("확인해주세요!")
                                .content("ID : " + userid + "\n" + "이름 : " + username + "\n" + "위의 이름으로 가입을 진행합니다.\n계속하시겠습니까?")
                                .negativeText("취소")
                                .positiveText("확인")
                                .callback(new MaterialDialog.ButtonCallback() {
                                    @Override
                                    public void onPositive(MaterialDialog dialog) {
                                        super.onPositive(dialog);
                                        service.userRegister(userid, userpwd, username, isParent, new Callback<String>() {
                                            @Override
                                            public void success(String s, Response response) {
                                                Toast.makeText(getApplicationContext(), "성공적으로 가입되었습니다.\n가입한 정보로 로그인해주세요!", Toast.LENGTH_SHORT).show();
                                                setLogin();
                                                user_id.setText("");
                                                user_pwd.setText("");
                                            }

                                            @Override
                                            public void failure(RetrofitError error) {
                                                if (error.getResponse().getStatus() == 409)
                                                    user_id.setError("중복된 아이디입니다.");
                                                else
                                                    Toast.makeText(getApplicationContext(), error.getMessage() + "", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                })
                                .show();
                    }
                }
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SELECT_KEY:
                if (resultCode == RESULT_OK) {
                    isParent = intent.getExtras().getBoolean("isParent");
                } else if (resultCode == RESULT_CANCELED)
                    startActivityForResult(Select, SELECT_KEY);
                break;
        }
    }

}