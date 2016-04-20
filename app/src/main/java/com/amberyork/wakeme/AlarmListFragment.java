package com.amberyork.wakeme;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class AlarmListFragment extends ListFragment implements OnItemClickListener {
    private static final String TAG = "AlarmListFragment LOG";

    private AlarmAdapter alarmAdapter;
    private ListView alarmListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarm_list, container, false);
        Log.d(TAG, "view inflated");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //below was canned data for display purposes
      //  ArrayAdapter mainAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.Alarms, android.R.layout.simple_list_item_1);
       // setListAdapter(mainAdapter);

        //get data from db through dbhelper
        DBHelper dbHelper = new DBHelper(getActivity());

        ArrayList<Alarm> alarms = dbHelper.getAllAlarms();

        //now fill list with custom adapter
        Log.d(TAG, "alarms.size(): "+ alarms.size());


        alarmAdapter = new AlarmAdapter(getActivity(), R.layout.alarm_layout,
                alarms);
        setListAdapter(alarmAdapter);


        //test comment out below
        //setListAdapter(mainAdapter);

        getListView().setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Log.d(TAG,"Item clicked: " + position);
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }
}