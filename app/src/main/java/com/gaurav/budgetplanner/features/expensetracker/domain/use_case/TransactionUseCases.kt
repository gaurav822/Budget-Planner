package com.gaurav.budgetplanner.features.expensetracker.domain.use_case

data class TransactionUseCases(
    val getTransactions: GetTransactions,
    val deleteTransaction: DeleteTransaction,
    val addTransaction: AddTransaction,
    val getTransaction : GetTransaction,
    val updateTransaction: UpdateTransaction
)