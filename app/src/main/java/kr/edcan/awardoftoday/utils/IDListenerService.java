package kr.edcan.awardoftoday.utils;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Junseok on 2015-10-22.
 */
public class IDListenerService extends InstanceIDListenerService {

    private static final String TAG = "MyInstanceIDLS";

    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, RegisterationIntentService.class);
        startService(intent);
    }
}