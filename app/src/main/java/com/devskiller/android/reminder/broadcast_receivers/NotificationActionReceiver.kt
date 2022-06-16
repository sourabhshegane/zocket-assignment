package com.devskiller.android.reminder.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationManagerCompat
import com.devskiller.android.reminder.Reminder

class NotificationActionReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        context?.let {
            intent?.let {
                val reminderBundle = intent.getParcelableExtra<Bundle>("reminder")
                val reminder = reminderBundle?.getSerializable("reminder") as Reminder?
                reminder?.let {
                    NotificationManagerCompat.from(context).cancel(null, reminder.id)
                }
            }
        }
    }
}