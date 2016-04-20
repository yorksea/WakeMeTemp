package com.amberyork.wakeme;

/**
 * Created by yorksea on 4/19/16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class AlarmAdapter extends ArrayAdapter<Alarm> {

    private final Context context;
    private final List<Alarm> objects;

    public AlarmAdapter(Context context, int resource,
                              ArrayList<Alarm> objects) {
        super(context, resource);
        this.context = context;
        this.objects = objects;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
       Alarm alarm = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.alarm_layout, parent, false);
        }
        // Lookup view for data population
       TextView tvID = (TextView) convertView.findViewById(R.id.AlarmID);
        TextView tvTime = (TextView) convertView.findViewById(R.id.AlarmTime);

        tvID.setText(Integer.toString(alarm.getAlarmID()));
        try {
            tvTime.setText(alarm.getSetTimePretty());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Return the completed view to render on screen
        return convertView;
    }


    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Alarm getItem(int position) {
        return objects.get(position);
    }
}