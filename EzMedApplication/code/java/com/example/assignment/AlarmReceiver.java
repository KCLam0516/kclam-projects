package com.example.assignment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;


public class AlarmReceiver extends BroadcastReceiver {
    DatabaseHelper myDb;

    private NotificationManager mNotificationManager;
    // Notification ID.
    private static final int NOTIFICATION_ID = 0;
    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

//    Cursor res = myDb.getReminderData();
//    String text = res.getString(1);


    @Override
    public void onReceive(Context context, Intent intent) {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        deliverNotification(context);
    }

    private void deliverNotification(Context context) {

        Intent contentIntent = new Intent(context, MainActivity.class);

        PendingIntent contentPendingIntent = PendingIntent.getBroadcast
                (context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.cover)
                .setContentTitle("EzMed Reminder")
                .setContentText("Remember To Bring Mask And Hand Sanitizer")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        // Deliver the notification
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}