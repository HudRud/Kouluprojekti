package com.example.kouluprojekti;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    ArrayList<MedicationData> medList;
    ListView mMainList;
    SharedPreferences getPref;
    MedListAdapter adapter;
    AddMedicationData updater;
    Calendar calendar;
    private static final String PREFNAME = "medfile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);
        calendar = Calendar.getInstance();
        ImageButton calendarButton =findViewById(R.id.calendar_button);
        medList = new ArrayList<>();
        mMainList = findViewById(R.id.ListView1);
        getPref = getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);
        loadData();
        adapter = new MedListAdapter(this, R.layout.mednotelayout, medList);
        mMainList.setAdapter(adapter);
        updater = new AddMedicationData();
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "DatePicker");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        adapter.clear();
        adapter.addAll(medList);
        adapter.notifyDataSetChanged();
    }

    /**
     * Method that loads data from SharedPreferences and inserts it into medList Array
     * Used to prevent data loss
     */
    public void loadData() {
        Gson gson = new Gson();
        String json = getPref.getString("DATAJSON", null);
        Type type = new TypeToken<ArrayList<MedicationData>>() {
        }.getType();
        medList = gson.fromJson(json, type);
        if (medList == null) {
            medList = new ArrayList<>();
        }
    }

    /***
     * Saves data to SharedPreferences
     */
    public void saveData() {
        Gson gson = new Gson();
        SharedPreferences.Editor edit = getPref.edit();
        String json = gson.toJson(medList);
        edit.putString("DATAJSON", json);
        edit.commit();
    }

    /**
     * Creates a popup menu for selecting the type of notification added
     *
     * @param v View parameter used to bind the method to a View
     */
    public void showPopUp(View v) {
        PopupMenu pop = new PopupMenu(this, v);
        pop.setOnMenuItemClickListener(this);
        pop.inflate(R.menu.popupmenu);
        pop.show();
    }
    public void addMedicationNoti(View v){
        Intent intent = new Intent(MainActivity.this,AddMedicationData.class);
        startActivity(intent);
    }

    /***
     * Method that starts an activity depending on button pressed
     * @param item Menu item that is pressed
     * @return returns true
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.settings:
                intent = new Intent(MainActivity.this, SettingsPage.class);
                startActivity(intent);
                break;
            case R.id.notification:
                intent = new Intent(MainActivity.this, DailyNotificationStarter.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    /**
     * OnDateSet uses DatePickerFragment to get the selected date
     * When the date is selected onDateSet saves the selected date to a calendar instance
     * Then it calls TimePicker
     *
     * @param view  Binds the method to a view
     * @param year  Gets the year from the DatePicker
     * @param month Gets the month from the DatePicker (Note: Calendar instance starts from 0)
     * @param day Gets the day from the DatePicker
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "TimePicker");
    }
    /**
     * OnTimeSet uses TimePickerFragment to get the selected time
     * After the time is selected Date and Time strings are created for the ListView adapter
     * The the alarm is set to the specified calendar instance
     * After that it creates aditional two strings and saves the to SharedPreferences to be use on the notification itself
     * Lastly it creates a toast for the user to see what date the alarm is set
     * @param view Binds the method to the view
     * @param hour Gets the hour from TimePicker
     * @param minute Gets the minutes from TimePicker
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        String date = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());

        String timeString = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        loadData();
        medList.add(new MedicationData("Appointment", timeString, false, date));
        saveData();
        adapter.clear();
        adapter.addAll(medList);
        adapter.notifyDataSetChanged();
        createAlarm(calendar);
        String alarmStringDate = calendar.get(Calendar.DAY_OF_MONTH) + "." + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR);
        String alarmStringTime = calendar.get(Calendar.HOUR_OF_DAY) + "." + calendar.get(Calendar.MINUTE);
        SharedPreferences prefPut = getSharedPreferences("AlarmString",Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = prefPut.edit();
        prefEdit.putString("alarmDate", alarmStringDate);
        prefEdit.putString("alarmTime", alarmStringTime);
        prefEdit.commit();
        Toast.makeText(getApplicationContext(),"Alarm set on: " + alarmStringDate
                + "\nOn: " + alarmStringTime,Toast.LENGTH_LONG).show();
    }

    /**
     * 
     * CreateAlarm is the method that sets the alarm to happen at the specified time
     * It creates a intent that calls BroadcastManager and gives that intent to a PendingIntent to be called later
     * alarmManager.setExact calls the pendingIntent it is given when the specified time is right
     * RTC_WAKEUP allows this to happen even if the phone is sleeping
     * @param calendar Calendar instance used to set the alarm time
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, BroadcastManager.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        assert alarmManager != null;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
