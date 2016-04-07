package com.amberyork.wakeme;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Toast;

public class AlarmListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // toast("started alarm list");

        //show data from db for demo purposes and then toast it
        DBHelper DbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase db = DbHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT set_time from alarm ", null);


        if (c != null && c.moveToFirst()) {
            c.moveToLast();
            String alarmVal = c.getString(c.getColumnIndexOrThrow(DBHelper.COLUMN_ALARM_SET_TIME));
            toast("Latest Alarm Added: "+alarmVal);
        } else {
            toast("No alarms in db yet");
        }
        c.close();
        db.close();
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
