package com.coding.wk.cataloguemovieextendedapplication.notifications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class ReleaseTodayPreference {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final String KEY_REPEATING_TIME_RELEASE = "RepeatingTimeRelease";
    @SuppressLint("CommitPrefEdits")
    public ReleaseTodayPreference(Context context){
        String PREF_NAME = "ReleaseTodayPreference";
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void setRepeatingTimeRelease(String time){
        editor.putString(KEY_REPEATING_TIME_RELEASE, time);
        editor.commit();
    }
    public String getRepeatingTimeRelease(){
        return sharedPreferences.getString(KEY_REPEATING_TIME_RELEASE, null);
    }
}
