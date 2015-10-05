package kr.edcan.awardoftoday.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

//import kr.edcan.aiolib.EDCAN;
import kr.edcan.awardoftoday.R;
import kr.edcan.awardoftoday.utils.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DrawerLayout drawerLayout;
    ImageView drawer_launcher, updown_state;
    ViewPager pager;
    ViewPagerAdapter adapter;
    String Titles[] = {"홈", "목표", "원해요", "상"}, UpDownList[] = {"최신순", "오래된순", "남은시간"};
    String on_color = "#EF90B1", off_color = "#E6E6E6";
    int tab_count = 4, tab_on[], tab_off[];
    RelativeLayout m1, m2, m3, m4;
    ImageView tab_home, tab_achieve, tab_want, tab_award, tabs[];
    TextView t1, t2, t3, t4, text[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDefault();
        setTab(0, true);
        sharedPreferences = getSharedPreferences("AwardOfToday", 0);
        editor = sharedPreferences.edit();
//        EDCAN edcan = new EDCAN(getApplicationContext());
//        edcan.showDialog("환영합니다", "ㅁㄴㅇㄹ");
    }

    public void setDefault() {
        updown_state = (ImageView) findViewById(R.id.updown_state);
        updown_state.setOnClickListener(this);
        drawer_launcher.setOnClickListener(this);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, tab_count);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        m1 = (RelativeLayout) findViewById(R.id.main_tab_home);
        m2 = (RelativeLayout) findViewById(R.id.main_tab_achieve);
        m3 = (RelativeLayout) findViewById(R.id.main_tab_want);
        m4 = (RelativeLayout) findViewById(R.id.main_tab_award);
        m1.setOnClickListener(this);
        m2.setOnClickListener(this);
        m3.setOnClickListener(this);
        m4.setOnClickListener(this);
        t1 = (TextView) findViewById(R.id.main_tab_home_text);
        t2 = (TextView) findViewById(R.id.main_tab_achieve_text);
        t3 = (TextView) findViewById(R.id.main_tab_want_text);
        t4 = (TextView) findViewById(R.id.main_tab_award_text);
        tab_home = (ImageView) findViewById(R.id.main_tab_home_image);
        tab_achieve = (ImageView) findViewById(R.id.main_tab_achieve_image);
        tab_want = (ImageView) findViewById(R.id.main_tab_want_image);
        tab_award = (ImageView) findViewById(R.id.main_tab_award_image);
        tab_achieve = (ImageView) findViewById(R.id.main_tab_achieve_image);
        tabs = new ImageView[]{tab_home, tab_achieve, tab_want, tab_award};
        tab_on = new int[]{R.drawable.ic_tab_sticker_on, R.drawable.ic_tab_homeworks_on, R.drawable.ic_tab_wants_on, R.drawable.on};
        tab_off = new int[]{R.drawable.ic_tab_home_off, R.drawable.ic_tab_mygoal_off, R.drawable.ic_tab_wants_off, R.drawable.ic_tab_prize_off};
        text = new TextView[]{t1, t2, t3, t4};
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTab(position, false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setTab(int position, boolean isFirst) {
        if(isFirst){
            for (int i = 0; i < tab_count; i++) {
                text[i].setText(Titles[i]);
            }
        }
        for (int i = 0; i < tab_count; i++) {
            if (i == position) {
                tabs[i].setImageResource(tab_on[i]);
                text[i].setTextColor(Color.parseColor(on_color));
            } else {
                tabs[i].setImageResource(tab_off[i]);
                text[i].setTextColor(Color.parseColor(off_color));
            }
        }
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
        switch (v.getId()) {
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
                                if (text.equals("최신순")) editor.putBoolean("byTime", false);
                                else if (text.equals("오래된순")) editor.putBoolean("byTime", true);
                                else
                                    Toast.makeText(MainActivity.this, "이 기능은 준비중입니다", Toast.LENGTH_SHORT).show();
                                editor.commit();
                                setDefault();
                            }
                        })
                        .show();
                break;
            case R.id.main_tab_home:
                pager.setCurrentItem(0, true);
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_tab_achieve:
                pager.setCurrentItem(1, true);
                break;
            case R.id.main_tab_want :
                pager.setCurrentItem(2, true);
                break;
            case R.id.main_tab_award:
                pager.setCurrentItem(3, true);
                break;
        }
    }

    public void onResume() {
        super.onResume();
        setDefault();
        setTab(0, false);
    }
}
