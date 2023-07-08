package com.gaurav.budgetplanner.features.expensetracker.domain.repository

import androidx.lifecycle.LiveData
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    fun getTransactions():LiveData<List<Account>>

    suspend fun getTrxByID(id:Int):Account?

    suspend fun insertTransaction(transaction: Account)

    suspend fun updateTransaction(transaction: Account)

    suspend fun deleteTrx(transaction: Account)
}