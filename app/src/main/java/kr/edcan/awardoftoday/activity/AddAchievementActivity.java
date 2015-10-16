package kr.edcan.awardoftoday.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
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

import java.util.Calendar;
import java.util.GregorianCalendar;

import kr.edcan.awardoftoday.R;
import kr.edcan.awardoftoday.utils.AlarmService;

public class AddAchievementActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int year, month, day, hour, minute, second = 0, reward, sharedCount;
//    Slider reward_count;
    TextView picker_count, achieve_when;
    FloatingActionButton confirm;
    DatePickerDialog datePicker;
    TimePickerDialog timePicker;
    MaterialEditText title, content;
    boolean isSettings, timeSettings= false;
    String getTitle, getContent, getWhen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_achievement);
        setActionBar();
        setDefault();
        setDatePick();
    }

    void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#46A67C")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Html.fromHtml("<b>새로운 목표</b>"));
        actionBar.setElevation(0);
    }

    void setDefault() {
        sharedPreferences = getSharedPreferences("AwardOfToday", 1);
        editor = sharedPreferences.edit();
        content = (MaterialEditText) findViewById(R.id.achieve_content);
        title = (MaterialEditText) findViewById(R.id.achieve_name);
        confirm = (FloatingActionButton) findViewById(R.id.confirm_add);
        picker_count = (TextView) findViewById(R.id.picker_count);
        achieve_when = (TextView) findViewById(R.id.achieve_when);
//        reward_count = (Slider) findViewById(R.id.reward_count);
        picker_count.setText("0개");
        confirm.setOnClickListener(this);
//        reward_count.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
//            @Override
//            public void onPositionChanged(Slider slider, boolean b, float v, float v1, int i, int i1) {
//                picker_count.setText(slider.getValue() + "개");
//                reward = slider.getValue();
//            }
//        });
        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
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
                }, year, month-1, day);
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
                    sharedCount = sharedPreferences.getInt("count", 1);
                    getTitle = title.getText().toString().trim();
                    getContent = content.getText().toString().trim();
                    getWhen = year + "년 " + month + "월 " + day + "일 " + hour + "시 " + minute + "분";
                    if (getTitle.equals("")) title.setError("목표 이름을 입력해주세요!");
                    else if (getContent.equals("")) content.setError("목표 내용을 입력해주세요!");
                    else {
                        new MaterialDialog.Builder(AddAchievementActivity.this)
                                .title("다시 한번 확인해주세요!")
                                .content(
                                        "[" + getTitle + "]\n"
                                                + getContent + "\n"
                                                + "칭찬스티커 " + reward + "개\n\n"
                                                + "위의 목표를 " + getWhen + "까지로 설정합니다."
                                )
                                .theme(Theme.LIGHT)
                                .positiveText("설정")
                                .negativeText("취소")
                                .callback(new MaterialDialog.ButtonCallback() {
                                    @Override
                                    public void onPositive(MaterialDialog dialog) {
                                        super.onPositive(dialog);
                                        AlarmManager alarm = (AlarmManager) AddAchievementActivity.this.getSystemService(Context.ALARM_SERVICE);
                                        Intent intent = new Intent(AddAchievementActivity.this, AlarmService.class)
                                                .putExtra("title", getTitle)
                                                .putExtra("content", getContent);
                                        PendingIntent pender = PendingIntent.getBroadcast(AddAchievementActivity.this, 0, intent, 0);
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(year, month - 1, day, hour, minute, second);
                                        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pender);
                                        // TODO Must-be Replaced when we use server
                                        editor.putString("title" + sharedCount, getTitle);
                                        editor.putString("content" + sharedCount, getContent);
                                        editor.putString("when" + sharedCount, year + "." + month + "." + day + "." + hour + "." + minute);
                                        editor.putInt("reward" + sharedCount, reward);
                                        editor.putInt("count", sharedCount + 1);
                                        editor.commit();
                                        Toast.makeText(AddAchievementActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                })
                                .show();

                    }
                }
        }
    }
}
