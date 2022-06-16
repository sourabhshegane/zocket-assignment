package com.devskiller.android.reminder.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.devskiller.android.reminder.Reminder
import com.devskiller.android.reminder.ReminderNotificationService

class AlarmBroadcastReceiver: BroadcastReceiver() {

    private val TAG = "AlarmBroadcastReceiver";

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive: ")

        val reminderBundle: Bundle? = intent?.getParcelableExtra("reminder")
        val reminder: Reminder? = reminderBundle?.getSerializable("reminder") as Reminder?

        reminder?.let {
            val reminderNotificationService = Intent(context, ReminderNotificationService::class.java)
            Log.d(TAG, "onReceive Data is: ${reminder.title}")
            reminderNotificationService.putExtra("reminder", reminderBundle)
            context?.let {
                ContextCompat.startForegroundService(context, reminderNotificationService)
            }
        }
    }
}