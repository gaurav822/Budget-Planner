package com.gaurav.budgetplanner.features.converter.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.gaurav.budgetplanner.features.converter.ViewModel.CountryViewModel
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityCurrencyConvertBinding
import java.text.NumberFormat
import java.util.*
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.gaurav.budgetplanner.Utils.Utils
import com.gaurav.budgetplanner.Views.Components.IconMapper
import com.gaurav.budgetplanner.features.Onboarding.presentation.Views.Activities.CurrencySelectActivity
import com.gaurav.budgetplanner.features.converter.model.CurrencyModel
import com.gaurav.budgetplanner.features.expensetracker.domain.model.Account

class CurrencyConvertActivity : BaseActivity() {
    private var _binding:ActivityCurrencyConvertBinding?= null
    private val binding get() = _binding!!
    private var currentNumber:String = ""
    private var toCurrencyVal: Double = 88.0
    private val model:CountryViewModel by viewModels()
    private var selectedCurrency:CurrencyModel?=null
    private var isFrom:Boolean = true
    private lateinit var fromCurrency:CurrencyModel
    private lateinit var toCurrency:CurrencyModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCurrencyConvertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        clickEvents()
    }

    private fun init(){
        fromCurrency = CurrencyModel(currencySymbol = "$", currencyCode = "AUD", countryFlag = "")
        toCurrency = CurrencyModel(currencySymbol = "Rs", currencyCode = "NPR", countryFlag = "")
        binding.tvLatestTime.text = Utils.getCurrentDateTime()
        binding.apply {
            tvFromData.text = "1 AUD = "
            tvToData.text = "88.76 NPR"
        }
    }

    private fun clickEvents(){
        binding.clDetails1.setOnClickListener {
            isFrom=true
            openCountryList()
        }

        binding.clDetails2.setOnClickListener {
            isFrom = false
            openCountryList()
        }

        binding.zero.setOnClickListener {
            if(currentNumber.isNotEmpty()){
                setNumberOnHeader("0")
            }
        }

        binding.one.setOnClickListener {
            setNumberOnHeader("1")
        }

        binding.two.setOnClickListener {
            setNumberOnHeader("2")
        }

        binding.three.setOnClickListener {
            setNumberOnHeader("3")
        }

        binding.four.setOnClickListener {
            setNumberOnHeader("4")
        }

        binding.five.setOnClickListener {
            setNumberOnHeader("5")
        }
        binding.six.setOnClickListener {
            setNumberOnHeader("6")
        }

        binding.seven.setOnClickListener {
            setNumberOnHeader("7")
        }

        binding.eight.setOnClickListener {
            setNumberOnHeader("8")
        }

        binding.nine.setOnClickListener {
            setNumberOnHeader("8")
        }

        binding.dotView.setOnClickListener {
            if(!currentNumber.contains("."))  setNumberOnHeader(".")
        }

        binding.btnReset.setOnClickListener {
            currentNumber = ""
            binding.fromValueCurr.text = "0"
            binding.toCurrValue.text = "0"
        }

        binding.imgRefresh.setOnClickListener {
            binding.tvLatestTime.text = Utils.getCurrentDateTime()
        }

    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent: Intent? = result.data
            intent?.extras.let {
                selectedCurrency = it?.getSerializable("currency") as CurrencyModel
                binding.apply {
                    if(isFrom){
                        tvSymbol1.text=selectedCurrency?.currencyCode
                        Glide.with(applicationContext)
                            .asBitmap()
                            .load(selectedCurrency?.countryFlag)
                            .into(binding.ivCountry1)
                        fromCurrency = selectedCurrency!!
                    }
                    else{
                        tvSymbol2.text =selectedCurrency?.currencyCode
                        Glide.with(applicationContext)
                            .asBitmap()
                            .load(selectedCurrency?.countryFlag)
                            .into(binding.ivCountry2)
                        toCurrency = selectedCurrency!!
                    }

                    tvFromData.text = "1 ${fromCurrency.currencyCode} = "
                    tvToData .text =  "2000 ${toCurrency.currencyCode}"

                }
            }
        }
    }

    private fun setNumberOnHeader(number:String){
            currentNumber += number
            binding.fromValueCurr.text = currentNumber
            convertCurrency()
    }

    private fun convertCurrency(){
        val result = currentNumber.toDouble() * toCurrencyVal
        binding.toCurrValue.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(result)
    }

    private fun openCountryList(){
        val intent = Intent(this,CurrencySelectActivity::class.java)
        intent.putExtra("fromConverter",true)
        resultLauncher.launch(intent)
    }

    override fun onResume() {
        super.onResume()
        model.getCountry().observe(this) {
            binding.tvSymbol1.text = it.currencyCode
        }
    }
}