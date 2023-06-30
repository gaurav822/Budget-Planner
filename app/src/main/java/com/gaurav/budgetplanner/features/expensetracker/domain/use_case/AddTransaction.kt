package com.gaurav.budgetplanner.features.expensetracker.domain.use_case

import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.domain.model.InValidTransactionException
import com.gaurav.budgetplanner.features.expensetracker.domain.repository.TransactionRepository

class AddTransaction(
    private val repository: TransactionRepository
) {

    @Throws(InValidTransactionException::class)
    suspend operator fun invoke(transaction: Account){
        if(transaction.amount.isBlank()){
            throw InValidTransactionException("Amount cannot be empty.")
        }

        if(transaction.category.isBlank()){
            throw InValidTransactionException("User must select category")
        }

        repository.insertTransaction(transaction)
    }
}