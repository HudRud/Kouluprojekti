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
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


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
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Calendar c = Calendar.getInstance();

        String name = getItem(position).getMedName();

        String time = getItem(position).getTime();

        boolean status = getItem(position).isChecked();

        String date = getItem(position).getDate();

        final MedicationData medData = new MedicationData(name, time, status, date);

        LayoutInflater inflater = LayoutInflater.from(this.mContext);

        convertView = inflater.inflate(this.mResource, parent, false);

        TextView topic = convertView.findViewById(R.id.topicAppointment);

        TextView timeText = convertView.findViewById(R.id.timeAppointment);

        TextView dateText = convertView.findViewById(R.id.dateAppointment);

        CheckBox cc = convertView.findViewById(R.id.checkboxAppointment);

        String timeString = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());

        if (!getItem(position).getDate().equals(timeString)) {
            getItem(position).setChecked(false);
            updater.logger(getContext(), position, false);

        }
        if(!getItem(position).getMedName().equals("Appointment")){

            topic.setText(name);

            timeText.setText(time);

            if (getItem(position).isChecked() == false) {
                convertView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                cc.setOnClickListener(new View.OnClickListener() {
                    /**
                     * onClick given to checkboxes created to the listView.
                     * Used to manipulate the parent and data in the medList on AddMedicationData.java
                     * @param v
                     */
                    @Override
                    public void onClick(View v) {
                        updater.logger(getContext(), position, true);
                        updater.loadData(getContext());
                        clear();
                        addAll(updater.medList);
                        notifyDataSetChanged();
                    }
                });
            } else {
                convertView.setBackgroundColor(Color.parseColor("#535353"));
                cc.setChecked(true);
                cc.setClickable(false);
                convertView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        updater.removeItem(getContext(),position);
                        updater.loadData(getContext());
                        clear();
                        addAll(updater.medList);
                        notifyDataSetChanged();
                        return false;
                    }
                });
            }

        }else{

            topic.setText(name);

            timeText.setText(time);

            dateText.setText(date);

            if (getItem(position).isChecked() == false) {
                convertView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                cc.setOnClickListener(new View.OnClickListener() {
                    /**
                     * onClick given to checkboxes created to the listView.
                     * Used to manipulate the parent and data in the medList on AddMedicationData.java
                     * @param v
                     */
                    @Override
                    public void onClick(View v) {
                        updater.removeItem(getContext(),position);
                        updater.loadData(getContext());
                        clear();
                        addAll(updater.medList);
                        notifyDataSetChanged();
                    }
                });
            }
        }

        return convertView;


    }


}
