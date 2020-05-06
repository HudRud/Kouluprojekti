package com.example.kouluprojekti;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class BroadcastManager extends BroadcastReceiver {
    /**
     * BroadcastReceiver ottaa sitä kutsuttaessa itselleen syötetyn contextin ja intentin ja käyttää niitä
     * lähettämään ilmoituksen
     * @param context context joka broadcastmanagerille annettiin
     * @param intent intent joka broadcastmanagerille annettiin
     */
    @Override
    public void onReceive(Context context, Intent intent){
        NotificationBuilder notificationBuilder = new NotificationBuilder(context);
        NotificationCompat.Builder builder = notificationBuilder.notChannel();
        notificationBuilder.notManager().notify(1,builder.build());
    }
}
