package com.coding.wk.cataloguemovieextendedapplication.notifications;

import android.content.Context;
import android.content.SharedPreferences;

public class DailyReminderPreference {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final String PREF_NAME = "DailyReminderPreference";
    private final String KEY_REPEATING_TIME = "RepeatingTime";
    private final String KEY_REPEATING_MESSAGE = "RepeatingMessage";
    public DailyReminderPreference(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void setRepeatingMessage(String message){
        editor.putString(KEY_REPEATING_MESSAGE, message);
        editor.commit();
    }
    public String getRepeatingMessage(){
        return sharedPreferences.getString(KEY_REPEATING_MESSAGE, null);
    }
    public void setRepeatingTime(String time){
        editor.putString(KEY_REPEATING_TIME, time);
        editor.commit();
    }
    public String getRepeatingTime(){
        return sharedPreferences.getString(KEY_REPEATING_TIME, null);
    }
    public void clear(){
        editor.clear();
        editor.commit();
    }
}
