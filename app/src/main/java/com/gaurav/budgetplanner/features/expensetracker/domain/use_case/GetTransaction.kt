package com.gaurav.budgetplanner.features.expensetracker.domain.use_case

import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.domain.repository.TransactionRepository


class GetTransaction(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(id: Int): Account? {
        return repository.getTrxByID(id)
    }
}