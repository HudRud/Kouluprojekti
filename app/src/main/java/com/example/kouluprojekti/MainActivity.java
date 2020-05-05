package com.example.kouluprojekti;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

ArrayList<MedicationData> medList;
ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureCalendarButton();

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

    // Calendar button takes the user to Calendar activity
    private void configureCalendarButton() {
        ImageButton calendarButton = findViewById(R.id.calendar_button);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Calendar.class));
            }
        });
    }


}
