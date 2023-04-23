package com.gaurav.budgetplanner.Features.Converter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.databinding.ActivityCurrencyConvertBinding

class CurrencyConvertActivity : AppCompatActivity() {
    private var _binding:ActivityCurrencyConvertBinding?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCurrencyConvertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        clickEvents()
    }

    private fun init(){

    }

    private fun clickEvents(){
        binding.clDetails1.setOnClickListener {
            openCountryList()
        }

        binding.clDetails2.setOnClickListener {
            openCountryList()
        }
    }

    private fun openCountryList(){
        startActivity(Intent(this,CountryListActivity::class.java))
    }
}