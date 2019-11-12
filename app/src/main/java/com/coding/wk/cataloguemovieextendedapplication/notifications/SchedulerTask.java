package com.coding.wk.cataloguemovieextendedapplication.notifications;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.OneoffTask;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

public class SchedulerTask {
    private GcmNetworkManager gcmNetworkManager;

    SchedulerTask(Context context){
        gcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    void createPeriodicTask(){
        Log.d(getClass().getSimpleName(), "Masuk createPeriodicTask()");
        Task periodicTask = new OneoffTask.Builder()
                .setService(ReleaseTodayService.class)
                .setTag(ReleaseTodayService.RELEASE_TAG)
                .setPersisted(true)
                .setExecutionWindow(0L, 10L)
                .build();
        gcmNetworkManager.schedule(periodicTask);
    }

    void cancelPeriodicTask(){
        if(gcmNetworkManager != null){
            gcmNetworkManager.cancelTask(ReleaseTodayService.RELEASE_TAG, ReleaseTodayService.class);
        }
    }
}
