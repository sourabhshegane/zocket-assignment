package com.devskiller.android.reminder.test;

import com.devskiller.android.reminder.Reminder;
import com.devskiller.android.reminder.ReminderRepository;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.Resetter;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;

@SuppressWarnings({"UnusedDeclaration"})
@Implements(ReminderRepository.class)
public class ShadowReminderRepository {
    private final List<Reminder> reminders = new LinkedList<>();

    @Implementation
    public Reminder findById(int id) {
        return reminders.stream()
                .filter(reminder -> reminder.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void setBackingCollection(@NonNull List<Reminder> aReminders) {
        reminders.clear();
        reminders.addAll(aReminders);
    }

    @Resetter
    public void reset() {
        reminders.clear();
    }
}
