package com.coding.wk.cataloguemovieextendedapplication;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.coding.wk.cataloguemovieextendedapplication.notifications.DailyReminderPreference;
import com.coding.wk.cataloguemovieextendedapplication.notifications.DailyReminderReceiver;
import com.coding.wk.cataloguemovieextendedapplication.notifications.NotificationPreference;
import com.coding.wk.cataloguemovieextendedapplication.notifications.ReleaseTodayPreference;
import com.coding.wk.cataloguemovieextendedapplication.notifications.ReleaseTodayReceiver;
import com.coding.wk.cataloguemovieextendedapplication.notifications.SchedulerTask;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener{
    private final String TAG = getClass().getSimpleName();
    private Switch switchDaily, switchRelease;
    private RelativeLayout layoutLanguage;
    private boolean isDailySwitch, isReleaseSwitch, isDailyActive, isReleaseActive;
    private DailyReminderPreference dailyPreference;
    private ReleaseTodayPreference releasePreference;
    private NotificationPreference notificationPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        dailyPreference = new DailyReminderPreference(this);
        releasePreference = new ReleaseTodayPreference(this);
        notificationPreference = new NotificationPreference(this);
        switchDaily = findViewById(R.id.switch_daily_reminder);
        switchRelease = findViewById(R.id.switch_release_reminder);

        layoutLanguage = findViewById(R.id.layout_language);
        layoutLanguage.setOnClickListener(this);

        dailyPreference.setRepeatingTime("07:00");
        dailyPreference.setRepeatingMessage(getResources().getString(R.string.message_daily_reminder));
        releasePreference.setRepeatingTimeRelease("08:00");

        isDailyActive = notificationPreference.getDailyReminder();
        isReleaseActive = notificationPreference.getReleaseReminder();

        checkActive(isDailyActive, isReleaseActive);

        switchDaily.setOnCheckedChangeListener(this);
        switchRelease.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        switch(id){
            case R.id.switch_daily_reminder:
                isDailySwitch = switchDaily.isChecked();
                refreshData(isDailySwitch, isReleaseSwitch);
                break;
            case R.id.switch_release_reminder:
                isReleaseSwitch = switchRelease.isChecked();
                refreshData(isDailySwitch, isReleaseSwitch);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        Intent localIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        startActivity(localIntent);
    }

    private void refreshData(boolean isDailyActive, boolean isReleaseActive){
        ReleaseTodayReceiver releaseReceiver = new ReleaseTodayReceiver();
        DailyReminderReceiver dailyReceiver = new DailyReminderReceiver();

        notificationPreference.setDailyReminder(isDailyActive);
        notificationPreference.setReleaseReminder(isReleaseActive);
        if(isDailyActive){
            String message = dailyPreference.getRepeatingMessage();
            String time = dailyPreference.getRepeatingTime();
            Log.d(TAG, "Time: "+time);
            Log.d(TAG, "Message: "+message);
            dailyReceiver.setAlarm(this, time, message);
        }
        else{
            dailyReceiver.cancelAlarm(this);
        }

        if(isReleaseActive){
            String time = releasePreference.getRepeatingTimeRelease();
            releaseReceiver.setAlarmRelease(this, time);
        }
        else{
            releaseReceiver.cancelAlarmRelease(this);
        }
    }
    public void checkActive(boolean isDailyActive, boolean isReleaseActive){
        this.isDailyActive = isDailyActive;
        this.isReleaseActive = isReleaseActive;
        switchDaily.setChecked(this.isDailyActive);
        switchRelease.setChecked(this.isReleaseActive);
    }
}
