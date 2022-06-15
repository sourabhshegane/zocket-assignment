package com.devskiller.android.reminder;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class ReminderNotificationService extends IntentService {
    public ReminderNotificationService() {
        super(ReminderNotificationService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // SOLUTION
    }
}
