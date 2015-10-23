package kr.edcan.awardoftoday.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.List;

import kr.edcan.awardoftoday.R;
import kr.edcan.awardoftoday.data.Article;
import kr.edcan.awardoftoday.data.User;
import kr.edcan.awardoftoday.utils.DeveloperService;
import kr.edcan.awardoftoday.utils.NetworkService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by kotohana5706 on 2015. 8. 30..
 */
public class Tab_Achieve extends Fragment implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public String END_POINT;
    NetworkService service;
    RestAdapter restAdapter;
    LinearLayout tab1, tab2, tab3;
    TextView tab_title1, tab_title2, tab_title3, tab_title[], myStickerCount, achieveStickerCount;
    ImageView tab_under1, tab_under2, tab_under3, tab_under[];
    View v, cardview;
    RelativeLayout footer_left, footer_right;
    LinearLayout main_view;
    FloatingActionButton add;
    CardView achieve_cardview, no_content;
    LayoutInflater inflater;
    boolean isParent;
    MaterialDialog loading;
    String apikey, targetApikey, status[], token;
    int sticker;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.main_achieve, container, false);
        setRest();
        setDefault();
        setTab(0);
        return v;
    }

    private void setRest() {
        DeveloperService d = new DeveloperService(getContext());
        END_POINT = d.getEndpoint();
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .build();
        service = restAdapter.create(NetworkService.class);
    }

    private void setDefault() {
        status = new String[]{"working", "finished", "failed"};
        sharedPreferences = getContext().getSharedPreferences("AwardOfToday", 0);
        editor = sharedPreferences.edit();
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
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        isParent = sharedPreferences.getBoolean("isParent", false);
        apikey = sharedPreferences.getString("apikey", "");
        sticker = sharedPreferences.getInt("sticker", -1);
        targetApikey = sharedPreferences.getString("targetApikey", "");
        token = sharedPreferences.getString("token", "");
        myStickerCount = (TextView) v.findViewById(R.id.achieve_reward_my_sticker_count);
        // OnClick Event
        tab1.setOnClickListener(Tab_Achieve.this);
        tab2.setOnClickListener(Tab_Achieve.this);
        tab3.setOnClickListener(Tab_Achieve.this);
        add.setOnClickListener(Tab_Achieve.this);
        // if user is child
        if (!isParent) add.setVisibility(View.GONE);
        setAchievementsFromServer(0);
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

    public void setAchievementsFromServer(final int type) {
        loading = new MaterialDialog.Builder(getContext())
                .progress(true, 0)
                .title("데이터를 로드합니다")
                .content("잠시만 기다려주세요")
                .show();
        service.listArticle((isParent) ? targetApikey : apikey, status[type], new Callback<List<Article>>() {
            @Override
            public void success(List<Article> articles, Response response) {
                for (Article article : articles) {
                    // Create
                    achieve_cardview = (CardView) inflater.inflate(R.layout.achieve_cardview_layout, null);
                    final TextView cardview_title, cardview_duedate, cardview_sticker_count, cardview_content, cardview_articleKey;
                    cardview_title = (TextView) achieve_cardview.findViewById(R.id.achieve_cardview_title);
                    cardview_duedate = (TextView) achieve_cardview.findViewById(R.id.achieve_cardview_duedate);
                    cardview_sticker_count = (TextView) achieve_cardview.findViewById(R.id.achieve_cardview_stickercount);
                    cardview_content = (TextView) achieve_cardview.findViewById(R.id.achieve_cardview_content);
                    footer_left = (RelativeLayout) achieve_cardview.findViewById(R.id.achieve_cardview_footer_left);
                    footer_right = (RelativeLayout) achieve_cardview.findViewById(R.id.achieve_cardview_footer_right);
                    cardview_articleKey = (TextView) achieve_cardview.findViewById(R.id.achieve_cardview_articleKey);

                    if(isParent){
                        footer_right.setVisibility(View.GONE);
                        footer_left.setVisibility(View.GONE);
                    }
                    if(article.getWaiting() == true){
                        footer_left.setEnabled(false);
                        footer_right.setEnabled(false);
                    } else{
                        footer_left.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                service.finishArticle(token, targetApikey, cardview_articleKey.getText().toString(), new Callback<String>() {
                                    @Override
                                    public void success(String s, Response response) {
                                        Toast.makeText(getContext(), "부모님께 알림이 전송되었습니다! \n 부모님이 확인해 주실때까지 기다려주세요", Toast.LENGTH_SHORT).show();
                                        footer_left.setEnabled(false);
                                    }
                                    @Override
                                    public void failure(RetrofitError error) {
                                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        footer_right.setOnClickListener(Tab_Achieve.this);
                    }
                    // OnClick Event
                    cardview_title.setText(article.getTitle());
                    cardview_duedate.setText(article.getDate());
                    cardview_content.setText(article.getContent());
                    cardview_sticker_count.setText(article.getSticker() + "");
                    cardview_articleKey.setText(article.getArticleKey());
                    main_view.addView(achieve_cardview);
                }
                loading.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getResponse() != null) {
                    if (error.getResponse().getStatus() == 400) {
                        no_content = (CardView) inflater.inflate(R.layout.cardview_no_content, null);
                        ImageView imageView = (ImageView) no_content.findViewById(R.id.no_image);
                        switch (type) {
                            case 0:
                                imageView.setImageResource((isParent) ? R.drawable.no_homeworks : R.drawable.no_ami);
                                break;
                            case 1:
                                imageView.setImageResource(R.drawable.no_completed);
                                break;
                            case 2:
                                imageView.setImageResource(R.drawable.no_failed);
                                break;
                        }
                        main_view.addView(no_content);

                        loading.dismiss();
                    } else
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

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
            case R.id.achieve_cardview_footer_left:
                TextView asdf = (TextView) v.findViewById(R.id.achieve_cardview_articleKey);

                break;
            case R.id.achieve_cardview_footer_right:
                break;
            case R.id.add_float:
                startActivity(new Intent(getContext(), AddAchievementActivity.class));
        }
    }

    public void onResume() {
        super.onResume();
        service.getMyStatus(apikey, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                myStickerCount.setText(user.sticker+"");
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

}
