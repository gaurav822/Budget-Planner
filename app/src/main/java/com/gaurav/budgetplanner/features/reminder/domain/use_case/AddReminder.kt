package com.gaurav.budgetplanner.features.reminder.domain.use_case

import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.domain.model.InValidTransactionException
import com.gaurav.budgetplanner.features.expensetracker.domain.repository.TransactionRepository
import com.gaurav.budgetplanner.features.reminder.domain.model.InValidReminderException
import com.gaurav.budgetplanner.features.reminder.domain.model.Reminder
import com.gaurav.budgetplanner.features.reminder.domain.repository.ReminderRepository

class AddReminder(
    private val repository: ReminderRepository
) {
    @Throws(InValidReminderException::class)
    suspend operator fun invoke(reminder: Reminder){
        if(reminder.name.isBlank()){
            throw InValidTransactionException("Reminder Name cannot be empty.")
        }

        repository.insertReminder(reminder)
    }
}