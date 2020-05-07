package com.example.kouluprojekti;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
//Kevin Akkoyun
/***
 * Adapter for ListView that uses a custom layout
 */
public class MedListAdapter extends ArrayAdapter<MedicationData> {
    private Context mContext;
    private int mResource;
    AddMedicationData updater = new AddMedicationData();
    ArrayList<MedicationData> medList;

    MedListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MedicationData> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;


    }

    /**
     * Method that creates a View based on the resource parameter
     *
     * @param position    Index of the given ArrayList item
     * @param convertView View used to create ListItems
     * @param parent      Parent ListView where the views are created
     * @return Returns the view that is used to create a ListItem
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
        if (!getItem(position).getMedName().equals("Appointment")) {

            topic.setText(name);

            timeText.setText(time);

            if (!getItem(position).isChecked()) {
                convertView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                cc.setOnClickListener(new View.OnClickListener() {
                    /**
                     * onClick given to checkboxes created to the listView.
                     * Used to manipulate the parent and data in the medList on AddMedicationData.java
                     * @param v View parameter given to use the method onClick
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
                        updater.removeItem(getContext(), position);
                        updater.loadData(getContext());
                        clear();
                        addAll(updater.medList);
                        notifyDataSetChanged();
                        return false;
                    }
                });
            }

        } else {

            topic.setText(name);

            timeText.setText(time);

            dateText.setText(date);

            if (!getItem(position).isChecked()) {
                convertView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                cc.setOnClickListener(new View.OnClickListener() {
                    /**
                     * onClick given to checkboxes created to the listView.
                     * Used to manipulate the parent and data in the medList on AddMedicationData.java
                     * @param v View parameter given to use the method onClick
                     */
                    @Override
                    public void onClick(View v) {
                        updater.removeItem(getContext(), position);
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
