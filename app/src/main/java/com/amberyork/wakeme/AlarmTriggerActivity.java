package com.amberyork.wakeme;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.res.Resources;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AlarmTriggerActivity extends AppCompatActivity {
    private static final String TAG = "AlarmTriggerActivityLOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_trigger);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //this gives you a nice back arrow
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    //--------------------------TESTING POST----------------------------
    class PostClient extends AsyncTask<String, Void, String> {
        public String doInBackground(String... params) {

            String trigger = params[0];
            String maker_key = params[1];

            //build the url based on the desired trigger
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("maker.ifttt.com")
                    .appendPath("trigger")
                    .appendPath(trigger)
                    .appendPath("with")
                    .appendPath("key")
                    .appendPath(maker_key);

            String urlString = builder.build().toString();
            try {
                // Stuff variables
                Log.d(TAG, urlString);
                URL url = new URL(urlString.toString());


                String param = "test=testing";
                Log.d(TAG, "param:" + param);

                // Open a connection using HttpURLConnection
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.connect();

                BufferedReader in = null;

                if (con.getResponseCode() != 200) {

                    in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    Log.d(TAG, "!=200: " + in);
                    //ok, so it was a bad request, what code is it?
                    Log.d(TAG, "Response code: " + con.getResponseCode());
                } else {
                    in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    Log.d(TAG, "POST request send successful: " + in);

                };

            } catch (MalformedURLException e) {
                Log.d(TAG,"The URL is not valid.");
                Log.d(TAG,e.getMessage());

            } catch (Exception e) {
                Log.d(TAG, "Exception");
                e.printStackTrace();
                return null;
            }
            // Set null and we´e good to go
            return null;
        }
    }

    //this function is used when a trigger button is pushed to activate the http POST
    public void clickFunc(View v){
        String trigger;
        String idString = "no id";

        Button b = (Button)v;
        //display which button pressed
        Log.d(TAG, "Button pressed: " + b.getText().toString());

        EditText edit_text = (EditText) findViewById(R.id.maker_key_text);
        CharSequence edit_text_value = edit_text.getText();
        String maker_key =edit_text_value.toString();
        Log.d(TAG, "Key: " +maker_key);
        //don't know why I couldn't just do this
        //String maker_key =(EditText)findViewById(R.id.maker_key_text).getText().toString();

        //this section activates a post with the trigger based on the id of button click
        //I like this better instead of switching all the cases of button press
        Resources res = v.getResources();     // get resources
        //use of resources to convert getID() result to string through getResourceEntryName

        int id = v.getId();
        if(res != null) {
            idString = res.getResourceEntryName(id); // get id string entry
            Log.d(TAG, idString);
            trigger = idString.replace("but_","");

            //got trigger now instantiate and send off post with trigger
            new PostClient().execute(trigger,maker_key);
        }
    }


}//AlarmTriggerActivity
