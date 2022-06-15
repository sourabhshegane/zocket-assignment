package com.devskiller.android.reminder.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.devskiller.android.reminder.Reminder

class NotificationActionReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val reminderToBeMarkedAsDone = intent?.getSerializableExtra("reminder") as Reminder
        //TODO Update the id of this reminder as done
    }
}