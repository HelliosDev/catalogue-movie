package com.coding.wk.cataloguemovieextendedapplication.notifications;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.coding.wk.cataloguemovieextendedapplication.R;

import java.util.Calendar;
import java.util.StringTokenizer;

public class DailyReminderReceiver extends BroadcastReceiver{
    private final String TAG = getClass().getSimpleName();
    private final int REQUEST_CODE = 100;


    public void setAlarm(Context context, String time, String message){
        Log.d(TAG, "set alarm active");
        Log.d(TAG, "setAlarm Time: "+time);
        Log.d(TAG, "setAlarm message: "+message);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminderReceiver.class);
        String [] timeArray = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        Log.d(TAG, "Hours: "+Integer.parseInt(timeArray[0]));
        Log.d(TAG, "Minutes: "+Integer.parseInt(timeArray[1]));
        if (calendar.before(Calendar.getInstance())){
            calendar.add(Calendar.DATE, 1);
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }
    private void showNotificationReminder(Context context, String title, String message, int notifId){
        Log.d(getClass().getSimpleName(), "Berhasil showNotification");
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.dicoding_notifications)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);
        if (notificationManager != null) {
            notificationManager.notify(notifId, builder.build());
        }
    }

    public void cancelAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Masuk on receive dailyreminder");
        String title = context.getResources().getString(R.string.title_daily_reminder);
        String message = context.getResources().getString(R.string.message_daily_reminder);
        showNotificationReminder(context, title, message, REQUEST_CODE);
        Log.d(TAG, "Berhasil showNotification");
    }
}
