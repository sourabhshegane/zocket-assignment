package com.devskiller.android.reminder.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.devskiller.android.reminder.ReminderNotificationService

class AlarmBroadcastReceiver: BroadcastReceiver() {

    private val TAG = "AlarmBroadcastReceiver";

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive: ")
        val reminderNotificationService = Intent(context, ReminderNotificationService::class.java)
        val reminder = reminderNotificationService.getSerializableExtra("reminder")
        reminderNotificationService.putExtra("reminder", reminder)
        context?.startService(reminderNotificationService)
    }
}