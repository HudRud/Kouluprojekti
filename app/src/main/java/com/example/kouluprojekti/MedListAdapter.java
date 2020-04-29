package com.example.kouluprojekti;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MedListAdapter extends ArrayAdapter<MedicationData> {
    private Context mContext;
    int mResource;
    public MedListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MedicationData> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String name = getItem(position).getMedName();

        MedicationData medData = new MedicationData(name);

        LayoutInflater inflater = LayoutInflater.from(this.mContext);

        convertView = inflater.inflate(this.mResource, parent, false);

        TextView tv = convertView.findViewById(R.id.medname);

        tv.setText(name);

        return convertView;

    }
}
