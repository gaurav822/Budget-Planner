package com.gaurav.budgetplanner.features.expensetracker.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.reminder.data.data_source.ReminderDao
import com.gaurav.budgetplanner.features.reminder.domain.model.Reminder

@Database(
    entities = [Account::class,Reminder::class],
    version = 4,
    exportSchema = false
)
abstract class TransactionDatabase:RoomDatabase() {
    abstract val transactionDao:TransactionDao

    abstract val reminderDao:ReminderDao

    companion object {
        const val DATABASE_NAME = "transactions_db"

        @Volatile
        private var INSTANCE:TransactionDatabase?=null
    }
}