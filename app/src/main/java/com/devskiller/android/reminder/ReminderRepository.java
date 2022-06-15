package com.devskiller.android.reminder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ReminderRepository {
    private static ReminderRepository instance = new ReminderRepository();

    public static ReminderRepository getInstance() {
        return instance;
    }

    private ReminderRepository() {
    }

    @Nullable
    public Reminder findById(int id) {
        return null;
    }

    public void save(@NonNull Reminder reminder) {
        // no-op
    }
}
