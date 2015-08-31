package kr.edcan.awardoftoday.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import kr.edcan.awardoftoday.R;

/**
 * Created by kotohana5706 on 2015. 8. 30..
 */
public class Tab_Home extends Fragment {
    LinearLayout HomeView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int sharedCount;

    //    editor.putString("title"+sharedCount, getTitle);
//    editor.putString("content" + sharedCount, getContent);
//    editor.putString("when"+sharedCount, year+"."+month+"."+day+"."+hour+"."+minute);
//    editor.putInt("reward"+sharedCount, reward);
//    editor.putInt("count", sharedCount++);
//    editor.commit();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_home, container, false);
        setData(v);
        return v;
    }

    public void setData(View v) {
        sharedPreferences = getContext().getSharedPreferences("AwardOfToday", 0);
        sharedCount = sharedPreferences.getInt("count", 0);
        HomeView = (LinearLayout) v.findViewById(R.id.home_layout);
        LayoutInflater cardInflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Toast.makeText(getContext(), sharedCount+"개", Toast.LENGTH_SHORT).show();
        if (sharedCount == 0) {
            TextView noText = (TextView) v.findViewById(R.id.no_goal_text);
            noText.setVisibility(View.VISIBLE);
        } else {
            for (int i = 1; i <= sharedCount-1; i++) {
                CardView route_info_tab = (CardView) cardInflator.inflate(R.layout.cardview_layout, null);
                TextView title = (TextView) route_info_tab.findViewById(R.id.card_title);
                TextView content = (TextView) route_info_tab.findViewById(R.id.card_description);
                TextView reward = (TextView) route_info_tab.findViewById(R.id.card_reward);
                title.setText(sharedPreferences.getString("title" + i, ""));
                content.setText(sharedPreferences.getString("content" + i, ""));
                reward.setText(sharedPreferences.getInt("reward" + i, 0)+"개");
                HomeView.addView(route_info_tab);
            }
        }
    }
}
