package kr.edcan.awardoftoday.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import kr.edcan.awardoftoday.R;
import kr.edcan.awardoftoday.data.User;
import kr.edcan.awardoftoday.utils.DeveloperService;
import kr.edcan.awardoftoday.utils.NetworkService;
import kr.edcan.awardoftoday.utils.RegisterationIntentService;
import kr.edcan.awardoftoday.utils.RegisterationStatus;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Junseok Oh on 2015-10-11.
 */
public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private String API_KEY, userid, userpwd, username, token;
    private static final int SELECT_KEY = 1111;
    private BroadcastReceiver broadcastReceiver;
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
        getInstanceIdToken();
        setRest();
        registBroadcastReceiver();
        setDefault();
    }

    private void setDefault() {
        sharedPref = getSharedPreferences("AwardOfToday", 0);
        editor = sharedPref.edit();
        API_KEY = sharedPref.getString("apikey", "");
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
        user_pwd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (isLogining) onLogin();
                    else if (isRegistering) onRegister();
                }
                return false;
            }
        });
    }

    private void startMain() {
        service.loginValidate(API_KEY, token,  new Callback<String>() {
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
//        logo.setVisibility(View.GONE);
        user_id.setVisibility(View.VISIBLE);
        user_pwd.setVisibility(View.VISIBLE);
        user_name.setVisibility(View.GONE);
        login_layout.setVisibility(View.VISIBLE);
        register_layout.setVisibility(View.GONE);
        isLogining = true;
        isRegistering = isTypeSelected = false;
    }

    private void setRegistering() {
//        logo.setVisibility(View.GONE);
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
                onLogin();
                break;
            case R.id.register_button:
                onRegister();
                break;
        }
    }

    private void onLogin() {
        if (!isLogining) setLogin();
        else {
            userid = user_id.getText().toString().trim();
            userpwd = user_pwd.getText().toString().trim();
            if (userid.equals("")) user_id.setError("ID를 입력해주세요");
            else if (userpwd.equals("")) user_pwd.setError("비밀번호를 입력해주세요!");
            else {
                service.userLogin(user_id.getText().toString(), user_pwd.getText().toString(), token, new Callback<User>() {
                    @Override
                    public void success(User user, Response response) {
                        editor.putString("id", user.id);
                        editor.putString("name", user.name);
                        editor.putString("apikey", user.apikey);
                        editor.putInt("sticker", user.sticker);
                        editor.putBoolean("isParent", user.isParent);
                        if (user.targetApikey == null) {
                            editor.commit();
                            startActivity(new Intent(getApplicationContext(), TutorialActivity.class));
                            finish();
                        } else {
                            editor.putString("targetName", user.targetName);
                            editor.putString("targetApikey", user.targetApikey);
                            editor.commit();
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
    }

    private void onRegister() {
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
                        .negativeColor(Color.parseColor("#8B8B8B"))
                        .positiveColor(Color.parseColor("#F499B8"))
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
    }

    @Override
    public void onBackPressed() {
        new MaterialDialog.Builder(SplashActivity.this)
                .title("오늘의 어린이상!")
                .content("정말로 종료하시겠습니까?")
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
            if (isLogining || isRegistering) {
                isLogining = isRegistering = false;
                user_id.setText("");
                user_pwd.setText("");
                user_name.setText("");
                setMainLayout();
            } else onBackPressed();
        }
        return false;
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

    /**
     * 앱이 실행되어 화면에 나타날때 LocalBoardcastManager에 액션을 정의하여 등록한다.
     */
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(RegisterationStatus.REGISTRATION_READY));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(RegisterationStatus.REGISTRATION_GENERATING));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(RegisterationStatus.REGISTRATION_COMPLETE));

    }

    /**
     * 앱이 화면에서 사라지면 등록된 LocalBoardcast를 모두 삭제한다.
     */
    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }


    /**
     * Google Play Service를 사용할 수 있는 환경이지를 체크한다.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    public void getInstanceIdToken() {
        Log.e("Token", "getInstanceIdToken");
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegisterationIntentService.class);
            startService(intent);
        } else
            Toast.makeText(getApplicationContext(), "기기에서 Play Service가 지원되지 않습니다! \n일부 알림 서비스가 제공되지 않습니다!", Toast.LENGTH_SHORT).show();
    }

    public void registBroadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("Token", "getBroadcast");
                String action = intent.getAction();

                if (action.equals(RegisterationStatus.REGISTRATION_READY)) {
                } else if (action.equals(RegisterationStatus.REGISTRATION_GENERATING)) {
                } else if (action.equals(RegisterationStatus.REGISTRATION_COMPLETE)) {
                    // 액션이 COMPLETE일 경우
                    token = intent.getStringExtra("token");
                    editor.putString("token", token);
                    editor.commit();
                    if (API_KEY.trim().equals("")) setMainLayout();
                    else startMain();
                }

            }
        };
    }


}