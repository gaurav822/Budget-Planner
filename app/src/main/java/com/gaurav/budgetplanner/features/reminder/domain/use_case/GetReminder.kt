package com.gaurav.budgetplanner.features.reminder.domain.use_case

import com.gaurav.budgetplanner.features.reminder.domain.model.Reminder
import com.gaurav.budgetplanner.features.reminder.domain.repository.ReminderRepository


class GetReminder(
    private val repository: ReminderRepository
) {

    suspend operator fun invoke(id: Int): Reminder? {
        return repository.getReminderById(id)
    }
}