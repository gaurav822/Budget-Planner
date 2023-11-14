package com.gaurav.budgetplanner.features.reminder.data.repository

import androidx.lifecycle.LiveData
import com.gaurav.budgetplanner.features.expensetracker.data.data_source.TransactionDao
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.domain.repository.TransactionRepository
import com.gaurav.budgetplanner.features.reminder.data.data_source.ReminderDao
import com.gaurav.budgetplanner.features.reminder.domain.model.Reminder
import com.gaurav.budgetplanner.features.reminder.domain.repository.ReminderRepository

class ReminderRepositoryImpl
    (
    private val dao: ReminderDao
): ReminderRepository {
    override fun getReminders(): LiveData<List<Reminder>> {
       return dao.getReminders()
    }

    override suspend fun getReminderById(id: Int): Reminder? {
        return dao.getReminderById(id)
    }

    override suspend fun insertReminder(reminder: Reminder) {
       dao.insertReminder(reminder)
    }

    override suspend fun updateReminder(reminder: Reminder) {
        dao.updateReminder(reminder)
    }

    override suspend fun delete(reminder: Reminder) {
        dao.delete(reminder)
    }


}