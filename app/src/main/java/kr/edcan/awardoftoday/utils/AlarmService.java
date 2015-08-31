package kr.edcan.awardoftoday.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import kr.edcan.awardoftoday.R;

/**
 * Created by Junseok on 2015-08-31.
 */
public class AlarmService extends BroadcastReceiver {
    String title, content;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("asdf", "receive");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_prize_on)
                        .setContentTitle(title)
                        .setContentText(content);
        int mNotificationId = 001;
        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
