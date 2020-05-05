package com.example.kouluprojekti;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class DailyNotificationsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DailyNotifications notification = new DailyNotifications(context);
        NotificationCompat.Builder notificationBuilder = notification.getChannelNotification();
        notification.getManager().notify(1,notificationBuilder.build());
    }
}
