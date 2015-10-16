package kr.edcan.awardoftoday.activity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;

import kr.edcan.awardoftoday.R;


/**
 * Created by kotohana5706 on 2015. 8. 30..
 */
public class Tab_Achieve extends Fragment implements View.OnClickListener {
    LinearLayout AchieveView;
    FloatingActionButton addAchieve;
    boolean byTime = false;
    SharedPreferences sharedPreferences;
    int sharedCount;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_achieve, container, false);
//        AchieveView = (LinearLayout) v.findViewById(R.id.achieve_layout);
//        addAchieve = (FloatingActionButton) v.findViewById(R.id.add_achieve);
//        addAchieve.setOnClickListener(this);
//        setData(v);
        return v;
    }

//    public void setData(View v) {
//        sharedPreferences = getContext().getSharedPreferences("AwardOfToday", 0);
//        sharedCount = sharedPreferences.getInt("count", 0);
//        byTime = sharedPreferences.getBoolean("byTime", false);
//        AchieveView = (LinearLayout) v.findViewById(R.id.achieve_layout);
//        LayoutInflater cardInflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        if (sharedCount == 0) {
//            TextView noText = (TextView) v.findViewById(R.id.no_goal_text);
//            noText.setVisibility(View.VISIBLE);
//        } else if(byTime){
//            for (int i = 1; i <= sharedCount-1; i++) {
//                CardView route_info_tab = (CardView) cardInflator.inflate(R.layout.cardview_layout, null);
//                TextView title = (TextView) route_info_tab.findViewById(R.id.card_title);
//                TextView content = (TextView) route_info_tab.findViewById(R.id.card_description);
//                TextView reward = (TextView) route_info_tab.findViewById(R.id.card_reward);
//                title.setText(sharedPreferences.getString("title" + i, ""));
//                content.setText(sharedPreferences.getString("content" + i, ""));
//                reward.setText(sharedPreferences.getInt("reward" + i, 0)+"개");
//                AchieveView.addView(route_info_tab);
//            }
//        } else {
//            for (int i = sharedCount-1;i>=1; i--) {
//                CardView route_info_tab = (CardView) cardInflator.inflate(R.layout.cardview_layout, null);
//                TextView title = (TextView) route_info_tab.findViewById(R.id.card_title);
//                TextView content = (TextView) route_info_tab.findViewById(R.id.card_description);
//                TextView reward = (TextView) route_info_tab.findViewById(R.id.card_reward);
//                title.setText(sharedPreferences.getString("title" + i, ""));
//                content.setText(sharedPreferences.getString("content" + i, ""));
//                reward.setText(sharedPreferences.getInt("reward" + i, 0)+"개");
//                AchieveView.addView(route_info_tab);
//            }
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.add_achieve:
//                startActivity(new Intent(getContext(), AddAchievementActivity.class));
//                break;
        }
    }
}
