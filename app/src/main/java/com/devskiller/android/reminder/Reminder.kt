package com.devskiller.android.reminder

import java.io.Serializable

data class Reminder(
    val id: Int = 0,
    val title: String,
    val done: Boolean = false,
    val time: Long = 0L
): Serializable