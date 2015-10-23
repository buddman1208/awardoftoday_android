package kr.edcan.awardoftoday.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import kr.edcan.awardoftoday.R;
import kr.edcan.awardoftoday.activity.GetIPActivity;
import kr.edcan.awardoftoday.activity.ParentConfirmActivity;

/**
 * Created by Junseok on 2015-10-22.
 */
public class GcmListenerService extends com.google.android.gms.gcm.GcmListenerService {

    public String TAG = "GCM";

    public String articleTitle, articleContent, articleKey;
    public String alertTitle, alertMessage;
    /**
     * @param from SenderID 값을 받아온다.
     * @param data Set형태로 GCM으로 받은 데이터 payload이다.
     */
    @Override
    public void onMessageReceived(String from, Bundle data) {
        switch(data.getString("type")){
            case "toParent":
                articleKey = data.getString("articleKey");
                articleTitle = data.getString("articleTitle");
                articleContent= data.getString("articleContent");
                sendNotificationToParent(articleTitle);
                break;
            case "toChild":
                alertTitle = data.getString("title");
                alertMessage = data.getString("message");
                sendNotificationToChild(articleTitle);

        }

    }

    private void sendNotificationToChild(String articleTitle) {
        Intent intent = new Intent(this, GetIPActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("오늘의 어린이상!")
                .setContentText(articleTitle)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1/* ID of notification */, notificationBuilder.build());
    }


    private void sendNotificationToParent(String title) {
        Intent intent = new Intent(this, ParentConfirmActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("title", articleTitle);
        intent.putExtra("content", articleContent);
        intent.putExtra("articlekey", articleKey);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("오늘의 어린이상!")
                .setContentText("자녀가 " + title + " 과제를 완료했습니다!")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
