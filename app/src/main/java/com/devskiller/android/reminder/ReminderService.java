package com.devskiller.android.reminder;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.devskiller.android.reminder.broadcast_receivers.AlarmBroadcastReceiver;

public class ReminderService {
    private Context context;
    private ReminderRepository reminderRepository;
    private final String TAG = "ReminderService";

    public ReminderService(@NonNull Context context, @NonNull ReminderRepository reminderRepository) {
        this.context = context;
        this.reminderRepository = reminderRepository;
    }

    public void scheduleReminder(@NonNull Reminder reminder) {
        // SOLUTION
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra("reminder_details", reminder);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, reminder.getId(), intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, reminder.getWhen(), pendingIntent);
        reminderRepository.save(reminder);
        Log.d(TAG, "Scheduled a new reminder: ");
    }

    public void markDone(@NonNull Reminder reminder) {
        // SOLUTION
    }
}
