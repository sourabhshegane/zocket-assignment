package com.devskiller.android.reminder;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.devskiller.android.reminder.activities.ReminderDetailsActivity;
import com.devskiller.android.reminder.broadcast_receivers.NotificationActionReceiver;
import com.devskiller.android.reminder.utils.NotificationUtils;

public class ReminderNotificationService extends IntentService {

    private final String TAG = "ReminderNotification";

    public ReminderNotificationService() {
        super(ReminderNotificationService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Bundle reminderBundle = intent.getParcelableExtra("reminder");
        Reminder reminder = (Reminder) reminderBundle.getSerializable("reminder");
        if(reminder != null){
            startForeground(12, createNewNotification(reminder));
        }else{
            stopForeground(true);
            Log.d(TAG, "onHandleIntent: Reminder is null");
        }
    }

    private Notification createNewNotification(Reminder reminder) {
        Log.d(TAG, "Creating new notification for " + reminder.getTitle());
        createNotificationChannel();

        Bundle bundle = new Bundle();
        bundle.putSerializable("reminder", reminder);

        Intent intent = new Intent(this, ReminderDetailsActivity.class);
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.putExtra("reminder", bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent broadcastIntent = new Intent(this, NotificationActionReceiver.class);
        broadcastIntent.putExtra("reminder", bundle);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationUtils.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_time)
                .setContentTitle(reminder.getTitle())
                .setContentText(reminder.getTitle())
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .addAction(R.mipmap.ic_launcher, getString(R.string.done), actionIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        Log.d(TAG, "Firing new notification...");
        return builder.build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(NotificationUtils.NOTIFICATION_CHANNEL_ID, NotificationUtils.NOTIFICATION_CHANNEL_NAME, importance);
            channel.setDescription(NotificationUtils.NOTIFICATION_CHANNEL_DESCRIPTION);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
