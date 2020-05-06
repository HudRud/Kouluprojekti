package com.example.kouluprojekti;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddMedicationData extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private static final String PREFNAME = "medfile";
    private static final String DATA = "DATAJSON";
    TextView inputField;
    TextView timeText;
    int hourOfDay, minute;
    MedicationData addMed;
    SharedPreferences setPref;
    ArrayList<MedicationData> medList;
    String medName;
    String timeString;
    Calendar c;
/*
This class handles SharedPreferences and data on the medList Array
use the PREFNAME string when using getSharedPreferences
to access the json string that contains notification data, use DATA string
Remember to fetch current json string and formatting it to ArrayList with Gson when manipulating Array
This is to provide accuracy for the json and to prevent data loss
 */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setPref = getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);

        setContentView(R.layout.medicationdatainsert);

        fetchExistingData();
    }

    /***
     * Method that shows the TimePicker
     * @param v View parameter used to bind the method to a View
     */
    public void timePickerButton(View v) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "Set time");
    }

    /***
     * Method that Checks that neither TextViews are empty and starts the Note creating process
     * @param v View parameter used to bind the method to a View
     */
    public void addButton(View v) {

        inputField = findViewById(R.id.editText1);

        timeText = findViewById(R.id.timetext);

        if (!inputField.getText().equals("") && !timeText.getText().equals("")) {

            saveMedData();
        }
    }

    /**
     * Method for saving inserted data from medicationdatainsert activity
     */
    public void saveMedData() {
        SharedPreferences.Editor editor = setPref.edit();

        c = Calendar.getInstance();

        String date = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());

        medName = inputField.getText().toString();

        addMed = new MedicationData(medName, timeString, false, date);

        Gson gson = new Gson();

        medList.add(addMed);

        String json = gson.toJson(medList);

        editor.putString(DATA, json);

        editor.commit();

        finish();
    }

    /**
     * Method for fetching data from SharedPreferences. Used to prevent data loss
     */
    private void fetchExistingData() {
        Gson gson = new Gson();

        String json = setPref.getString(DATA, null);

        Type type = new TypeToken<ArrayList<MedicationData>>() {
        }.getType();

        medList = gson.fromJson(json, type);
        if (medList == null) {
            medList = new ArrayList<>();
        }

    }

    /**
     * Method for MedListAdapter to access SharedPreferences and deleting an item in the medList array
     *
     * @param context Context given from non-Activity class
     * @param index   index of the specified ArrayList item
     */
    public void logger(Context context, int index, boolean check) {
        Gson gson = new Gson();

        SharedPreferences getPref = context.getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);

        String json = getPref.getString(DATA, null);

        Type type = new TypeToken<ArrayList<MedicationData>>() {
        }.getType();

        medList = gson.fromJson(json, type);
        if (medList == null) {
            medList = new ArrayList<>();
        }
        Calendar c = Calendar.getInstance();

        medList.get(index).setDate(DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime()));
        Log.d("status", medList.get(index).getDate());
        medList.get(index).setChecked(check);

        json = gson.toJson(medList);

        SharedPreferences.Editor editor = getPref.edit();

        editor.putString(DATA, json);

        editor.commit();

    }

    /***
     * Removes item from the SharedPreferences
     * Method is used outside of the original Activity class
     * @param context Context parameter given from outside the activity class
     * @param index index of the ArrayList item
     */
    public void removeItem(Context context, int index) {

        Gson gson = new Gson();

        SharedPreferences getPref = context.getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);

        String json = getPref.getString(DATA, null);

        Type type = new TypeToken<ArrayList<MedicationData>>() {
        }.getType();

        medList = gson.fromJson(json, type);
        if (medList == null) {
            medList = new ArrayList<>();
        }
        medList.remove(index);

        json = gson.toJson(medList);

        SharedPreferences.Editor editor = getPref.edit();

        editor.putString(DATA, json);

        editor.commit();


    }

    /**
     * Method for accessing sharedPreferences outside of the activity class
     *
     * @param context Context given from outside the activity
     */
    public void loadData(Context context) {
        Gson gson = new Gson();
        SharedPreferences getPref = context.getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);
        String json = getPref.getString("DATAJSON", null);

        Type type = new TypeToken<ArrayList<MedicationData>>() {
        }.getType();

        medList = gson.fromJson(json, type);

        if (medList == null) {
            medList = new ArrayList<>();
        }
    }

    /***
     * Method that triggers when TimePicker is closed
     * @param view View parameter that represents the TimePicker
     * @param hourOfDay Hours given by TimePicker
     * @param minute Minutes given by TimePicker
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        timeString = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        setTimeText(timeString);
    }

    /***
     * Sets the TextView to given time
     * @param time Time that is set to TextView
     */
    private void setTimeText(String time) {
        timeText = findViewById(R.id.timetext);
        timeText.setText(time);
    }

}
