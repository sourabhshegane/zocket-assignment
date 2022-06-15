package com.devskiller.android.reminder.test;

import com.devskiller.android.reminder.Reminder;

import androidx.annotation.NonNull;

public final class TestReminders {
    public static final int TEST_REMINDER_ID = 300;
    public static final int NOT_EXISTING_REMINDER_ID = 1000;

    private TestReminders() {
        throw new AssertionError("No instance allowed!");
    }

    @NonNull
    public static Reminder done() {
        return new Reminder("Test", System.currentTimeMillis()).setDone(true);
    }

    @NonNull
    public static Reminder done(int reminderId) {
        return new Reminder(reminderId, "Test", System.currentTimeMillis()).setDone(true);
    }

    @NonNull
    public static Reminder pastUndone() {
        return new Reminder("Past", System.currentTimeMillis() - 20000);
    }

    @NonNull
    public static Reminder futureUndone(int id) {
        return new Reminder(id, "Future", System.currentTimeMillis() + 10000);
    }

    @NonNull
    public static Reminder futureUndone() {
        return new Reminder("Future", System.currentTimeMillis() + 10000);
    }
}
