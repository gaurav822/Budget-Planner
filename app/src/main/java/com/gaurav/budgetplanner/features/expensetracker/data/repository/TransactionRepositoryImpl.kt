package com.gaurav.budgetplanner.features.expensetracker.data.repository

import com.gaurav.budgetplanner.features.expensetracker.data.data_source.TransactionDao
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class TransactionRepositoryImpl
    (
    private val dao: TransactionDao
            ):TransactionRepository {

    override fun getTransactions(): Flow<List<Account>> {
        return dao.getTransaction()
    }

    override suspend fun getTrxByID(id: Int): Account? {
       return dao.getTrxById(id)
    }

    override suspend fun insertTransaction(transaction: Account) {
        dao.insertTrx(transaction)
    }

    override suspend fun deleteTrx(transaction: Account) {
        dao.deleteTrx(transaction)
    }
}