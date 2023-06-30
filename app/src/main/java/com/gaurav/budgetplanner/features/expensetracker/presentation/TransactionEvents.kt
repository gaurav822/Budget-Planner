package com.gaurav.budgetplanner.features.expensetracker.presentation

import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.domain.util.TransactionType

sealed class TransactionEvents {
    data class ChangeTransaction(val transactionType: TransactionType):TransactionEvents()
    data class DeleteTransaction(val transaction: Account):TransactionEvents()
    object RestoreTransaction:TransactionEvents()

}
