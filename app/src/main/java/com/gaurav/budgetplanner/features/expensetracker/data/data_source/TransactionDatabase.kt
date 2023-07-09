package com.gaurav.budgetplanner.features.expensetracker.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account

@Database(
    entities = [Account::class],
    version = 2,
    exportSchema = false
)
abstract class TransactionDatabase:RoomDatabase() {
    abstract val transactionDao:TransactionDao

    companion object {
        const val DATABASE_NAME = "transactions_db"

        @Volatile
        private var INSTANCE:TransactionDatabase?=null
    }
}