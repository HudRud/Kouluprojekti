package com.example.kouluprojekti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ArrayList<MedicationData> medList;
ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        medList = new ArrayList<>();
        lv = findViewById(R.id.ListView1);
        MedicationData med1 = new MedicationData("Concerta");
        MedicationData med2 = new MedicationData("Vicodin");
        MedicationData med3 = new MedicationData("Simvastatin");
        MedicationData med4 = new MedicationData("Jaakko");
        medList.add(med1);
        medList.add(med2);
        medList.add(med3);
        medList.add(med4);
        MedListAdapter adapter = new MedListAdapter(this,R.layout.mednotelayout,medList);
        lv.setAdapter(adapter);
    }
}
