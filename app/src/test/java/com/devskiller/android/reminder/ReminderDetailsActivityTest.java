package com.devskiller.android.reminder;

import android.content.Intent;
import android.os.Build;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;

import com.devskiller.android.reminder.test.ShadowReminderRepository;
import com.devskiller.android.reminder.test.TestReminders;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowToast;

import java.text.DateFormat;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class ReminderDetailsActivityTest {
    private ShadowReminderRepository shadowRepository;
    private ReminderDetailsActivity activity;
    private Reminder usedReminder;

    @Before
    public void setUp() {
        this.shadowRepository = Shadow.extract(ReminderRepository.getInstance());
        setupRepositoryWithReminder(TestReminders.futureUndone(TestReminders.TEST_REMINDER_ID));
        setupActivityForReminderId(TestReminders.TEST_REMINDER_ID);
    }

    private void setupRepositoryWithReminder(Reminder savedReminder) {
        this.usedReminder = savedReminder;
        this.shadowRepository.setBackingCollection(Collections.singletonList(savedReminder));
    }

    private void setupActivityForReminderId(int reminderId) {
        this.activity = Robolectric.buildActivity(ReminderDetailsActivity.class,
                new Intent(ApplicationProvider.getApplicationContext(), ReminderDetailsActivity.class)
                        .putExtra("id", reminderId))
                .setup()
                .get();
    }

    @Test
    public void shouldMarkCheckboxForDoneReminder() {
        setupRepositoryWithReminder(TestReminders.done(TestReminders.TEST_REMINDER_ID));
        setupActivityForReminderId(TestReminders.TEST_REMINDER_ID);
        CheckBox doneCheckBox = activity.findViewById(R.id.reminder_done);

        assertThat(doneCheckBox.isChecked()).isTrue();
    }

    @Test
    public void shouldDisplayReminderTitle() {
        TextView title = activity.findViewById(R.id.reminder_title);

        assertThat(title.getText().toString()).isEqualToIgnoringCase(usedReminder.getTitle());
    }

    @Test
    public void shouldDisplayReminderDateAndTime() {
        TextView dateAndTime = activity.findViewById(R.id.reminder_datetime);

        assertThat(dateAndTime.getText().toString())
                .isEqualTo(DateFormat.getDateTimeInstance().format(usedReminder.getWhen()));
    }

    @Test
    public void shouldShowToastWhenReminderNotFound() {
        setupActivityForReminderId(TestReminders.NOT_EXISTING_REMINDER_ID);

        assertThat(ShadowToast.showedToast(activity.getString(R.string.reminder_not_found_error)))
                .isTrue();
    }
}
