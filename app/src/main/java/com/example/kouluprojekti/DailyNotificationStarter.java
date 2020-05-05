package com.example.kouluprojekti;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;

public class DailyNotificationStarter extends AppCompatActivity {

    private static final String DATAFILE = "datafile";

    private static final String SWITCHSTATE = "switchCheck";

    private static final String ALARMSTATE = "alarmCheck";

    private static final String TIMEHOUR = "hourCheck";

    private static final String TIMEMINUTE = "minuteCheck";

    private boolean switchState;

    private boolean alarmState;

    private int hours, minutes;

    java.util.Calendar c;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.daily_notification_settings);

        getPrefData();

        setTimePicker();

        setSwitchState(switchState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onPause() {
        super.onPause();

        getTimePicker();

        notificationState(switchState,alarmState);

        setPrefData();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();

        getPrefData();

        setTimePicker();

        setSwitchState(switchState);
    }

    private void setAlarmState(boolean alarmState) {

        this.alarmState = alarmState;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setTimePicker() {

        TimePicker t = findViewById(R.id.dailyTimePicker);

        t.setIs24HourView(true);

        t.setHour(hours);

        t.setMinute(minutes);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getTimePicker() {

        TimePicker t = findViewById(R.id.dailyTimePicker);

        hours = t.getHour();

        minutes = t.getMinute();
    }


    private void getPrefData() {

        SharedPreferences getPref = getSharedPreferences(DATAFILE, Activity.MODE_PRIVATE);

        switchState = getPref.getBoolean(SWITCHSTATE, false);

        alarmState = getPref.getBoolean(ALARMSTATE, false);

        hours = getPref.getInt(TIMEHOUR, 0);

        minutes = getPref.getInt(TIMEMINUTE, 0);
    }

    private void setPrefData() {
        SharedPreferences setPref = getSharedPreferences(DATAFILE, Activity.MODE_PRIVATE);

        SharedPreferences.Editor editPref = setPref.edit();

        editPref.putBoolean(SWITCHSTATE, switchState);

        editPref.putBoolean(ALARMSTATE, alarmState);

        editPref.putInt(TIMEHOUR, hours);

        editPref.putInt(TIMEMINUTE, minutes);

        editPref.commit();
    }

    private void setSwitchState(boolean b) {
        Switch s = findViewById(R.id.toggleDaily);

        s.setChecked(b);
    }

    public void onSwitchFlip(View v) {

        Switch s = findViewById(R.id.toggleDaily);

        switchState = s.isChecked();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void calendarSetUp() {

        TimePicker t = findViewById(R.id.dailyTimePicker);

        c = java.util.Calendar.getInstance();

        c.set(java.util.Calendar.HOUR_OF_DAY, t.getHour());

        c.set(java.util.Calendar.MINUTE, t.getMinute());
    }

    private void startNotifications(java.util.Calendar c) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this, DailyNotificationsReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 60000 * 60 * 24, pendingIntent);

        setAlarmState(true);

    }

    private void cancelNotifications() {

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this, DailyNotificationsReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);

        setAlarmState(false);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void notificationState(boolean switchState, boolean alarmState) {

        if (switchState && !alarmState) {

            calendarSetUp();

            startNotifications(c);

        } else if (!switchState && alarmState) {

            cancelNotifications();

        }
    }

}
