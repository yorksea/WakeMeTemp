package com.amberyork.wakeme;

import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import android.os.AsyncTask;
import java.net.URLEncoder;
import java.net.MalformedURLException;

import android.os.SystemClock;
import android.net.Uri;
/*
TODO:
add LOG
incorporate smart_period
edit theme colors
 */


public class MainActivity extends AppCompatActivity {
    //for sqlite db
    private static final String TAG = "MainActivityLOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


    } //end onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_motion_display:
                Intent intent = new Intent(this, MotionDisplayActivity.class);
                this.startActivity(intent);
                toast("Switch to Motion Display");
                break;
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                this.startActivity(settingsIntent);
                break;
            case R.id.action_alarm_trigger:
                Intent alarmTriggerIntent = new Intent(this, AlarmTriggerActivity.class);
                this.startActivity(alarmTriggerIntent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
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

    //toast for testing
    private void toast(String aToast){
        Toast.makeText(getApplicationContext(), aToast, 5000).show();
    }

//-------------------------END TESTING POST----------------------------
}
