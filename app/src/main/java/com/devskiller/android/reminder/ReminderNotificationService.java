package com.devskiller.android.reminder;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.devskiller.android.reminder.broadcast_receivers.NotificationActionReceiver;

public class ReminderNotificationService extends IntentService {
    public ReminderNotificationService() {
        super(ReminderNotificationService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Reminder reminder = (Reminder) intent.getSerializableExtra("reminder");
        if(reminder != null){
            createNewNotification(reminder);
        }
    }

    private void createNewNotification(Reminder reminder) {
        createNotificationChannel();

        Intent intent = new Intent(this, ReminderDetailsActivity.class);
        intent.putExtra("reminder", reminder);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Intent broadcastIntent = new Intent(this, NotificationActionReceiver.class);
        broadcastIntent.putExtra("reminder", reminder);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.abc_vector_test)
                .setContentTitle(reminder.getTitle())
                .setContentText(reminder.getTitle())
                .setContentIntent(pendingIntent)
                .addAction(R.mipmap.ic_launcher, "Mark as Done", actionIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(reminder.getId(), builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("ZOCKET_REMINDER_SERVICE", "ZRS", importance);
            channel.setDescription("ZOCKET_REMINDER_SERVICE");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
