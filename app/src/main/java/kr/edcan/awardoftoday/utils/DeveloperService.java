package kr.edcan.awardoftoday.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Junseok on 2015-10-14.
 */
public class DeveloperService {
    private String Endpoint;
    public DeveloperService(Context c){
        SharedPreferences sharedPreferences = c.getSharedPreferences("AwardOfToday", 0);
        this.Endpoint = sharedPreferences.getString("ip", "");
    }
    public String getEndpoint(){
        return Endpoint;
    }
}
