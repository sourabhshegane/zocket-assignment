package com.devskiller.android.reminder.dialogs

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.devskiller.android.reminder.*
import com.devskiller.android.reminder.callbacks.OnAddNewReminderDialogActions
import com.devskiller.android.reminder.databinding.DialogAddNewReminderBinding
import java.util.*


class AddReminderDialog(
    private val ctx: Context,
    private val onAddNewReminderDialogActions: OnAddNewReminderDialogActions
) : DialogFragment() {

    private lateinit var binding: DialogAddNewReminderBinding
    private var selectedEpoch: Long = -1
    private val TAG = "AddReminderDialog"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_add_new_reminder, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
    }

    private fun initUI() {

        binding.ibSelectTime.setOnClickListener {
            timePicker.show()
        }

        binding.btnCreateReminder.setOnClickListener {

            val enteredTitle = binding.edReminderTitle.text.toString()

            if (!isEnteredDataCorrect(enteredTitle)) return@setOnClickListener

            Log.d(TAG, "Selected epoch is: $selectedEpoch")
            val newReminder = Reminder(
                1,
                title = enteredTitle,
                time = selectedEpoch
            )

            onAddNewReminderDialogActions.onNewReminderCreated(newReminder)
        }

        binding.btnCancel.setOnClickListener {
            onAddNewReminderDialogActions.onCancelled()
            dismiss()
        }

        binding.edReminderTime.setOnClickListener {
            timePicker.show()
        }
    }

    private fun isEnteredDataCorrect(enteredTitle: String): Boolean {
        if (enteredTitle.isNullOrEmpty()) {
            binding.edReminderTitle.error = getString(R.string.enter_valid_title)
            return false
        }

        if (selectedEpoch == -1L) {
            binding.edReminderTime.error = "Please select time"
            return false
        }

        return true
    }

    private val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val formattedTime: String = when {
                hourOfDay == 0 -> {
                    if (minute < 10) {
                        "${hourOfDay + 12}:0${minute} am"
                    } else {
                        "${hourOfDay + 12}:${minute} am"
                    }
                }
                hourOfDay > 12 -> {
                    if (minute < 10) {
                        "${hourOfDay - 12}:0${minute} pm"
                    } else {
                        "${hourOfDay - 12}:${minute} pm"
                    }
                }
                hourOfDay == 12 -> {
                    if (minute < 10) {
                        "${hourOfDay}:0${minute} pm"
                    } else {
                        "${hourOfDay}:${minute} pm"
                    }
                }
                else -> {
                    if (minute < 10) {
                        "${hourOfDay}:${minute} am"
                    } else {
                        "${hourOfDay}:${minute} am"
                    }
                }
            }

            val calendar: Calendar = getEpochOfSelectedTime(hourOfDay, minute)
            selectedEpoch = calendar.timeInMillis
            binding.edReminderTime.setText(formattedTime)
        }

    private fun getEpochOfSelectedTime(hourOfDay: Int, minute: Int): Calendar {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR))
        calendar.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH))
        calendar.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        return calendar
    }

    private val timePicker: TimePickerDialog = TimePickerDialog(
        ctx,
        timePickerDialogListener,
        12,
        10,
        false
    )

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            it.window?.setLayout(width, height)
        }
    }
}