package com.gaurav.budgetplanner.features.expensetracker.domain.util

sealed class TransactionType{
    object Expense:TransactionType()
    object Income:TransactionType()
}
