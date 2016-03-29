package com.example.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.gcm.db.DBManager;
import com.example.gcm.db.DBSqlData;
import com.example.gcm.db.PushMsgData;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by namjinha on 2016-02-03.
 */
public class ExGcmListenerService extends GcmListenerService
{
    @Override
    public void onMessageReceived(String from, Bundle data)
    {
        String message = data.getString("message");

        PushMsgData pData = new PushMsgData(message);

        DBManager dbMgr = new DBManager(this);
        dbMgr.dbOpen();
        dbMgr.insertData(DBSqlData.SQL_DB_INSERT_DATA, pData);
        dbMgr.dbClose();

        sendNotification(message);
    }

    /**
     * Notification bar를 통해서 알림을 보여준다.
     */
    private void sendNotification(String message)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("GCM Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
