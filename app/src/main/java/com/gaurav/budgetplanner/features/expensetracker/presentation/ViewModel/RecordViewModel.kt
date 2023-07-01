package com.gaurav.budgetplanner.features.expensetracker.presentation.ViewModel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.domain.use_case.TransactionUseCases
import com.gaurav.budgetplanner.features.expensetracker.domain.util.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
 class RecordViewModel @Inject constructor(val useCases: TransactionUseCases):ViewModel() {

    val allRecords = useCases.getTransactions(transactionType = TransactionType.Expense)

    fun deleteRecord(record:Account) = viewModelScope.launch(Dispatchers.IO) {
        useCases.deleteTransaction(record)
    }

    fun updateRecord(record: Account) = viewModelScope.launch(Dispatchers.IO) {
        useCases.addTransaction(record)
    }

    fun addRecord(record: Account) = viewModelScope.launch(Dispatchers.IO) {
        useCases.addTransaction(record)
    }


}