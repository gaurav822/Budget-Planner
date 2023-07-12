package com.gaurav.budgetplanner.Utils

import com.gaurav.budgetplanner.BudgetPlannerApp
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

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

            return "$total"
        }

        fun calculatePercentage(numbers: List<Int>,position:Int): Int {
            val sum = numbers.sum().toDouble()
            val percentages = numbers.map { (it.toDouble() / sum * 100) }

            val roundedPercentages = percentages.map { ceil(it).toInt() }.toMutableList()
            val sumRounded = roundedPercentages.sum()

            if (sumRounded != 100) {
                var diff = sumRounded - 100
                val sortedDiffIndices = roundedPercentages
                    .mapIndexed { index, value -> index to value }
                    .sortedByDescending { (_, value) -> value - value }
                    .map { it.first }

                var i = 0
                while (diff != 0) {
                    val index = sortedDiffIndices[i]
                    roundedPercentages[index]--
                    i = (i + 1) % sortedDiffIndices.size
                    diff--
                }
            }

            return roundedPercentages[position]
        }

        fun getFormattedDateFromMillis(millis: Long, dateFormat: String?): String? {
            var dateFormat = dateFormat
            return try {
                if (dateFormat == null || dateFormat.isEmpty()) dateFormat = "yyyy-MM-dd"
                val inputFormat: SimpleDateFormat = SimpleDateFormat(
                    dateFormat,
                    getEnglishLocale()
                )
                val date = Date()
                date.time = millis
                inputFormat.format(millis)
            } catch (e: Exception) {
                null
            }
        }

        fun getEnglishLocale(): Locale {
            return Locale.US
        }

        fun getSelectedCurrency(isSymbol:Boolean=true):String{
            val data: String? = BudgetPlannerApp.getStorage().getString(if(isSymbol)Constants.PREF_CURRENCY_SYMBOL else Constants.PREF_CURRENCY_VALUE , "")
            return data!!
        }
    }

}