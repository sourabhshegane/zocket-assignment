package com.devskiller.android.reminder.callbacks

import com.devskiller.android.reminder.Reminder

interface OnAddNewReminderDialogActions {

    fun onNewReminderCreated(reminder: Reminder)

    fun onCancelled()
}