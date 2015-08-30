package kr.edcan.awardoftoday.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.rey.material.app.Dialog;

import kr.edcan.awardoftoday.R;

public class AddAchievement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_achievement);
        setActionBar();
        setDatePick();
    }

    void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#46A67C")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Html.fromHtml("<b>새로운 목표</b>"));
        actionBar.setElevation(0);
    }

    void setDatePick(){
        MaterialRippleLayout datePick = (MaterialRippleLayout)findViewById(R.id.add_datepick);
        datePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddAchievement.this, "asdf", Toast.LENGTH_SHORT).show();
                Dialog datePick = new Dialog(AddAchievement.this);
                datePick.applyStyle(R.style.Material_App_Dialog_DatePicker_Light);
                datePick.show();
            }
        });
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
}
