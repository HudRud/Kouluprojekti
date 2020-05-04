package com.example.kouluprojekti;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Switch;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    ArrayList<MedicationData> medList;

    ListView mMainList;

    FloatingActionButton addMed;

    SharedPreferences getPref;

    MedListAdapter adapter;

    AddMedicationData updater;

    private static final String PREFNAME = "medfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        addMed = findViewById(R.id.floatingActionButton);

        medList = new ArrayList<>();

        mMainList = findViewById(R.id.ListView1);

        getPref = getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);

        loadData();

        adapter = new MedListAdapter(this, R.layout.mednotelayout, medList);

        mMainList.setAdapter(adapter);

        updater = new AddMedicationData();

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

   //Temporary method for resetting notifications
    public void resetCheck(View v) {
        SharedPreferences.Editor edit = getSharedPreferences(PREFNAME,Activity.MODE_PRIVATE).edit();
        edit.putString("DATAJSON","");
        edit.commit();
        medList = new ArrayList<>();
        adapter.clear();

    }

    /**
     * Creates a popup menu for selecting the type of notification added
     * @param v
     */
    public void showPopUp(View v){
        PopupMenu pop = new PopupMenu(this,v);
        pop.setOnMenuItemClickListener(this);
        pop.inflate(R.menu.popupmenu);
        pop.show();
    }
    @Override
    public boolean onMenuItemClick(MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.medication:
                 intent = new Intent(MainActivity.this, AddMedicationData.class);
                 startActivity(intent);
                 break;
            case R.id.notification:
                 intent = new Intent(MainActivity.this, Calendar.class);
                 startActivity(intent);
                 break;
        }
        return true;
    }

}
