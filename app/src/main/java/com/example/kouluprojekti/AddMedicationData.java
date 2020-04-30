package com.example.kouluprojekti;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddMedicationData extends AppCompatActivity {
    private static final String PREFNAME = "medfile";
    private static final String DATA = "DATAJSON";
    TextView inputField;
    MedicationData addMed;
    SharedPreferences setPref;
    ArrayList<MedicationData> medList;
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

    /**
     * Method for saving inserted data from medicationdatainsert activity
     * @param v
     */
    public void saveMedData(View v) {
        SharedPreferences.Editor editor = setPref.edit();
        Spinner spinnerHour = (Spinner) findViewById(R.id.hourSpinner);
        Spinner spinnerMinute = (Spinner) findViewById(R.id.minuteSpinner);
        inputField = findViewById(R.id.editText1);
        String timePre = spinnerHour.getSelectedItem().toString() + "."+ spinnerMinute.getSelectedItem().toString();
        boolean isChecked = false;
        Log.d("test",timePre);
        String medName = inputField.getText().toString();

        addMed = new MedicationData(medName,timePre,isChecked);

        Gson gson = new Gson();

        medList.add(addMed);

        String json = gson.toJson(medList);

        editor.putString(DATA,json);

        editor.commit();

        finish();
    }

    /**
     * Method for fetching data from SharedPreferences. Used to prevent data loss
     */
    private void fetchExistingData(){
        Gson gson = new Gson();

        String json = setPref.getString(DATA,null);

        Type type = new TypeToken<ArrayList<MedicationData>>() {}.getType();

        medList = gson.fromJson(json,type);
        if(medList == null){
            medList = new ArrayList<>();
        }

    }

    /**
     * Method for MedListAdapter to access SharedPreferences and deleting an item in the medList array
     * @param context
     * @param index
     */
    public void logger(Context context,int index){
        Gson gson = new Gson();
        SharedPreferences getPref = context.getSharedPreferences(PREFNAME,Activity.MODE_PRIVATE);
    String json = getPref.getString(DATA,null);

    Type type = new TypeToken<ArrayList<MedicationData>>() {}.getType();

    medList = gson.fromJson(json,type);
    if(medList == null){
        medList = new ArrayList<>();
    }
        medList.remove(index);
         json = gson.toJson(medList);
    SharedPreferences.Editor editor = getPref.edit();
    editor.putString(DATA,json);
    editor.commit();

}

}
