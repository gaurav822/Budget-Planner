package com.gaurav.budgetplanner.features.expensetracker.domain.use_case

import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.domain.repository.TransactionRepository
import com.gaurav.budgetplanner.features.expensetracker.domain.util.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTransactions(private val repository:TransactionRepository)
{
    operator fun invoke(transactionType: TransactionType=TransactionType.Expense): Flow<List<Account>>{
        return repository.getTransactions().map {
            trx ->
            when(transactionType){
                TransactionType.Expense -> {
                    trx.filter { 
                        transaction ->  transaction.transactionType=="E"
                    }
                }
                TransactionType.Income -> {
                    trx.filter {
                            transaction ->  transaction.transactionType=="I"
                    }
                }
            }
        }
    }

}