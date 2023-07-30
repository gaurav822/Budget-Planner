package com.gaurav.budgetplanner.features.converter.presentation.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityCurrencyConvertBinding
import java.text.NumberFormat
import java.util.*
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Utils.Utils
import com.gaurav.budgetplanner.features.Onboarding.presentation.Views.Activities.CurrencySelectActivity
import com.gaurav.budgetplanner.features.converter.domain.model.CurrencyModel
import com.gaurav.budgetplanner.features.converter.presentation.ViewModel.ConversionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.DecimalFormat

@AndroidEntryPoint
class CurrencyConvertActivity : BaseActivity() {
    private var _binding:ActivityCurrencyConvertBinding?= null
    private val binding get() = _binding!!
    private var currentNumber:String = "0"
    private var selectedCurrency: CurrencyModel?=null
    private var isFrom:Boolean = true
    private lateinit var fromCurrency: CurrencyModel
    private lateinit var toCurrency: CurrencyModel
    private val viewModel: ConversionViewModel by viewModels()
    private var conversionRate:Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCurrencyConvertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initData()
        listenViewModel()
        clickEvents()
    }

    private fun init(){
        viewModel.convertCurrency("AUD", "NPR")
        initData()
    }

    private fun initData(){
        setSupportActionBar(binding.toolbar)
        fromCurrency = CurrencyModel(currencySymbol = "$", currencyCode = "AUD", countryFlag = "https://flagsapi.com/AU/flat/64.png")
        toCurrency = CurrencyModel(currencySymbol = "Rs", currencyCode = "NPR", countryFlag = "https://flagsapi.com/NP/flat/64.png")
        setConversionData(fromCurrency,toCurrency)
        binding.tvLatestTime.text = Utils.getCurrentDateTime()
        binding.tvConversionData.text = String.format(
            getString(R.string.currency_conversion_format),
            "AUD", conversionRate, "NPR"
        )
    }

    private fun setConversionData(from:CurrencyModel,to:CurrencyModel){

        Glide.with(applicationContext)
            .asBitmap()
            .load(from.countryFlag)
            .into(binding.ivCountry1)

        Glide.with(applicationContext)
            .asBitmap()
            .load(to.countryFlag)
            .into(binding.ivCountry2)

        binding.tvSymbol1.text = from.currencyCode
        binding.tvSymbol2.text = to.currencyCode
    }

    private fun listenViewModel(){
        viewModel.state.onEach { conversionState ->
            val isLoading = conversionState.isLoading
            val data = conversionState.data
            val error = conversionState.error

            if (isLoading) {

            } else{
                conversionRate = DecimalFormat("#.##").format(data?.rate).toDouble()
                binding.tvConversionData.text = String.format(
                    getString(R.string.currency_conversion_format),
                    fromCurrency.currencyCode, conversionRate, toCurrency.currencyCode
                )
                convertCurrency()
            }
        }.launchIn(lifecycleScope)
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
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
            currentNumber = "0"
            binding.fromValueCurr.text = "0"
            binding.toCurrValue.text = "0"
        }

        binding.swapCountry.setOnClickListener {
            val temp = fromCurrency
            fromCurrency = toCurrency
            toCurrency = temp
            viewModel.convertCurrency(fromCurrency.currencyCode,toCurrency.currencyCode)
            setConversionData(fromCurrency,toCurrency)
            convertCurrency()
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
                }
            }

            viewModel.convertCurrency(fromCurrency.currencyCode,toCurrency.currencyCode)
        }
    }

    private fun setNumberOnHeader(number:String){
        if(currentNumber == "0"){
            currentNumber = ""
        }
        currentNumber += number
        binding.fromValueCurr.text = currentNumber
        convertCurrency()
    }

    private fun convertCurrency(){
        val result = currentNumber.toDouble() * conversionRate!!
        binding.toCurrValue.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(result)
    }

    private fun openCountryList(){
        val intent = Intent(this,CurrencySelectActivity::class.java)
        intent.putExtra("fromConverter",true)
        resultLauncher.launch(intent)
    }
}