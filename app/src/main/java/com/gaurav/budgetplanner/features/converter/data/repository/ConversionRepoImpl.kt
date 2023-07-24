package com.gaurav.budgetplanner.features.converter.data.repository

import com.gaurav.budgetplanner.features.converter.data.data_source.CurrencyConvertApi
import com.gaurav.budgetplanner.features.converter.data.data_source.dto.ConversionDto
import com.gaurav.budgetplanner.features.converter.domain.repository.ConversionRepository
import javax.inject.Inject

class ConversionRepoImpl @Inject constructor(
    private val api:CurrencyConvertApi
) : ConversionRepository{
    override suspend fun convertCurrency(baseCode: String, targetCode: String): ConversionDto {
        return api.convertCurrency(baseCode, targetCode)
    }
}