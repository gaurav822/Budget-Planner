package com.gaurav.budgetplanner.features.expensetracker.presentation.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.domain.use_case.TransactionUseCases
import com.gaurav.budgetplanner.features.expensetracker.domain.util.TransactionType
import com.gaurav.budgetplanner.features.expensetracker.presentation.TransactionEvents
import com.gaurav.budgetplanner.features.expensetracker.presentation.TransactionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases

):ViewModel(){

    private val _state = mutableStateOf<TransactionState>(TransactionState())
    private var getTrxJob:Job? = null

    val state : State<TransactionState> = _state
    private var recentlyDeletedTrx:Account?=null

    init {
        getTransaction(TransactionType.Expense)
    }

    fun onEvent(event:TransactionEvents){
        when(event) {
            is TransactionEvents.ChangeTransaction -> {
                if(state.value.transactionType::class == event.transactionType::class){
                    return
                }

                getTransaction(event.transactionType)
            }

            is TransactionEvents.DeleteTransaction -> {
                viewModelScope.launch {
                    transactionUseCases.deleteTransaction(event.transaction)
                    recentlyDeletedTrx = event.transaction
                }
            }

            is TransactionEvents.RestoreTransaction -> {
                viewModelScope.launch {
                    transactionUseCases.addTransaction(recentlyDeletedTrx?:return@launch)
                    recentlyDeletedTrx=null
                }
            }
        }
    }

    private fun getTransaction(transactionType: TransactionType){
        getTrxJob?.cancel()
        getTrxJob = transactionUseCases.getTransactions(transactionType).onEach {
            trx->
            _state.value = state.value.copy(transactions = trx,transactionType=transactionType)
        }
            .launchIn(viewModelScope)
    }
}