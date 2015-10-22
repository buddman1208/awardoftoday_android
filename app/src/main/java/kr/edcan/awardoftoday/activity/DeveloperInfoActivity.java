package kr.edcan.awardoftoday.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.edcan.awardoftoday.R;
import kr.edcan.awardoftoday.data.DeveloperInfo;
import kr.edcan.awardoftoday.utils.DeveloperAdapter;

public class DeveloperInfoActivity extends AppCompatActivity {

    DeveloperAdapter adapter;
    ArrayList<DeveloperInfo> arrayList;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_info);
        setActionbar(getSupportActionBar());
        setDefault();
    }

    private void setActionbar(ActionBar actionbar) {
        actionbar.setTitle(Html.fromHtml("<b>개발자 정보</b>"));
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    private void setDefault() {
        listView = (ListView) findViewById(R.id.developer_view_listview);
        arrayList = new ArrayList<>();
        arrayList.add(new DeveloperInfo("안드로이드 클라이언트 개발", "오준석"));
        arrayList.add(new DeveloperInfo("서버 백엔드 개발", "오준석"));
        arrayList.add(new DeveloperInfo("<디자이너 class=\"기획자\">", "구창림"));
        arrayList.add(new DeveloperInfo("<!-- /* 기획자 */ -->", "김영환"));
        arrayList.add(new DeveloperInfo("Special Thanks", "삼성전자 S/W센터 김지원 멘토님"));
        arrayList.add(new DeveloperInfo("", "License"));
        adapter = new DeveloperAdapter(DeveloperInfoActivity.this, arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Toast.makeText(DeveloperInfoActivity.this, "그만 밤새게 해주세요 제발", Toast.LENGTH_SHORT).show();
                    case 1:
                        Toast.makeText(DeveloperInfoActivity.this, "제발 자게 해주세요", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(DeveloperInfoActivity.this, "김영환 덤벼", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(DeveloperInfoActivity.this, "누가 요즘 기획자가 기획하죠 깔깔깔", Toast.LENGTH_SHORT).show();
                        break;
                }
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
