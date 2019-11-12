package com.coding.wk.cataloguemovieextendedapplication.notifications;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.coding.wk.cataloguemovieextendedapplication.BuildConfig;
import com.coding.wk.cataloguemovieextendedapplication.R;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class ReleaseTodayService extends GcmTaskService {
    private final String TAG = getClass().getSimpleName();
    public static final String RELEASE_TAG = "ReleaseTodayTag";
    private final String API_KEY = BuildConfig.API_KEY;
    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(RELEASE_TAG)){
            getReleaseUpcoming();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }
        return result;
    }

    @Override
    public void onInitializeTasks() {
        super.onInitializeTasks();
        SchedulerTask schedulerTask = new SchedulerTask(this);
        schedulerTask.createPeriodicTask();
    }

    private void showNotificationReleaseToday(Context context, String title, String message, int notifId){
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.dicoding_notifications)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);
        notificationManager.notify(notifId, builder.build());
    }

    private void getReleaseUpcoming(){
        Log.d(TAG, "getReleaseUpcoming is Running");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        final String sysDate = dateFormat.format(new Date());
        Log.d(getClass().getSimpleName(), "Date dalam sistem adalah: "+sysDate);

        SyncHttpClient client = new SyncHttpClient();
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key="+API_KEY;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    int notifId = 200;
                    JSONArray jsonArray = new JSONObject(new String(responseBody)).getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++){
                        String releaseDate = jsonArray.getJSONObject(i).getString("release_date");
                        String movieTitle = jsonArray.getJSONObject(i).getString("title");
                        Log.d(getClass().getSimpleName(), "Release date: "+releaseDate+" dan Movie Name: "+movieTitle);
                        String message = movieTitle+ " " + getResources().getString(R.string.release_today_notif);
                        //String testingDate = "2018-09-13";
                        if(releaseDate.equals(sysDate)) showNotificationReleaseToday(getApplicationContext(), movieTitle, message, notifId);
                        notifId++;
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "getReleaseUpcoming has failed");
            }
        });
    }
}
