package com.gaurav.budgetplanner.features.reminder.presentation.ViewModel

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaurav.budgetplanner.Utils.Utils
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.domain.use_case.TransactionUseCases
import com.gaurav.budgetplanner.features.reminder.Service.NotificationBroadCastReceiver
import com.gaurav.budgetplanner.features.reminder.Service.NotificationService
import com.gaurav.budgetplanner.features.reminder.domain.model.Reminder
import com.gaurav.budgetplanner.features.reminder.domain.use_case.ReminderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(private val useCases: ReminderUseCases): ViewModel() {

    fun deleteRecord(record: Reminder) = viewModelScope.launch(Dispatchers.IO) {
        useCases.deleteReminder(record)
    }

    fun getAllRecords(): LiveData<List<Reminder>> {
        return useCases.getReminders()
    }

    fun updateRecord(record: Reminder) = viewModelScope.launch(Dispatchers.IO) {
        useCases.updateReminder(record)
    }

    fun addRecord(record: Reminder) = viewModelScope.launch(Dispatchers.IO) {
        useCases.addReminder(record)
    }

    fun updateChecked(id: Int,isChecked:Boolean) = viewModelScope.launch(Dispatchers.IO) {
        useCases.updateIsActive(id,isChecked)
    }


    @SuppressLint("UnspecifiedImmutableFlag")
    fun scheduleAlarm(context: Context, reminder: Reminder) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Create an intent that will be triggered when the alarm fires
        val intent = Intent(context, NotificationBroadCastReceiver::class.java)
        intent.putExtra("REMINDER_ID", reminder.id) // You can pass the reminder ID to identify which reminder triggered the alarm
        intent.putExtra(NotificationService.EXTRA_TITLE,reminder.name)
        intent.putExtra(NotificationService.EXTRA_DESCRIPTION,reminder.comment)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            Utils.generateUniqueRequestCode(reminder.id), // Use a unique ID for each alarm
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Set the alarm time
        val calendar = Calendar.getInstance().apply {
            timeInMillis = reminder.date
            set(Calendar.HOUR_OF_DAY, reminder.hour)
            set(Calendar.MINUTE, reminder.minute)
            set(Calendar.SECOND, 0)
        }

        // Schedule the alarm
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

        Log.d("AlarmDebug", "Reminder date: ${reminder.date}, hour: ${reminder.hour}, minutes: ${reminder.minute}")

    }


    fun cancelAlarm(context: Context, reminder: Reminder) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Create the same intent that was used to schedule the alarm
        val intent = Intent(context, NotificationBroadCastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            Utils.generateUniqueRequestCode(reminder.id),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Cancel the alarm
        alarmManager.cancel(pendingIntent)
    }


}