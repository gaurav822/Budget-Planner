package com.gaurav.budgetplanner.features.reminder.domain.use_case

data class ReminderUseCases(
    val getReminders: GetReminders,
    val deleteReminder: DeleteReminder,
    val addReminder: AddReminder,
    val getReminder : GetReminder,
    val updateReminder: UpdateReminder
)