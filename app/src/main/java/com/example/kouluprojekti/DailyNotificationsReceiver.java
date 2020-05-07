package com.example.kouluprojekti;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
//Kevin Akkoyun
/**
 * Class that receives intent from DailyNotificationsStarter
 * Builds a notification based on the options in DailyNotifications class
 */
public class DailyNotificationsReceiver extends BroadcastReceiver {
    /**
     * Receives an intent from DailyNotificationStarter and sends a built notification.
     * Uses DailyNotifications to build a notification.
     * @param context Context used to create DailyNotifications which takes context as parameter
     * @param intent Given Intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        DailyNotifications notification = new DailyNotifications(context);
        NotificationCompat.Builder notificationBuilder = notification.getChannelNotification();
        notification.getManager().notify(1, notificationBuilder.build());
    }
}
