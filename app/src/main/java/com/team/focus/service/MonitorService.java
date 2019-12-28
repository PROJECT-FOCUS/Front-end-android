package com.team.focus.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.team.focus.R;
import com.team.focus.data.model.SharedPreferenceAccessUtils;
import com.team.focus.data.model.Usage;
import com.team.focus.data.model.UsageFromSystem;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MonitorService extends Service {

    private Timer timer = null;
    private Set<String> packageNames;
    private Context context;

    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    public static long SERVICE_PERIOD = 120000; // sync data every 2 minutes

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        context = this;
        start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void start()
    {
        if(timer == null)
        {
            timer = new Timer();
            timer.schedule(new MonitoringTimerTask(), 500, SERVICE_PERIOD);
        }
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("FOCUS Foreground Service")
                .setContentText("FOCUS is standing in the foreground to monitor your app usage")
                .setSmallIcon(R.mipmap.ic_launcher_square)
                .build();

        startForeground(1, notification);
    }

    public void stop()
    {
        timer.cancel();
        timer.purge();
        timer = null;
    }

    private class MonitoringTimerTask extends TimerTask
    {
        @Override
        public void run()
        {
            Map<String, Usage> monitoredSet = UsageFromSystem.getMonitoredUsage(context);

            // sync cloud

            ActivityManager activityManager = (ActivityManager)MonitorService.this.getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);
            String current = taskInfo.get(0).topActivity.getPackageName();
            if (!SharedPreferenceAccessUtils.getNotification(context)) {
                return;
            }

            if (monitoredSet.get(current) != null && monitoredSet.get(current).
                    compareTo(SharedPreferenceAccessUtils.getExpectedUsage(context, current)) > 0) {
                // push notification
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Foreground", importance);
            channel.setDescription("Foreground service to monitor app usage");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
