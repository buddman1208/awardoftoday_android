package kr.edcan.awardoftoday.activity;


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

    View v;
    LinearLayout main_view;
    FloatingActionButton add;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_achieve, container, false);
        this.v = v;
        setDefault();
        return v;
    }

    private void setDefault() {
        add = (FloatingActionButton)v.findViewById(R.id.add_float);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_view = (LinearLayout)Tab_Achieve.this.v.findViewById(R.id.achieve_view_layout);
                main_view.removeAllViews();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
