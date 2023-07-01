package com.gaurav.budgetplanner.features.expensetracker.presentation.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gaurav.budgetplanner.features.expensetracker.domain.use_case.TransactionUseCases
import javax.inject.Inject

class RecordViewModelFactory @Inject constructor(private val useCases: TransactionUseCases) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecordViewModel::class.java)) {
            return RecordViewModel(useCases) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}