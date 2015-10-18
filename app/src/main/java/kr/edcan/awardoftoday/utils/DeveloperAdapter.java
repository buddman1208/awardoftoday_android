package kr.edcan.awardoftoday.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.edcan.awardoftoday.R;
import kr.edcan.awardoftoday.data.DeveloperInfo;

/**
 * Created by kotohana5706 on 15. 7. 13.
 */
public class DeveloperAdapter extends ArrayAdapter<DeveloperInfo> {
    private LayoutInflater mInflater;

    public DeveloperAdapter(Context context, ArrayList<DeveloperInfo> object) {
        super(context, 0, object);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        View view = null;
        // 현재 리스트의 하나의 항목에 보일 컨트롤 얻기
        if (v == null) {
            // XML 레이아웃을 직접 읽어서 리스트뷰에 넣음
            view = mInflater.inflate(R.layout.listview_developer_info, null);
        } else {
            view = v;
        }
        // 자료를 받는다.
        final DeveloperInfo data = this.getItem(position);
        if (data != null) {
            //화면 출력
            TextView title = (TextView) view.findViewById(R.id.developer_info_title);
            TextView description = (TextView) view.findViewById(R.id.developer_info_description);
            if(data.getTitle().trim().equals("")) {
                title.setVisibility(View.GONE);
            }
            else title.setText(data.getTitle());
            description.setText(data.getDescription());
        }
        return view;
    }
}
