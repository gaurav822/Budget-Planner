package com.gaurav.budgetplanner.features.converter.data.data_source

import com.gaurav.budgetplanner.features.converter.data.data_source.dto.ConversionDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyConvertApi {

    @GET("pair/{baseCode}/{targetCode}")
    suspend fun convertCurrency(
        @Path("baseCode") baseCode:String,
        @Path("targetCode") targetCode:String) : ConversionDto
}