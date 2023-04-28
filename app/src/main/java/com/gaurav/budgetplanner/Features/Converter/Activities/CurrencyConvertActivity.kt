package com.gaurav.budgetplanner.Features.Converter.Activities

import android.content.Intent
import android.os.Bundle
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityCurrencyConvertBinding
import java.text.NumberFormat
import java.util.*

class CurrencyConvertActivity : BaseActivity() {
    private var _binding:ActivityCurrencyConvertBinding?= null
    private val binding get() = _binding!!
    private var currentNumber:String = ""
    private var toCurrencyVal: Double = 88.0

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
        startActivity(Intent(this, CountryListActivity::class.java))
    }
}