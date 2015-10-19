package kr.edcan.awardoftoday.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import kr.edcan.awardoftoday.R;

/**
 * Created by kotohana5706 on 2015. 8. 30..
 */
public class Tab_Home extends Fragment {
    LinearLayout HomeView;
    SharedPreferences sharedPreferences;
    int sharedCount;
    boolean byTime = false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_home, container, false);
        HomeView = (LinearLayout) v.findViewById(R.id.main_home_sticker_panel);
        int width = HomeView.getLayoutParams().width;
        Toast.makeText(getContext(), width+"", Toast.LENGTH_SHORT).show();
        HomeView.setLayoutParams(new FrameLayout.LayoutParams(width, width));
//        setData(v);
        return v;
    }

//    public void setData(View v) {
//        sharedPreferences = getContext().getSharedPreferences("AwardOfToday", 0);
//        sharedCount = sharedPreferences.getInt("count", 0);
//        byTime = sharedPreferences.getBoolean("byTime", false);
//        HomeView = (LinearLayout) v.findViewById(R.id.home_layout);
//        LayoutInflater cardInflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        if (sharedCount == 0) {
//            TextView noText = (TextView) v.findViewById(R.id.no_goal_text);
//            noText.setVisibility(View.VISIBLE);
//        } else if(byTime){
//            for (int i = 1; i <= sharedCount-1; i++) {
//                CardView route_info_tab = (CardView) cardInflator.inflate(R.layout.achieve_cardview_layout, null);
//                TextView title = (TextView) route_info_tab.findViewById(R.id.card_title);
//                TextView content = (TextView) route_info_tab.findViewById(R.id.card_description);
//                TextView reward = (TextView) route_info_tab.findViewById(R.id.card_reward);
//                title.setText(sharedPreferences.getString("title" + i, ""));
//                content.setText(sharedPreferences.getString("content" + i, ""));
//                reward.setText(sharedPreferences.getInt("reward" + i, 0)+"개");
//                HomeView.addView(route_info_tab);
//            }
//        } else {
//            for (int i = sharedCount-1;i>=1; i--) {
//                CardView route_info_tab = (CardView) cardInflator.inflate(R.layout.achieve_cardview_layout, null);
//                TextView title = (TextView) route_info_tab.findViewById(R.id.card_title);
//                TextView content = (TextView) route_info_tab.findViewById(R.id.card_description);
//                TextView reward = (TextView) route_info_tab.findViewById(R.id.card_reward);
//                title.setText(sharedPreferences.getString("title" + i, ""));
//                content.setText(sharedPreferences.getString("content" + i, ""));
//                reward.setText(sharedPreferences.getInt("reward" + i, 0)+"개");
//                HomeView.addView(route_info_tab);
//            }
//        }
//    }
}
