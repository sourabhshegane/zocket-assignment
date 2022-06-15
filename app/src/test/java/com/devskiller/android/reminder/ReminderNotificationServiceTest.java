package com.devskiller.android.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.devskiller.android.reminder.test.ShadowReminderRepository;
import com.devskiller.android.reminder.test.ShadowReminderService;
import com.devskiller.android.reminder.test.TestReminders;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowNotificationManager;

import java.util.Collections;
import java.util.Optional;

import androidx.annotation.NonNull;
import androidx.test.core.app.ApplicationProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class ReminderNotificationServiceTest {
    private ReminderNotificationService service;

    private ShadowNotificationManager shadowNotificationManager;

    @Before
    public void setup() {
        this.service = Robolectric.buildService(ReminderNotificationService.class).create().get();

        this.shadowNotificationManager =
                shadowOf((NotificationManager) ApplicationProvider.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE));
        ShadowReminderRepository shadowReminderRepository = Shadow.extract(ReminderRepository.getInstance());

        shadowReminderRepository.setBackingCollection(
                Collections.singletonList(TestReminders.futureUndone(
                        TestReminders.TEST_REMINDER_ID)));
    }

    @NonNull
    private Intent serviceIntentWithReminderId(int id) {
        return new Intent(ApplicationProvider.getApplicationContext(), ReminderNotificationService.class)
                .putExtra("id", id);
    }

    @Test
    public void shouldNotDisplayNotificationWhenReminderForIdWasNotFound() {
        service.onHandleIntent(serviceIntentWithReminderId(TestReminders.NOT_EXISTING_REMINDER_ID));

        assertThat(shadowNotificationManager.size()).isZero();
    }

    @Test
    public void shouldScheduleNotificationForExistingReminderId() {
        service.onHandleIntent(serviceIntentWithReminderId(TestReminders.TEST_REMINDER_ID));

        assertThat(shadowNotificationManager.size()).isOne();
    }

    @Test
    public void shouldHaveContentIntentSet() {
        service.onHandleIntent(serviceIntentWithReminderId(TestReminders.TEST_REMINDER_ID));

        Notification notification = shadowNotificationManager.getAllNotifications().get(0);
        assertThat(notification.contentIntent).isNotNull();
    }

    @Test
    public void shouldHaveDoneAction() {
        service.onHandleIntent(serviceIntentWithReminderId(TestReminders.TEST_REMINDER_ID));

        Notification notification = shadowNotificationManager.getAllNotifications().get(0);

        Notification.Action action = Optional.ofNullable(notification.actions)
                .filter(actions -> actions.length == 1)
                .map(actions -> actions[0])
                .orElse(null);

        assertThat(action).isNotNull();
        assertThat(action.title).isEqualToIgnoringCase("Done");
    }
}
