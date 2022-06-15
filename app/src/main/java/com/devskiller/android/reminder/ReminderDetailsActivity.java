package com.devskiller.android.reminder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.devskiller.android.reminder.databinding.ActivityMainBinding;

public class ReminderDetailsActivity extends AppCompatActivity {

    private ReminderRepository repository = ReminderRepository.getInstance();
    private ActivityMainBinding activityMainBinding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initUI();
    }

    private void initUI() {

        activityMainBinding.btnAddReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
