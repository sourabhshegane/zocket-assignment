package com.devskiller.android.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.devskiller.android.reminder.callbacks.OnAddNewReminderDialogActions;
import com.devskiller.android.reminder.databinding.ActivityMainBinding;
import com.devskiller.android.reminder.dialogs.AddReminderDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReminderDetailsActivity extends AppCompatActivity implements OnAddNewReminderDialogActions {

    private ReminderRepository repository = ReminderRepository.getInstance();
    private ActivityMainBinding binding = null;
    private ReminderService reminderService = null;
    private AddReminderDialog addReminderDialog;
    private final String TAG = "ReminderDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initUI();
        reminderService = new ReminderService(getApplicationContext(), repository);

        if (isDataAvailableInIntent(getIntent())) {
            Reminder reminder = getReminderDataFromIntent(getIntent());
            populateFieldsWithData(reminder);
        } else {
            Toast.makeText(getApplicationContext(), R.string.reminder_not_found_error, Toast.LENGTH_SHORT).show();
        }

      /*  Reminder reminder = new Reminder(1, "MM", false, System.currentTimeMillis());
        reminderService.scheduleReminder(reminder);*/
    }

    private Reminder getReminderDataFromIntent(Intent intent) {
        Bundle reminderBundle = intent.getParcelableExtra("reminder");
        Reminder reminder = (Reminder) reminderBundle.getSerializable("reminder");
        return reminder;
    }

    private boolean isDataAvailableInIntent(Intent intent) {
        if(intent == null) return false;
        if(!intent.hasExtra("reminder")) return false;

        return true;
    }

    private void populateFieldsWithData(Reminder reminder) {
        binding.reminderTitle.setText(reminder.getTitle());
        binding.reminderDatetime.setText(getReadableDateFromEpoch(reminder.getTime()));
        binding.cbReminderDone.setChecked(reminder.getDone());
    }

    private String getReadableDateFromEpoch(long epoch){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        return sdf.format(new Date(epoch));
    }

    private void initUI() {

        binding.cbReminderDone.setEnabled(false);

        binding.btnAddReminder.setOnClickListener(v -> {
            addReminderDialog = new AddReminderDialog(this, ReminderDetailsActivity.this);
            addReminderDialog.show(getSupportFragmentManager(), "");

         /*   //TODO: Remove this hardcoded reminder
            Reminder reminder = new Reminder(1, "MM", false, System.currentTimeMillis());
            reminderService.scheduleReminder(reminder);*/
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
