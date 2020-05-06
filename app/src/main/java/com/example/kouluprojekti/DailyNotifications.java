package com.example.kouluprojekti;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class DailyNotifications extends ContextWrapper {
    public static final String CHANNELID = "Channel_1";
    public static final String CHANNELNAME = "dailyChannel";

    private NotificationManager manager;

    /***
     * Starts the process of building a notification only if android version is at least Oreo
     * @param base Context parameter given to the Superclass
     */
    public DailyNotifications(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    /***
     * Creates a notification channel via given Channel ID and Channel Name.
     * Calls for getManager() to create the notification channel
     */
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNELID, CHANNELNAME, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    /***
     * Method for fetching a notification manager, creates a new manager if needed.
     * @return Returns a NotificationManager
     */
    public NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    /***
     * Creates the notification based on given options
     * @return Returns new set of options which the NotificationCompat.Builder uses to build a notification
     */
    public NotificationCompat.Builder getChannelNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), CHANNELID)
                .setContentTitle("Daily reminder")
                .setContentText("Remember to take your daily medication")

                //Insert the name of notification logo for the daily notifications below to "R.drawable. "
                .setSmallIcon(R.drawable.ic_one);

    }
}
