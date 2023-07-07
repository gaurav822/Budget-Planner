package com.gaurav.budgetplanner.features.expensetracker.presentation.ViewModel
import android.app.Application
import androidx.lifecycle.*
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.domain.use_case.TransactionUseCases
import com.gaurav.budgetplanner.features.expensetracker.domain.util.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
 class RecordViewModel @Inject constructor(private val useCases: TransactionUseCases):ViewModel() {

    private val _amount = MutableLiveData<Int>()
    val amount: LiveData<Int>
        get() = _amount

    private var amt: Int = 0

    init {
        _amount.value = amt
    }

    fun deleteRecord(record:Account) = viewModelScope.launch(Dispatchers.IO) {
        useCases.deleteTransaction(record)
    }

    fun getAllRecords():LiveData<List<Account>>{
        return useCases.getTransactions()
    }

    fun updateRecord(record: Account) = viewModelScope.launch(Dispatchers.IO) {
        useCases.addTransaction(record)
    }

    fun addRecord(record: Account) = viewModelScope.launch(Dispatchers.IO) {
        useCases.addTransaction(record)
    }
}