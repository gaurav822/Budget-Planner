package com.gaurav.budgetplanner.features.converter.domain.use_case

import com.gaurav.budgetplanner.features.converter.domain.model.Conversion
import com.gaurav.budgetplanner.features.converter.domain.repository.ConversionRepository
import com.gaurav.budgetplanner.features.converter.common.Resource
import com.gaurav.budgetplanner.features.converter.data.data_source.dto.toConversion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ConvertUseCase @Inject constructor(
    private val repository: ConversionRepository
){

    operator fun invoke(baseCode:String,targetCode:String): Flow<Resource<Conversion>> = flow {
         try {
            emit(Resource.Loading())
             val data = repository.convertCurrency(baseCode = baseCode, targetCode = targetCode).toConversion()
             emit(Resource.Success(data))
         } catch (e:HttpException) {
             emit(Resource.Error(e.localizedMessage?:"An unexpected Error Occurred"))

         } catch (i:IOException){
             emit(Resource.Error("Couldn't reach Server, Please check your Internet Connection!"))
         }
    }

}