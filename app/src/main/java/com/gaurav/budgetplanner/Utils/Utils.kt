package com.gaurav.budgetplanner.Utils

import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account

class Utils {

    companion object {
        fun getTotalBudget(data:List<Account>):String {
            var expenseSum = 0
            var incomeSum = 0
            var total = 0

            expenseSum += data.filter { transaction ->  transaction.transactionType=="E" }.sumOf {
                it.amount.toInt()
            }
            incomeSum += data.filter { transaction ->  transaction.transactionType=="I" }.sumOf {
                it.amount.toInt()
            }
            total = incomeSum - expenseSum

            return "NRs $total"
        }
    }
}