package com.devskiller.android.reminder;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReminderDetailsActivity extends AppCompatActivity {
    private TextView reminderTitle;
    private TextView reminderDateTime;
    private CheckBox reminderDone;

    private ReminderRepository repository = ReminderRepository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reminderTitle = findViewById(R.id.reminder_title);
        reminderDateTime = findViewById(R.id.reminder_datetime);
        reminderDone = findViewById(R.id.reminder_done);

        // SOLUTION
    }
}
