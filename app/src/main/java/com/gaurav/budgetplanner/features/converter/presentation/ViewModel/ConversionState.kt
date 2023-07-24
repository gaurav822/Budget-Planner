package com.gaurav.budgetplanner.features.converter.presentation.ViewModel

import com.gaurav.budgetplanner.features.converter.domain.model.Conversion

data class ConversionState(
    val isLoading:Boolean=false,
    val data: Conversion? = null,
    val error:String = ""
)