package com.example.kouluprojekti;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.core.app.NotificationCompat;

/**
 * NotificationBuilder creates notifications and creates the needed channels for them
 */
public class NotificationBuilder extends ContextWrapper {
    public static String Id = "Id";
    private NotificationManager notManager;

    public NotificationBuilder(Context base){
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationChannel();
        }
    }

    /**
     * NotificationChannel method creates a channel for the notification and sets permissions for it
     */
    @TargetApi(Build.VERSION_CODES.O)
    public void notificationChannel(){
        NotificationChannel channel = new NotificationChannel(Id,"Channel",NotificationManager.IMPORTANCE_DEFAULT);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        channel.enableVibration(true);
        channel.enableLights(true);
        channel.setLightColor(R.color.colorPrimary);
        notManager().createNotificationChannel(channel);
    }

    /**
     * Creates NotificationManager if one is not created
     * @return  returns the NotificationManager for later use
     */
    public NotificationManager notManager(){
        if(notManager == null){
            notManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notManager;
    }

    /**
     * NotificationCompat.Builder creates the notification
     * Date and Time variables are taken from SharedPreferences and used to create the ContentText
     * Icon for the notification is taken from the Drawables
     * @return returns the notification builder
     */
    public NotificationCompat.Builder notChannel(){
        SharedPreferences prefGet = getSharedPreferences("AlarmString", Activity.MODE_PRIVATE);
        String alarmStringDate = prefGet.getString("alarmDate", "1.1.1970");
        String alarmStringTime = prefGet.getString("alarmTime", "12.00");
        return new NotificationCompat.Builder(getApplicationContext(),Id)
                .setContentText("Doctor's appointment on the " + alarmStringDate + " at " + alarmStringTime)
                .setContentTitle(getString(R.string.remind))
                .setSmallIcon(R.drawable.ic_templogo);
    }
}
