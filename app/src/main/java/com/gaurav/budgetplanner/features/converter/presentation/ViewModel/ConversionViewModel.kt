package com.gaurav.budgetplanner.features.converter.presentation.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaurav.budgetplanner.features.converter.common.Resource
import com.gaurav.budgetplanner.features.converter.domain.model.Conversion
import com.gaurav.budgetplanner.features.converter.domain.use_case.ConvertUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ConversionViewModel @Inject constructor(
    private val convertUseCase:ConvertUseCase,
    savedStateHandle: SavedStateHandle
):ViewModel() {

    private val _state = mutableStateOf(ConversionState())
    val state: State<ConversionState> = _state

    fun convertCurrency(baseCode:String,targetCode:String) {
        convertUseCase(baseCode, targetCode).onEach {
            result->
            when(result){
                is Resource.Success-> {
                    _state.value = ConversionState(data = result.data)
                }

                is Resource.Error -> {
                    _state.value = ConversionState(error = result.message ?: "An unexpected Error Occurred")
                }

                is Resource.Loading -> {
                    _state.value = ConversionState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}