package com.coding.wk.cataloguemovieextendedapplication.notifications;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.coding.wk.cataloguemovieextendedapplication.R;

import java.util.Calendar;

public class ReleaseTodayReceiver extends BroadcastReceiver {
    private final String TAG = getClass().getSimpleName();
    private final int REQUEST_CODE = 801;
    private SchedulerTask schedulerTask;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Masuk onReceive ReleaseToday");
        schedulerTask = new SchedulerTask(context);
        schedulerTask.createPeriodicTask();
    }

    public void cancelAlarmRelease(Context context){
        schedulerTask = new SchedulerTask(context);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseTodayReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        schedulerTask.cancelPeriodicTask();
    }
    public void setAlarmRelease(Context context, String time){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseTodayReceiver.class);
        String [] timeArray = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        if (calendar.before(Calendar.getInstance())){
            calendar.add(Calendar.DATE, 1);
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

}
