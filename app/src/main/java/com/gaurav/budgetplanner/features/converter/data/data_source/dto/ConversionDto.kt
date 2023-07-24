package com.gaurav.budgetplanner.features.converter.data.data_source.dto

import com.gaurav.budgetplanner.features.converter.domain.model.Conversion
import com.google.gson.annotations.SerializedName

data class ConversionDto(
    @SerializedName("base_code")
    val baseCode: String,
    @SerializedName("conversion_rate")
    val conversionRate: Double,
    @SerializedName("documentation")
    val documentation: String,
    @SerializedName("result")
    val result: String,
    @SerializedName("target_code")
    val targetCode: String,
    @SerializedName("terms_of_use")
    val termsOfUse: String,
    @SerializedName("time_last_update_unix")
    val timeLastUpdateUnix: Int,
    @SerializedName("time_last_update_utc")
    val timeLastUpdateUtc: String,
    @SerializedName("time_next_update_unix")
    val timeNextUpdateUnix: Int,
    @SerializedName("time_next_update_utc")
    val timeNextUpdateUtc: String

) :java.io.Serializable

fun ConversionDto.toConversion() : Conversion {
    return Conversion(
        result = result,
        baseCode = baseCode,
        targetCode = targetCode,
        rate = conversionRate
    )
}