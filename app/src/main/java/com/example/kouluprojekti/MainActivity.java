package com.example.kouluprojekti;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
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

        //addMed = findViewById(R.id.floatingActionButton);

        medList = new ArrayList<>();

        //mMainList = findViewById(R.id.ListView1);

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
     * Method that when used, starts another activity for medication information input
     * @param v
     */
    public void addMedButton(View v) {
        Intent nextIntent = new Intent(MainActivity.this, AddMedicationData.class);

        startActivity(nextIntent);
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

        if (medList == null){
            medList = new ArrayList<>();
        }

    }
    public void resetCheck(View v){
        for(int i = 0; i < medList.size(); i++){
            Log.d("flags",Boolean.toString(medList.get(i).isChecked()));
            updater.logger(this,i,false);
        }
        loadData();
        adapter.clear();
        adapter.addAll(medList);
        adapter.notifyDataSetChanged();
    }

}
