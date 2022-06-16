package com.devskiller.android.reminder.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationManagerCompat
import com.devskiller.android.reminder.Reminder
import com.devskiller.android.reminder.activities.ReminderDetailsActivity

class NotificationActionReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        context?.let {ctx->
            intent?.let {
                val reminderBundle = intent.getParcelableExtra<Bundle>("reminder")
                val reminder = reminderBundle?.getSerializable("reminder") as Reminder?
                reminder?.let {
                    NotificationManagerCompat.from(context).cancel(null, reminder.id)
                    reminder.done = true
                    
                    val bundle = Bundle()
                    bundle.putSerializable("reminder", reminder)

                    val intentToReminderDetailsActivity = Intent(ctx, ReminderDetailsActivity::class.java)
                    intentToReminderDetailsActivity.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
                    intentToReminderDetailsActivity.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    intentToReminderDetailsActivity.action = Intent.ACTION_MAIN
                    intentToReminderDetailsActivity.addCategory(Intent.CATEGORY_LAUNCHER)
                    intentToReminderDetailsActivity.putExtra("reminder", bundle)
                    context.startActivity(intentToReminderDetailsActivity)
                }
            }
        }
    }
}