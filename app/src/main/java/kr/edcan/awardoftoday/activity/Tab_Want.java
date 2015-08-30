package kr.edcan.awardoftoday.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.edcan.awardoftoday.R;

/**
 * Created by kotohana5706 on 2015. 8. 30..
 */
public class Tab_Want extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.main_want,container,false);
        return v;
    }
}
