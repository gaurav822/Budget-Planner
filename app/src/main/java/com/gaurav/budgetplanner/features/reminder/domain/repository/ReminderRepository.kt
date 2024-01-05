package com.gaurav.budgetplanner.features.reminder.domain.repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.reminder.domain.model.Reminder

interface ReminderRepository {

    fun getReminders(): LiveData<List<Reminder>>

    suspend fun getReminderById(id:Int): Reminder?

    suspend fun insertReminder(reminder: Reminder):Long

    suspend fun updateReminder(reminder: Reminder)

    suspend fun delete(reminder: Reminder)
    suspend fun updateIsActive(reminderId: Int, isActive: Boolean)
}