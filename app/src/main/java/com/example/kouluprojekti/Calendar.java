package com.example.kouluprojekti;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

public class Calendar extends AppCompatActivity {

    private String curDate;
    SimpleDateFormat sdf =  new SimpleDateFormat("yy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Update(View view){
        CalendarView calendarView = (CalendarView)findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                curDate = String.valueOf(dayOfMonth) + "." + String.valueOf(month) + "." + String.valueOf(year);
            }
        });
        TextView textView = findViewById(R.id.textView);
        textView.setText(curDate);
    }
}
