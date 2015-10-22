package kr.edcan.awardoftoday.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.balysv.materialripple.MaterialRippleLayout;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Slider;

import java.util.Calendar;
import java.util.GregorianCalendar;

import kr.edcan.awardoftoday.R;
import kr.edcan.awardoftoday.data.Article;
import kr.edcan.awardoftoday.utils.DeveloperService;
import kr.edcan.awardoftoday.utils.NetworkService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Junseok Oh on 2015-10-11.
 */
public class AddAchievementActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int year, month, day, hour, minute, reward;
    Slider reward_count;
    TextView picker_count, achieve_when;
    FloatingActionButton confirm;
    DatePickerDialog datePicker;
    TimePickerDialog timePicker;
    MaterialEditText title, content;
    boolean isSettings, timeSettings= false;
    String getTitle, getContent, getWhen;
    NetworkService service;
    RestAdapter restAdapter;
    public String API_KEY, date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_achievement);
        setActionBar(getSupportActionBar());
        setDefault();
        setRest();
        setDatePick();
    }

    private void setActionBar(ActionBar actionbar) {
        actionbar.setTitle(Html.fromHtml("<b>새로운 목표</b>"));
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    public void setDefault() {
        sharedPreferences = getSharedPreferences("AwardOfToday", 1);
        editor = sharedPreferences.edit();
        API_KEY = sharedPreferences.getString("targetApikey", "");
        content = (MaterialEditText) findViewById(R.id.achieve_content);
        title = (MaterialEditText) findViewById(R.id.achieve_name);
        confirm = (FloatingActionButton) findViewById(R.id.confirm_add);
        picker_count = (TextView) findViewById(R.id.picker_count);
        achieve_when = (TextView) findViewById(R.id.achieve_when);
        reward_count = (Slider) findViewById(R.id.reward_count);
        confirm.setOnClickListener(this);
        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        picker_count.setText("0개");
        reward_count.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider slider, boolean b, float v, float v1, int i, int i1) {
                picker_count.setText(slider.getValue() + "");
                reward = slider.getValue();
            }
        });
    }
    private void setRest() {
        DeveloperService d = new DeveloperService(getApplicationContext());
        String END_POINT = d.getEndpoint();
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .build();
        service = restAdapter.create(NetworkService.class);
    }

    void setDatePick() {
        final MaterialRippleLayout datePick = (MaterialRippleLayout) findViewById(R.id.add_datepick);
        datePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = new DatePickerDialog(AddAchievementActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int i, int j, int k) {
                        year = i;
                        month = j + 1;
                        day = k;
                        isSettings = true;
                    }
                }, year, month - 1, day);
                datePicker.setCancelable(false);
                datePicker.show();
                datePicker.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (isSettings == true) {
                            setTimeDialog();
                        }
                    }
                });
            }
        });
    }

    void setTimeDialog() {
        if (isSettings == true) {
            timePicker = new TimePickerDialog(AddAchievementActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    hour = i;
                    minute = i1;
                    timeSettings = true;
                    achieve_when.setText(year + "년 " + month + "월 " + day + "일 " + hour + "시 " + minute + "분");
                }
            }, hour, minute, true);
            timePicker.setCancelable(false);
            timePicker.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_add:
                if(!timeSettings)
                    Toast.makeText(AddAchievementActivity.this, "시간 설정을 완료해주세요!", Toast.LENGTH_SHORT).show();
                else {
                    getTitle = title.getText().toString().trim();
                    getContent = content.getText().toString().trim();
                    getWhen = year + "년 " + month + "월 " + day + "일 " + hour + "시 " + minute + "분";
                    if (getTitle.equals("")) title.setError("과제 제목을 입력해주세요!");
                    else if (getContent.equals("")) content.setError("목표 내용을 입력해주세요!");
                    else {
                        new MaterialDialog.Builder(AddAchievementActivity.this)
                                .title("다시 한번 확인해주세요!")
                                .content(
                                        "[" + getTitle + "]\n"
                                                + getContent + "\n"
                                                + "칭찬스티커 " + reward + "개\n\n"
                                                + "기한 : "+getWhen+"\n"
                                                + "위 정보로 과제를 등록합니다."
                                )
                                .theme(Theme.LIGHT)
                                .positiveText("등록")
                                .negativeText("취소")
                                .negativeColor(Color.parseColor("#8B8B8B"))
                                .positiveColor(Color.parseColor("#F499B8"))
                                .callback(new MaterialDialog.ButtonCallback() {
                                    @Override
                                    public void onPositive(MaterialDialog dialog) {
                                        super.onPositive(dialog);
                                        date = year+"-"+month+"-"+day+" "+hour+":"+minute;
                                        postArticle();
                                    }
                                })
                                .show();

                    }
                }
        }
    }
    public void postArticle(){
        service.postArticle(API_KEY, getTitle, Integer.parseInt(picker_count.getText().toString()), date, getContent, new Callback<Article>() {
            @Override
            public void success(Article article, Response response) {
                Toast.makeText(AddAchievementActivity.this, "과제가 등록되었습니다!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        new MaterialDialog.Builder(AddAchievementActivity.this)
                .title("오늘의 어린이상")
                .content("목표 설정을 취소하고 뒤로 나가시겠습니까?")
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
}
