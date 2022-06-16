package com.devskiller.android.reminder

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class Reminder(
    val id: Int = 0,
    val title: String,
    val done: Boolean = false,
    val time: Long = 0L
): Serializable