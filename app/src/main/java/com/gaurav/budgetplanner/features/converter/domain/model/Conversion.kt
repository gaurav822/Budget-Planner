package com.gaurav.budgetplanner.features.converter.domain.model

import com.gaurav.budgetplanner.features.converter.data.data_source.dto.ConversionDto

class Conversion(
    val result:String,
    val baseCode:String,
    val targetCode:String,
    val rate:Double
)