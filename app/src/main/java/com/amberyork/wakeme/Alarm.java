package com.amberyork.wakeme;

import android.widget.Toast;
public class Alarm {


    int alarm_id; //don't need this for creation now, it autoincrements
    String set_time;
    String triggered_at;
    String created_at;
    int smart_period; // not used yet but included here for future
    int trigger_lights;
    int trigger_heat;
    int trigger_sound;

    // constructors
    public Alarm(){
    }

    /**
     *  @description constructor NEW ALARM (new alarm input - no id/"created at" needed because this is done on insert)
     *  also doesn't use triggered_at because that hasn't happened yet
     *  @example      Alarm newAlarm = new Alarm(set_time,boolLights, boolHeat, boolSound);

     */
    public Alarm(String set_time, int trigger_lights, int trigger_heat, int trigger_sound) {
        //this.alarm_id = alarm_id;
        // this.created_at = created_at;
        this.set_time = set_time;
      //  this.triggered_at = triggered_at;  //hasn't happened yet
        this.trigger_lights = trigger_lights;
        this.trigger_heat = trigger_heat;
        this.trigger_sound = trigger_sound;
    }




   // constructor ALL (if making an object with all variables, like from a return of query)
    public Alarm(int alarm_id, String created_at, String set_time, String triggered_at, int trigger_lights, int trigger_heat, int trigger_sound) {
        this.alarm_id = alarm_id;
        this.created_at = created_at;
        this.set_time = set_time;
        this.triggered_at = triggered_at;
        this.trigger_lights = trigger_lights;
        this.trigger_heat = trigger_heat;
        this.trigger_sound = trigger_sound;
    }

    // 7 getters
    public String getCreatedAt() {
        return this.created_at;
    }
    public int getAlarmID() {
        return this.alarm_id;
    }
    public String getSetTime() {
        return this.set_time;
    }
    public String getTriggeredAt() {
        return this.triggered_at;
    }
    public int getTriggerHeat() {
        return this.trigger_heat;
    }
    public int getTriggerSound() {
        return this.trigger_sound;
    }
    public int getTriggerLights() {
        return this.trigger_lights;
    }

    // 7 setters
    public void setAlarmID(int alarm_id) {
        this.alarm_id = alarm_id;
    }
    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }
    public void setSetTime(String set_time) {
        this.set_time = set_time;
    }
    public void setTriggeredAt(String triggered_at) {
        this.triggered_at = triggered_at;
    }
    public void setTriggerHeat(int trigger_heat) {
        this.trigger_heat = trigger_heat;
    }
    public void setTriggerSound(int trigger_heat) {
       this.trigger_sound = trigger_sound;
    }
    public void setTriggerLights(int trigger_lights) {
        this.trigger_lights = trigger_lights;
    }


}