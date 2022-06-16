package com.devskiller.android.reminder;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.devskiller.android.reminder.callbacks.OnAddNewReminderDialogActions;
import com.devskiller.android.reminder.databinding.ActivityMainBinding;
import com.devskiller.android.reminder.dialogs.AddReminderDialog;

public class ReminderDetailsActivity extends AppCompatActivity implements OnAddNewReminderDialogActions {

    private ReminderRepository repository = ReminderRepository.getInstance();
    private ActivityMainBinding activityMainBinding = null;
    private ReminderService reminderService = null;
    private AddReminderDialog addReminderDialog;
    private final String TAG = "ReminderDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initUI();
        reminderService = new ReminderService(this, repository);
    }

    private void initUI() {
        activityMainBinding.btnAddReminder.setOnClickListener(v -> {
            addReminderDialog = new AddReminderDialog(this, ReminderDetailsActivity.this);
            addReminderDialog.show(getSupportFragmentManager(), "");
        });
    }

    @Override
    public void onNewReminderCreated(@NonNull Reminder reminder) {
        Log.d(TAG, "onNewReminderCreated: ");
        reminderService.scheduleReminder(reminder);
        addReminderDialog.dismiss();
    }

    @Override
    public void onCancelled() {
        Log.d(TAG, "onCancelled:");
    }
}
