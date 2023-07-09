package com.gaurav.budgetplanner.features.expensetracker.presentation.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account
import com.gaurav.budgetplanner.features.expensetracker.domain.model.InValidTransactionException
import com.gaurav.budgetplanner.features.expensetracker.domain.use_case.TransactionUseCases
import com.gaurav.budgetplanner.features.expensetracker.domain.util.TransactionType
import com.gaurav.budgetplanner.features.expensetracker.presentation.AddEditRecordEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddEditRecordViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases,
    savedStateHandle: SavedStateHandle

):ViewModel(){

    private val _amount = mutableStateOf("")
    private val amount: State<String> = _amount

    private val _category = mutableStateOf("")
    private val category:State<String> = _category

    private val _comment = mutableStateOf("")
    private val comment:State<String> = _comment

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentRecordId:Int? =null

    init {
        savedStateHandle.get<Int>("recordId")?.let {
            recordId -> {
                if(recordId!=-1){
                    viewModelScope.launch {
                        transactionUseCases.getTransaction(recordId)?.also {
                            record ->
                            currentRecordId = record.id
                            _amount.value = record.amount
                            _category.value = record.category
                            _comment.value = record.comment
                        }
                    }
                }
        }
        }
    }

    fun onEvent(event:AddEditRecordEvent){
        when(event){
            is AddEditRecordEvent.EnteredAmount -> {
                _amount.value = event.value
            }
            is AddEditRecordEvent.EnteredCategory -> {
                _category.value = event.value
            }
            is AddEditRecordEvent.EnteredComment -> {
                _comment.value = event.value
            }

            is AddEditRecordEvent.SaveRecord -> {
                viewModelScope.launch {
                    try {
                        transactionUseCases.addTransaction(
                            Account(
                                amount = amount.value,
                                category = category.value,
                                comment = comment.value,
                                transactionType = "E",
                                id = currentRecordId!!,
                                timeStamp = System.currentTimeMillis(),
                                date = ""
                            )
                        )
                        _eventFlow.emit(UIEvent.SaveRecord)

                    } catch (e:InValidTransactionException){
                        _eventFlow.emit(UIEvent.ShowSnackBar(message = e.message ?: "Problem saving the record"))
                    }
                }
            }
        }
    }

    sealed class UIEvent{
        data class ShowSnackBar(val message:String):UIEvent()
        object SaveRecord:UIEvent()
    }

}