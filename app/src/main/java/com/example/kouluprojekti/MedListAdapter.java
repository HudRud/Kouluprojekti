package com.example.kouluprojekti;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;


public class MedListAdapter extends ArrayAdapter<MedicationData> {
    private Context mContext;

    int mResource;

    AddMedicationData updater = new AddMedicationData();

    ArrayList<MedicationData> medList;

    public MedListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MedicationData> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;

    }

    /**
     * Methdod that creates a View based on the resource parameter
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        String name = getItem(position).getMedName();

        String time = getItem(position).getTime();

        boolean status = getItem(position).isChecked();

        final MedicationData medData = new MedicationData(name,time,status);

        LayoutInflater inflater = LayoutInflater.from(this.mContext);



            convertView = inflater.inflate(this.mResource, parent, false);


            TextView tv = convertView.findViewById(R.id.medname);

            tv.setText(name);

            CheckBox cc = convertView.findViewById(R.id.checkbox1);
            if(getItem(position).isChecked() == false){
                convertView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                cc.setOnClickListener(new View.OnClickListener() {
                    /**
                     * onClick given to checkboxes created to the listView.
                     * Used to manipulate the parent and data in the medList on AddMedicationData.java
                     * @param v
                     */
                    @Override
                    public void onClick(View v) {
                        //remove(getItem(position));
                        //notifyDataSetChanged();
                        updater.logger(getContext(),position,true);
                        updater.loadData(getContext());
                        clear();
                        addAll(updater.medList);
                        notifyDataSetChanged();
                    }
                });
            }else{
                //cc.setVisibility(View.INVISIBLE);
                Log.d("flag","entry no." + position + " is checked");
                convertView.setBackgroundColor(Color.parseColor("#535353"));
            }




        return convertView;


    }


}
