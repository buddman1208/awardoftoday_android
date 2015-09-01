package kr.edcan.awardoftoday.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rey.material.widget.Slider;

import java.util.ArrayList;

import kr.edcan.awardoftoday.R;
import kr.edcan.awardoftoday.utils.SlidingTabLayout;
import kr.edcan.awardoftoday.utils.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DrawerLayout drawerLayout;
    ImageView drawer_launcher, updown_state;
    ViewPager pager;
    ViewPagerAdapter adapter;
    ArrayAdapter<String> updownAdapter;
    SlidingTabLayout tabs;
    String Titles[] = {"홈", "목표", "원해요", "상"}, UpDownList[] = {"최신순", "오래된순", "남은시간"};
    int tab_count = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDefault();
        sharedPreferences = getSharedPreferences("AwardOfToday", 0);
        editor = sharedPreferences.edit();
        ArrayList<String> arr = new ArrayList<>();
        arr.add("최신순");
        arr.add("오래된순");
        updownAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arr);
    }
    public void setDefault(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_launcher =(ImageView) findViewById(R.id.drawer_launch);
        updown_state = (ImageView)findViewById(R.id.updown_state);
        updown_state.setOnClickListener(this);
        drawer_launcher.setOnClickListener(this);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, tab_count);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        tabs.setViewPager(pager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.drawer_launch:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.updown_state:
                new MaterialDialog.Builder(this)
                        .title("정렬")
                        .items(UpDownList)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                if(text.equals("최신순")) editor.putBoolean("byTime", false);
                                else if(text.equals("오래된순")) editor.putBoolean("byTime", true);
                                else
                                    Toast.makeText(MainActivity.this, "이 기능은 준비중입니다", Toast.LENGTH_SHORT).show();
                                editor.commit();
                                setDefault();
                            }
                        })
                        .show();
        }
    }
    public void onResume(){
        super.onResume();
        setDefault();
    }
}
