/**
 * @description This class is a database helper for the database of the WakeMe app.
 * It includes storage of data for alarms, sleep sessions, and movement data.
 * @author Amber York yorksea@gmail.com
 */

/*
TODO add database column for if alarm is active

*/

package com.amberyork.wakeme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper  extends SQLiteOpenHelper {

    // DB INFO: VERSION/NAME,ETC
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "wakeMe";

    // Table Names
    public static final String TABLE_SESSION = "session";
    public static final String TABLE_ALARM = "alarm";
    // MOVEBIN "movement bin" which is average readings within time range
    public static final String TABLE_MOVEBIN = "movebin";

    //------------------------------------------------------------------------------------------
    // table column definitions, and create statements (a section for each of three tables)

    // - - - - - - - - - - - - session table - - - - - - - - - - -
    public static final String COLUMN_SESSION_ID = "session_id"; //pk unique to a session
    public static final String COLUMN_SESSION_CREATED_AT = "created_at";
    public static final String COLUMN_SESSION_ENDED_AT = "ended_at";
    public static final String COLUMN_SESSION_ALARM_ID = "alarm_id";  //fk references alarm table
    public static final String COLUMN_SESSION_MINUTE_BIN_SIZE = "minute_bin_size"; //how movement data is binned


    // Table Create Statement
    private static final String CREATE_TABLE_SESSION = "CREATE TABLE "
            + TABLE_SESSION + "(" +
            COLUMN_SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_SESSION_CREATED_AT + "  DATETIME DEFAULT CURRENT_TIMESTAMP," +
            COLUMN_SESSION_ENDED_AT + "  DATETIME," +
            COLUMN_SESSION_ALARM_ID + "  INTEGER," +
            COLUMN_SESSION_MINUTE_BIN_SIZE + "  INTEGER)";


    // - - - - - - - - - - - - alarm table- - - - - - - - - - - - -
    //columns
    public static final String COLUMN_ALARM_ID = "session_id"; //pk unique to an alarm
    public static final String COLUMN_ALARM_CREATED_AT = "created_at";
    public static final String COLUMN_ALARM_SET_TIME = "set_time"; //user-entered drop-dead wakeup time
    public static final String COLUMN_ALARM_TRIGGERED_AT = "triggered_at"; //actual time alarm sounded
    // SMART PERIOD  is number of minutes before alarm time smart wakeup can trigger
    public static final String COLUMN_ALARM_SMART_PERIOD = "smart_period";
    public static final String COLUMN_ALARM_TRIGGER_LIGHTS = "trigger_lights"; //BOOLEAN
    public static final String COLUMN_ALARM_TRIGGER_HEAT = "trigger_heat"; //BOOLEAN
    public static final String COLUMN_ALARM_TRIGGER_SOUND = "trigger_sound"; //BOOLEAN

    // Table Create Statement
    private static final String CREATE_TABLE_ALARM = "CREATE TABLE "
            + TABLE_ALARM + "(" +
            COLUMN_ALARM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_ALARM_CREATED_AT + "  DATETIME DEFAULT CURRENT_TIMESTAMP," +
            COLUMN_ALARM_SET_TIME + " DATETIME," +
            COLUMN_ALARM_TRIGGERED_AT + " DATETIME," +
            COLUMN_ALARM_SMART_PERIOD + " INTEGER DEFAULT 30," +
            COLUMN_ALARM_TRIGGER_LIGHTS + " INTEGER DEFAULT 0," + //used as boolean 0 or 1
            COLUMN_ALARM_TRIGGER_HEAT + " INTEGER DEFAULT 0," + //used as boolean 0 or 1
            COLUMN_ALARM_TRIGGER_SOUND + " INTEGER DEFAULT 0)"; //used as boolean 0 or 1

    // - - - - - - - - - - - - MOVEBIN table  - - - - - - - - - - - - -
    //columns
    public static final String COLUMN_MOVEBIN_ID = "movement_id"; //pk, unique to a bin
    public static final String COLUMN_MOVEBIN_SESSION_ID = "session_id"; //FK to session table
    public static final String COLUMN_MOVEBIN_START_AT = "start_at";
    public static final String COLUMN_MOVEBIN_END_AT = "end_at";
    public static final String COLUMN_MOVEBIN_N = "n";   //number of measurements in bin
    public static final String COLUMN_MOVEBIN_NUM = "num";   //number bin in series
    public static final String COLUMN_MOVEBIN_AVG_X = "avg_x";
    public static final String COLUMN_MOVEBIN_AVG_Y = "avg_y";
    public static final String COLUMN_MOVEBIN_AVG_Z = "avg_z";


    // Table Create Statement
    private static final String CREATE_TABLE_MOVEBIN = "CREATE TABLE "
            + TABLE_MOVEBIN + "(" +
            COLUMN_MOVEBIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_MOVEBIN_SESSION_ID + " INTEGER," + //FK
            COLUMN_MOVEBIN_START_AT + " DATETIME," +
            COLUMN_MOVEBIN_END_AT + " DATETIME," +
            COLUMN_MOVEBIN_N + " INTEGER," +
            COLUMN_MOVEBIN_NUM + " INTEGER," +
            COLUMN_MOVEBIN_AVG_X + " DECIMAL(6,2)," + // average and round in movebin class
            COLUMN_MOVEBIN_AVG_Y + " DECIMAL(6,2)," + //9,2 because not sure the magnitude expected yet
            COLUMN_MOVEBIN_AVG_Z + " DECIMAL(6,2))";

    // -------------------------------END TABLE DEFINITIONS----------------------------------------

    // -------------------------------ALARM TABLE CRUD START-------------------------------
    /**
     * Create an alarm row in the database using the Alarm class
     */
    public long createAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ALARM_SET_TIME, alarm.getSetTime() );
        values.put(COLUMN_ALARM_TRIGGERED_AT, alarm.getTriggeredAt() );

        values.put(COLUMN_ALARM_TRIGGER_LIGHTS, alarm.getTriggerLights());
        values.put(COLUMN_ALARM_TRIGGER_SOUND, alarm.getTriggerSound());
        values.put(COLUMN_ALARM_TRIGGER_HEAT, alarm.getTriggerHeat());


        // insert row
        long alarm_insert_id = db.insert(TABLE_ALARM, null, values);


        return alarm_insert_id;
    }

    /*
 * getting all alarm items
 * */
    public ArrayList<Alarm> getAllAlarms() {
        ArrayList<Alarm> alarms = new ArrayList<Alarm>();
        String selectQuery = "SELECT  * FROM " + TABLE_ALARM;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                /* broke this out into cursorToAlarm()
                Alarm a = new Alarm();
                a.setAlarmID((c.getInt(c.getColumnIndex(COLUMN_ALARM_ID))));
                a.setCreatedAt((c.getString(c.getColumnIndex(COLUMN_ALARM_CREATED_AT))));
                a.setSetTime((c.getString(c.getColumnIndex(COLUMN_ALARM_SET_TIME))));
                a.setTriggeredAt((c.getString(c.getColumnIndex(COLUMN_ALARM_TRIGGERED_AT))));
               // a.setSmartPeriod((c.getInt(c.getColumnIndex(COLUMN_ALARM_SMART_PERIOD))));  //ALARM add this
                a.setTriggerLights((c.getInt(c.getColumnIndex(COLUMN_ALARM_TRIGGER_LIGHTS))));
                a.setTriggerHeat((c.getInt(c.getColumnIndex(COLUMN_ALARM_TRIGGER_HEAT))));
                a.setTriggerSound((c.getInt(c.getColumnIndex(COLUMN_ALARM_TRIGGER_SOUND))));
                */
                // adding to alarm list
                Alarm a = cursorToAlarm(c);
                alarms.add(a);
            } while (c.moveToNext());
        }

        return alarms;
    }

    //took out getAllAlarmStrings because the Alarm class will return appropriate toString() for the listView

    // function to toast last added alarm
    public String getLastAlarmString(){
        SQLiteDatabase db = this.getReadableDatabase();
      //  Cursor c = db.rawQuery(selectQuery, null);

        Cursor c = db.rawQuery("SELECT set_time from alarm ", null);
        String lastAlarmString =   "No alarms in db yet";
        if (c != null && c.moveToFirst()) {
            c.moveToLast();
            String alarmVal = c.getString(c.getColumnIndexOrThrow(DBHelper.COLUMN_ALARM_SET_TIME));
            lastAlarmString =  "Latest Alarm Added: "+alarmVal;
        }

        c.close();
        db.close();
        return lastAlarmString;
    }


    /*
 * Updating an alarm so if you want to just change the time
 * TODO : create ui experince to do this action
 */
    public int updateAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
       // values.put(COLUMN_ALARM_ID, alarm.getAlarmID()); //can't modify this
        //values.put(COLUMN_ALARM_CREATED_AT, alarm.getCreatedAt()); can't modify this
        values.put(COLUMN_ALARM_SET_TIME, alarm.getSetTime() );
        values.put(COLUMN_ALARM_TRIGGERED_AT, alarm.getTriggeredAt() );
        values.put(COLUMN_ALARM_TRIGGER_LIGHTS, alarm.getTriggerLights());
        values.put(COLUMN_ALARM_TRIGGER_SOUND, alarm.getTriggerSound());
        values.put(COLUMN_ALARM_TRIGGER_HEAT, alarm.getTriggerHeat());

        // updating row
        return db.update(TABLE_ALARM, values, COLUMN_ALARM_ID + " = ?",
                new String[] { String.valueOf(alarm.getAlarmID()) }); //find id of updated alarms (could have put null)
    }

    //cursorToAlarm returns an alarm from a db row based on cursor position.
    private Alarm cursorToAlarm(Cursor c) {

        //instantiate alarm class
        Alarm a = new Alarm();

        //set all the alarm stuff with data from db based on cursor position
        a.setAlarmID((c.getInt(c.getColumnIndex(COLUMN_ALARM_ID))));
        a.setCreatedAt((c.getString(c.getColumnIndex(COLUMN_ALARM_CREATED_AT))));
        a.setSetTime((c.getString(c.getColumnIndex(COLUMN_ALARM_SET_TIME))));
        a.setTriggeredAt((c.getString(c.getColumnIndex(COLUMN_ALARM_TRIGGERED_AT))));
        // a.setSmartPeriod((c.getInt(c.getColumnIndex(COLUMN_ALARM_SMART_PERIOD))));  //ALARM add this
        a.setTriggerLights((c.getInt(c.getColumnIndex(COLUMN_ALARM_TRIGGER_LIGHTS))));
        a.setTriggerHeat((c.getInt(c.getColumnIndex(COLUMN_ALARM_TRIGGER_HEAT))));
        a.setTriggerSound((c.getInt(c.getColumnIndex(COLUMN_ALARM_TRIGGER_SOUND))));

        //now return the alarm
        return a;
    }


    //--------------------------------ALARM TABLE END -------------------------------
    //--------------------------------START create/upgrade---------------------------------------------


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // create the three tables
        db.execSQL(CREATE_TABLE_SESSION);
        db.execSQL(CREATE_TABLE_ALARM);
        db.execSQL(CREATE_TABLE_MOVEBIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop the tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVEBIN);

        // create new tables
        onCreate(db);
    }

}//DBHelper class