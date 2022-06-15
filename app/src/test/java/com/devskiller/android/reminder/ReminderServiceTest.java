package com.devskiller.android.reminder;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import com.devskiller.android.reminder.test.TestReminders;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlarmManager;
import org.robolectric.shadows.ShadowPendingIntent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class ReminderServiceTest {
    private ReminderService reminderService;
    private ShadowAlarmManager alarmManager;
    @Spy
    private ReminderRepository spiedRepository = ReminderRepository.getInstance();

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Before
    public void setUp() {
        Application application = ApplicationProvider.getApplicationContext();
        this.alarmManager = shadowOf((AlarmManager) application.getSystemService(Context.ALARM_SERVICE));
    }

    @Test
    public void shouldNotScheduleAlreadyDoneTask() {
        reminderService.scheduleReminder(TestReminders.done());

        assertThat(alarmManager.getScheduledAlarms()).isEmpty();
    }

    @Test
    public void shouldScheduleServiceStartIntent() {
        reminderService.scheduleReminder(TestReminders.futureUndone());

        ShadowPendingIntent scheduledAlarm = shadowOf(alarmManager.getNextScheduledAlarm().operation);
        Intent intent = scheduledAlarm.getSavedIntent();

        assertThat(scheduledAlarm.isServiceIntent()).isTrue();
        assertThat(intent.getComponent())
                .isNotNull()
                .matches(component -> component.getClassName()
                        .equals(ReminderNotificationService.class.getName()));
    }

    @Test
    public void givenUndoneTaskShouldSaveOneAsDone() {
        Reminder testReminder = TestReminders.pastUndone();
        reminderService.markDone(testReminder);

        verify(spiedRepository).save(argThat(reminder -> reminder.isDone() && reminder.getId() == testReminder.getId()));
    }

    @Test
    public void givenDoneTaskShouldNotSaveAtAll() {
        reminderService.markDone(TestReminders.done());

        verifyNoMoreInteractions(spiedRepository);
    }
}
