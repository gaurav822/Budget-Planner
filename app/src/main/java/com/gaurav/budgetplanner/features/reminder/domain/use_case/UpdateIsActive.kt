package com.gaurav.budgetplanner.features.reminder.domain.use_case

import com.gaurav.budgetplanner.features.reminder.domain.repository.ReminderRepository

class GetUpdateIsActive(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(id: Int,isActive:Boolean) {
        return repository.updateIsActive(id,isActive)
    }
}