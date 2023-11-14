package com.gaurav.budgetplanner.features.reminder.domain.use_case

import androidx.lifecycle.LiveData
import com.gaurav.budgetplanner.features.reminder.domain.model.Reminder
import com.gaurav.budgetplanner.features.reminder.domain.repository.ReminderRepository

class GetReminders(private val repository:ReminderRepository)
{
    operator fun invoke(): LiveData<List<Reminder>>{
        return repository.getReminders()
    }

}