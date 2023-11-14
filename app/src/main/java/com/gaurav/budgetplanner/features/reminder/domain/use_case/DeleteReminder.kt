package com.gaurav.budgetplanner.features.reminder.domain.use_case

import com.gaurav.budgetplanner.features.reminder.domain.model.Reminder
import com.gaurav.budgetplanner.features.reminder.domain.repository.ReminderRepository

class DeleteReminder(private val repository:ReminderRepository) {

    suspend operator fun invoke(reminder: Reminder){
        repository.delete(reminder)
    }
}