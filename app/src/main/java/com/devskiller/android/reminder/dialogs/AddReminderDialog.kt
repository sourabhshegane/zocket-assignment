package com.devskiller.android.reminder.dialogs

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.devskiller.android.reminder.R
import com.devskiller.android.reminder.databinding.DialogAddNewReminderBinding

class AddReminderDialog : DialogFragment() {

    private lateinit var dialogAddNewReminderBinding: DialogAddNewReminderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        dialogAddNewReminderBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_add_new_reminder, container, false)
        return dialogAddNewReminderBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
    }

    private fun initUI() {
        dialogAddNewReminderBinding.btnCreateReminder.setOnClickListener {

        }
    }
}