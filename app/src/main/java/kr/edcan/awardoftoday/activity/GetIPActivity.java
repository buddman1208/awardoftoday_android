package kr.edcan.awardoftoday.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import kr.edcan.awardoftoday.R;

/**
 * Created by Junseok Oh on 2015-10-11.
 */
public class GetIPActivity extends AppCompatActivity {

    Button button;
    SharedPreferences asfd;
    SharedPreferences.Editor editor;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ip);
        button = (Button) findViewById(R.id.next);
        editText = (EditText) findViewById(R.id.asdf);
        editText.setText("http://192.168.1.8:3000");
        asfd = getSharedPreferences("AwardOfToday", 0);
        editor = asfd.edit();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("ip", editText.getText().toString());
                editor.commit();
                startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                finish();
            }
        });
    }
}
