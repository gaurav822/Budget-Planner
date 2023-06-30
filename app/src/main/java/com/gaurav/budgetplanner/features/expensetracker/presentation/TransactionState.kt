package com.gaurav.budgetplanner.features.expensetracker.presentation

import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.domain.util.TransactionType

data class TransactionState(
    val transactions:List<Account> = emptyList(),
    val transactionType: TransactionType = TransactionType.Expense
)
