package kr.edcan.awardoftoday.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import kr.edcan.awardoftoday.R;

/**
 * Created by kotohana5706 on 2015. 8. 30..
 */
public class Tab_Achieve extends Fragment implements View.OnClickListener {

    LinearLayout tab1, tab2, tab3;
    TextView tab_title1, tab_title2, tab_title3, tab_title[];
    ImageView tab_under1, tab_under2, tab_under3, tab_under[];
    View v;
    LinearLayout main_view;
    FloatingActionButton add;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.main_achieve, container, false);
        setDefault();
        setTab(0);
        return v;
    }

    private void setDefault() {
        main_view = (LinearLayout) v.findViewById(R.id.achieve_view_layout);
        tab1 = (LinearLayout) v.findViewById(R.id.achieve_tab1);
        tab2 = (LinearLayout) v.findViewById(R.id.achieve_tab2);
        tab3 = (LinearLayout) v.findViewById(R.id.achieve_tab3);
        tab_title1 = (TextView) v.findViewById(R.id.achieve_tab_title_1);
        tab_title2 = (TextView) v.findViewById(R.id.achieve_tab_title_2);
        tab_title3 = (TextView) v.findViewById(R.id.achieve_tab_title_3);
        tab_under1 = (ImageView) v.findViewById(R.id.achieve_tab_under_1);
        tab_under2 = (ImageView) v.findViewById(R.id.achieve_tab_under_2);
        tab_under3 = (ImageView) v.findViewById(R.id.achieve_tab_under_3);
        tab_title = new TextView[]{tab_title1, tab_title2, tab_title3};
        tab_under = new ImageView[]{tab_under1, tab_under2, tab_under3};
        add = (FloatingActionButton) v.findViewById(R.id.add_float);

        // OnClick Event
        tab1.setOnClickListener(Tab_Achieve.this);
        tab2.setOnClickListener(Tab_Achieve.this);
        tab3.setOnClickListener(Tab_Achieve.this);
        add.setOnClickListener(Tab_Achieve.this);

    }

    public void setTab(int tab) {
        for (int i = 0; i < 3; i++) {
            if (i == tab) {
                tab_title[i].setTextColor(getResources().getColor(R.color.sub_color));
                tab_under[i].setVisibility(View.VISIBLE);
            } else {
                tab_title[i].setTextColor(getResources().getColor(R.color.hurin_color));
                tab_under[i].setVisibility(View.INVISIBLE);
            }
        }
    }
    public void setAchievementsFromServer(int type) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.achieve_tab1:
                setTab(0);
                main_view.removeAllViews();
                setAchievementsFromServer(0);
                break;
            case R.id.achieve_tab2:
                setTab(1);
                main_view.removeAllViews();
                setAchievementsFromServer(1);
                break;
            case R.id.achieve_tab3:
                setTab(2);
                main_view.removeAllViews();
                setAchievementsFromServer(2);
                break;
        }
    }

}
