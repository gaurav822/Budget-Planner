package com.gaurav.budgetplanner.features.converter.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gaurav.budgetplanner.features.converter.model.Country

class CountryViewModel:ViewModel() {

    private val selectedCountry = MutableLiveData<Country>()

    fun select(item: Country) {
        selectedCountry.value = item
    }

    fun getCountry(): LiveData<Country> = selectedCountry
}

//class CountryViewModelFactory (private val repository: CountryRepository) : ViewModelProvider.Factory{
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if(modelClass.isAssignableFrom(SavedArticleViewModel::class.java)){
//            return SavedArticleViewModel(repository) as T
//        }
//
//        throw IllegalArgumentException("Unknown Viewmodel class")
//
//    }
//
//}