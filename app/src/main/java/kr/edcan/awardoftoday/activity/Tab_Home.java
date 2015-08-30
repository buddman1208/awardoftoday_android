package kr.edcan.awardoftoday.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import kr.edcan.awardoftoday.R;

/**
 * Created by kotohana5706 on 2015. 8. 30..
 */
public class Tab_Home extends Fragment {
    LinearLayout HomeView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_home, container, false);
        HomeView = (LinearLayout) v.findViewById(R.id.home_layout);
        LayoutInflater cardInflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < 20; i++) {
            CardView route_info_tab = (CardView) cardInflator.inflate(R.layout.cardview_layout, null);
            HomeView.addView(route_info_tab);
        }
        return v;
    }
}
