package com.gaurav.budgetplanner.features.expensetracker.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account

@Database(
    entities = [Account::class],
    version = 1
)
abstract class TransactionDatabase:RoomDatabase() {
    abstract val transactionDao:TransactionDao

    companion object {
        const val DATABASE_NAME = "transactions_db"
    }
}