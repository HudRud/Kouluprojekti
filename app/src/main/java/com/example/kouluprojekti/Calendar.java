package com.example.kouluprojekti;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Calendar extends AppCompatActivity {
    private DatePicker picker;
    private String date;
    private String alarmDate;
    private String time;
    private String alarmTime;
    private int hours;
    private int minutes;
    SharedPreferences prefDate;
    private Spinner hour;
    private Spinner minute;
    String[] spinnerHours = new String[]{"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
    String[] spinnerMinutes = new String[]{"00","10","20","30","40","50"};
    private ArrayList<NotificationArray> notificationList;

    /**
     * OnCreate() metodissa määritellään tarvittavia muuttujia ja widgettejä.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.calendarr);
        Button button = findViewById(R.id.button);
        hour = findViewById(R.id.hour);
        minute = findViewById(R.id.minute);
        ArrayAdapter<String> hourAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerHours);
        ArrayAdapter<String> minuteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerMinutes);
        hour.setAdapter(hourAdapter);
        minute.setAdapter(minuteAdapter);
        /**
         * button.setOnClickListener luo activityn nappiin tapahtumia.
         * Nappia painettaessa ohjelma ottaa activityn DatePickeristä valitun päivän, Spinnereistä ajan ja luo molemmista Stringin
         * Luodut Stringit tallennetaan SharedPrefrenceihin
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = picker.getDayOfMonth();
                int month = picker.getMonth();
                int year = picker.getYear();
                String alarmHour = hour.getSelectedItem().toString();
                String alarmMinute = minute.getSelectedItem().toString();
                alarmDate = day + "." + (month+1) + "." + year;
                alarmTime = alarmHour + "." + alarmMinute;
                prefDate = getSharedPreferences("AlarmDates", Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefDate.edit();
                prefEditor.putString("SetDate",alarmDate);
                prefEditor.putString("SetTime",alarmTime);
                prefEditor.apply();
            }
        });
        picker = (DatePicker)findViewById(R.id.datePicker);
        /**
         * Tässä tallenetaan systeemin päivämäärä ja luodaan siitä String.
         * Samalla myös ajasta luodaan String.
         */
        final java.util.Calendar calendar = java.util.Calendar.getInstance();
        int day = calendar.get(java.util.Calendar.DATE);
        int month = calendar.get(java.util.Calendar.MONTH);
        int year = calendar.get(java.util.Calendar.YEAR);
        date = day + "." + (month +1) + "." + year;
        hours = calendar.get(java.util.Calendar.HOUR_OF_DAY);
        minutes = calendar.get(java.util.Calendar.MINUTE);
        time = hours + "." + minutes;

        /**
         * Timer tarkistaa määritetyn ajan jälkeeen kutsuu run() metodia jossa tarkistetaan
         * onko aika lähettää ilmoitus käyttäjälle.
         * Timer pyörii laitteen taustalla vaikka sovellus olisi suljettu.
         */
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(Timer_Tick);
                final java.util.Calendar tick = java.util.Calendar.getInstance();
                hours = tick.get(java.util.Calendar.HOUR_OF_DAY);
                minutes = tick.get(java.util.Calendar.MINUTE);
                time = hours + "." + minutes;
                Log.d("TickTime", time);
            }
        }, 0,30000);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
    }

    /**
     * addNotification() metodi luo halutun ilmoituksen, luo sille ilmoitus kanavan ja lähettää ilmoituksen käyttäjälle kun sitä kutsutaan:
     * NotificationCompat.Builder constructorilla luodaan halutun lainen ilmoitus jolle myöhemmin annetaan PendingIntent sen lähettämistä varten.
     * Sen jälkeen luodaan NotificationManager joka luo kanavan jota kautta ilmoitus voidaan lähettää.
     * Luotu kanava annetaan erikseen ilmoitukselle määritetylle channelId:lle
     * ja viimeisenä kutsutaan NotificationManagerin läpi notify() metodilla Notification Builderia joka lähettää ilmoituksen.
     */
    private void addNotification(){
        String info = getString(R.string.notificationInfo);
        String text = getString(R.string.notificationText1);
        Intent intent = new Intent(this, AlertDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"notify_001")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentInfo(info)
                .setContentText(text + alarmTime)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String channelId = "notify_001";
            NotificationChannel channel = new NotificationChannel(channelId,"Title", NotificationManager.IMPORTANCE_DEFAULT);
            assert manager != null;
            manager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }
        assert manager != null;
        manager.notify(0,builder.build());
    }

    /**
     * Seuraavassa metodissa määritellään mitä Timer_Tick kutsuu kun sille märitelty aika on kulunut.
     */
    private final Runnable Timer_Tick = new Runnable() {
        @Override
        public void run() {
            Log.d("Log","Viesti");
            prefDate = getSharedPreferences("AlarmDates",Activity.MODE_PRIVATE);
            alarmDate = prefDate.getString("SetDate","No date set.");
            alarmTime = prefDate.getString("SetTime","No time set");
            Log.d("Check", alarmDate);
            Log.d("Check",date);
            Log.d("Check", alarmTime);
            if(time.length() == 1){
                time = "0" + time;
            }
            Log.d("Check", time);
            if(date.equals(alarmDate) && time.equals(alarmTime)){
                addNotification();
            }
        }
    };
    private void NotificationList(){
        prefDate = getSharedPreferences("AlarmDates",Activity.MODE_PRIVATE);
        alarmDate = prefDate.getString("SetDate","1.1.1970");
        alarmTime = prefDate.getString("SetTime","12.00");
        notificationList = new ArrayList<NotificationArray>();
    }

}
