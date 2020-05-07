package com.example.kouluprojekti;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
//Henri Hyytiä
/**
 * BroadcastManager sends the notification
 */
public class BroadcastManager extends BroadcastReceiver {
    /**
     * BroadcastManager uses the context and intent it is given to send the notification
     * @param context Context given to the BroadcastReceiver
     * @param intent Intent given to the BroadcastReceiver
     */
    @Override
    public void onReceive(Context context, Intent intent){
        NotificationBuilder notificationBuilder = new NotificationBuilder(context);
        NotificationCompat.Builder builder = notificationBuilder.notChannel();
        notificationBuilder.notManager().notify(1,builder.build());
    }
}
