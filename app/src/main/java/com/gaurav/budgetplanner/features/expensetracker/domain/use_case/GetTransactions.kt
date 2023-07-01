package com.gaurav.budgetplanner.features.expensetracker.domain.use_case

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.domain.repository.TransactionRepository
import com.gaurav.budgetplanner.features.expensetracker.domain.util.TransactionType

class GetTransactions(private val repository:TransactionRepository)
{
    operator fun invoke(transactionType: TransactionType=TransactionType.Expense): LiveData<List<Account>>{
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