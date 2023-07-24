package com.gaurav.budgetplanner.features.converter.domain.repository

import com.gaurav.budgetplanner.features.converter.data.data_source.dto.ConversionDto

interface ConversionRepository {

    suspend fun convertCurrency(baseCode:String,targetCode:String):ConversionDto
}