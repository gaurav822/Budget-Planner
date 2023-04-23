package com.gaurav.budgetplanner.Features.Converter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.databinding.ActivityCountryListBinding
import com.gaurav.budgetplanner.databinding.ActivityCurrencyConvertBinding

class CountryListActivity : AppCompatActivity() {
    private var _binding: ActivityCountryListBinding?= null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCountryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarCountry.mainGenericToolbar)
        binding.toolbarCountry.toolbarTitle.text =  "Currencies"
    }
}