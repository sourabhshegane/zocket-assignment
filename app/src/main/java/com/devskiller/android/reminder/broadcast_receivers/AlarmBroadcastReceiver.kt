package com.devskiller.android.reminder.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.devskiller.android.reminder.ReminderNotificationService

class AlarmBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val reminderNotificationService = Intent(context, ReminderNotificationService::class.java)
        val reminder = reminderNotificationService.getSerializableExtra("reminder")
        reminderNotificationService.putExtra("reminder", reminder)
        context?.startService(reminderNotificationService)
    }
}