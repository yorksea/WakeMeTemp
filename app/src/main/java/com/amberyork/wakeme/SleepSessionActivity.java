package com.amberyork.wakeme;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;

//for plotting

import java.util.Random;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class SleepSessionActivity extends AppCompatActivity {
    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_session);
        // we get graph view instance
        GraphView graph = (GraphView) findViewById(R.id.graph);
        // data
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);
        // customize a little bit viewport
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(30);
        viewport.setScrollable(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {

            @Override
            public void run() {
                // we add 1000 new entries
                for (int i = 0; i < 2000; i++) {
                    final double Y = i;
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                             if (Y % 100 > 10 && Y %100 < 30) {
                                addEntry(2,5);//rem1
                             } else if (Y % 100 > 60 && Y %100 < 75) {
                                 addEntry(3,7);//rem2
                             } else if (Y % 100 > 75 ) {
                                 addEntry(26,29); //shallow1
                             } else{
                                addEntry(23,27); //shallow2
                             }
                        }
                    });

                    // sleep to slow down the add of entries
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        // manage error ...
                    }
                }
            }
        }).start();
    }

    // add random data to graph
    private void addEntry(int min, int max) {
        // here, we choose to display max 10 points on the viewport and we scroll to end
      //  series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 30d), true, 30);
        series.appendData(new DataPoint(lastX++, (RANDOM.nextInt((max - min) + 1) + min)*1.0), true, 200);
      //the 400 is how many points to display before scrolling

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


}