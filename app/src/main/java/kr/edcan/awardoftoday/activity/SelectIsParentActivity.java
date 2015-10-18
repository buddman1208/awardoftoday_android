package kr.edcan.awardoftoday.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import kr.edcan.awardoftoday.R;

/**
 * Created by Junseok Oh on 2015-10-11.
 */
public class SelectIsParentActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView child, parent;
    Bundle extra;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_is_parent);
        setDefault();
    }

    private void setDefault() {
        extra = new Bundle();
        intent = new Intent();
        child = (ImageView) findViewById(R.id.select_child);
        parent = (ImageView) findViewById(R.id.select_parent);
        child.setOnClickListener(this);
        parent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_parent:
                extra.putBoolean("isParent", true);
                intent.putExtras(extra);
                this.setResult(RESULT_OK, intent);
                this.finish();
                break;
            case R.id.select_child:
                extra.putBoolean("isParent", false);
                intent.putExtras(extra);
                this.setResult(RESULT_OK, intent);
                this.finish();
                break;
        }
    }
}
