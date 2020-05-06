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
 * NotificationBuilder classissa määritetään minkä lainen ilmoitus halutaan luoda kun on aika luoda sellainen
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
     * NotificationChannel metodi luo itsessään ilmoituksen ja määrittää sille haluttuja lupia,
     * kuten lupa värisyttää puhelinta
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
     * Jos notificationmanageria ei ole luo sellainen
     * @return notificationmanager
     */
    public NotificationManager notManager(){
        if(notManager == null){
            notManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notManager;
    }

    /**
     * Annetaan ilmoitukselle siihen halutut tekstit ja kuva
     * @return
     */
    public NotificationCompat.Builder notChannel(){
        SharedPreferences prefGet = getSharedPreferences("AlarmString", Activity.MODE_PRIVATE);
        String alarmStringDate = prefGet.getString("alarmDate", "1.1.1970");
        String alarmStringTime = prefGet.getString("alarmTime", "12.00");
        return new NotificationCompat.Builder(getApplicationContext(),Id)
                .setContentText("Doctor's appointment on the " + alarmStringDate + " at " + alarmStringTime)
                .setContentTitle(getString(R.string.remind))
                .setSmallIcon(R.mipmap.ic_launcher_round);
    }
}
