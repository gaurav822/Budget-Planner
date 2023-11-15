package com.gaurav.budgetplanner.features.reminder.presentation.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.domain.use_case.TransactionUseCases
import com.gaurav.budgetplanner.features.reminder.domain.model.Reminder
import com.gaurav.budgetplanner.features.reminder.domain.use_case.ReminderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(private val useCases: ReminderUseCases): ViewModel() {

    fun deleteRecord(record: Reminder) = viewModelScope.launch(Dispatchers.IO) {
        useCases.deleteReminder(record)
    }

    fun getAllRecords(): LiveData<List<Reminder>> {
        return useCases.getReminders()
    }

    fun updateRecord(record: Reminder) = viewModelScope.launch(Dispatchers.IO) {
        useCases.updateReminder(record)
    }

    fun addRecord(record: Reminder) = viewModelScope.launch(Dispatchers.IO) {
        useCases.addReminder(record)
    }

    fun updateChecked(id: Int,isChecked:Boolean) = viewModelScope.launch(Dispatchers.IO) {
        useCases.updateIsActive(id,isChecked)
    }
}