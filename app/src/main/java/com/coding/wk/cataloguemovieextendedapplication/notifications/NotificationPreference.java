package com.coding.wk.cataloguemovieextendedapplication.notifications;

import android.content.Context;
import android.content.SharedPreferences;

public class NotificationPreference {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final String PREF_NAME = "NotificationPreference";
    private final String KEY_DAILY = "KeyDaily";
    private final String KEY_RELEASE = "KeyRelease";
    public NotificationPreference(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void setDailyReminder(boolean isActive){
        editor.putBoolean(KEY_DAILY, isActive);
        editor.commit();
    }
    public boolean getDailyReminder(){
        return sharedPreferences.getBoolean(KEY_DAILY, false);
    }
    public void setReleaseReminder(boolean isActive){
        editor.putBoolean(KEY_RELEASE, isActive);
        editor.commit();
    }
    public boolean getReleaseReminder(){
        return sharedPreferences.getBoolean(KEY_RELEASE, false);
    }
    public void clear(){
        editor.clear();
        editor.commit();
    }
}
