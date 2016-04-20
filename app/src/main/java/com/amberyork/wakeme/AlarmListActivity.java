package com.amberyork.wakeme;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.content.Intent;
import android.widget.Toast;


public class AlarmListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // toast("started alarm list");

        //get data from db and then toast it
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        String lastAlarm = dbHelper.getLastAlarmString();

        toast(lastAlarm);


    }

    //have to add getActivity(). if in fragment, before get app context or it won't work.
    public void goToAlarmListActivity (View view){
        Intent intent = new Intent (getApplicationContext(), AlarmListActivity.class);
        startActivity(intent);
    }

    //// view sleep chart

    public void goToSleepSessionActivity (View view){
        Intent intent = new Intent (getApplicationContext(), SleepSessionActivity.class);
        startActivity(intent);
    }
    //// main activiy

    public void goToMainActivity (View view){
        Intent intent = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

//just for list activity
public void goToAlarmEditActivity (View view){
    Intent intent = new Intent (getApplicationContext(), AlarmEditActivity.class);
    startActivity(intent);
}

    //toast for testing
    private void toast(String aToast){
        Toast.makeText(getApplicationContext(), aToast, 10000).show();
    }

}
