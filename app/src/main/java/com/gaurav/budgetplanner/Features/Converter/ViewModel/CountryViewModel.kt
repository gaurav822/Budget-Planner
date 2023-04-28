package com.gaurav.budgetplanner.Features.Converter.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gaurav.budgetplanner.Features.Converter.Model.Country

class CountryViewModel:ViewModel() {

    private val selected: MutableLiveData<Country> = MutableLiveData<Country>()

    fun select(item: Country) {
        selected.setValue(item)
    }

    fun getSelected(): LiveData<Country>? {
        return selected
    }
}