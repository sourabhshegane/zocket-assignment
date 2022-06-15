package com.devskiller.android.reminder.test;

import com.devskiller.android.reminder.Reminder;
import com.devskiller.android.reminder.ReminderService;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;
import org.robolectric.annotation.Resetter;

import java.util.LinkedList;
import java.util.Queue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static org.robolectric.shadow.api.Shadow.directlyOn;

@Implements(ReminderService.class)
@SuppressWarnings({"UnusedDeclaration"})
public class ShadowReminderService {
    @RealObject
    private ReminderService originalService;
    private static final Queue<Reminder> callsForMarkDone = new LinkedList<>();

    @Implementation
    public void markDone(@NonNull Reminder reminder) {
        callsForMarkDone.offer(reminder);
        directlyOn(originalService, ReminderService.class).markDone(reminder);
    }

    @Nullable
    public static Reminder pollLastMarkDoneCall() {
        return callsForMarkDone.poll();
    }

    @Resetter
    public static void reset() {
        callsForMarkDone.clear();
    }
}
