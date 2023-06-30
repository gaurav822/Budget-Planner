package com.gaurav.budgetplanner.features.expensetracker.domain.use_case

import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.domain.repository.TransactionRepository

class DeleteTransaction(private val repository:TransactionRepository) {

    suspend operator fun invoke(transaction: Account){
        repository.deleteTrx(transaction)
    }
}