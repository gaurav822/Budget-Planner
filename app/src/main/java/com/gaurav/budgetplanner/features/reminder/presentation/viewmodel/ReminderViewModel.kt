package com.gaurav.budgetplanner.features.reminder.presentation.viewmodel

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaurav.budgetplanner.features.reminder.Service.NotificationBroadCastReceiver
import com.gaurav.budgetplanner.features.reminder.Service.NotificationService
import com.gaurav.budgetplanner.features.reminder.domain.model.Reminder
import com.gaurav.budgetplanner.features.reminder.domain.use_case.ReminderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
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

    suspend fun addRecord(record: Reminder): Long {
        return try {
            val deferredId = viewModelScope.async(Dispatchers.IO) {
                useCases.addReminder(record)
            }
            deferredId.await()
        } catch (e: Exception) {
            Log.d("EXCEPT", e.toString())
            -1 // or any default value to indicate an error
        }
    }

    fun updateChecked(id: Int,isChecked:Boolean) = viewModelScope.launch(Dispatchers.IO) {
             useCases.updateIsActive(id, isChecked)
    }

    // Define a function to handle the broadcast


    @SuppressLint("UnspecifiedImmutableFlag")
    fun scheduleAlarm(context: Context, reminder: Reminder) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Create an intent that will be triggered when the alarm fires
        val intent = Intent(context, NotificationBroadCastReceiver::class.java)

        intent.putExtra(NotificationService.EXTRA_ID, reminder.id.toInt()) // You can pass the reminder ID to identify which reminder triggered the alarm
        intent.putExtra(NotificationService.EXTRA_TITLE,reminder.name)
        intent.putExtra(NotificationService.EXTRA_DESCRIPTION,reminder.comment)

        intent.action = NotificationService.ACTION_NOTIFICATION_SHOWN

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.id.toInt(), // Use a unique ID for each alarm
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
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

        intent.putExtra(NotificationService.EXTRA_ID, reminder.id) // You can pass the reminder ID to identify which reminder triggered the alarm
        intent.putExtra(NotificationService.EXTRA_TITLE,reminder.name)
        intent.putExtra(NotificationService.EXTRA_DESCRIPTION,reminder.comment)

        intent.action = NotificationService.ACTION_NOTIFICATION_SHOWN

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Cancel the alarm
        alarmManager.cancel(pendingIntent)
    }
}
