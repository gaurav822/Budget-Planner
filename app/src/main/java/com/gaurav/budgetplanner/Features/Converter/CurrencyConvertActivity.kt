package com.gaurav.budgetplanner.Features.Converter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.databinding.ActivityCurrencyConvertBinding
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class CurrencyConvertActivity : AppCompatActivity() {
    private var _binding:ActivityCurrencyConvertBinding?= null
    private val binding get() = _binding!!
    private var currentNumber:String = ""

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

    }

    private fun setNumberOnHeader(number:String){
        if(currentNumber.length<=15){
            currentNumber += number
            binding.fromValueCurr.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(currentNumber.toLong())
        }
    }

    private fun openCountryList(){
        startActivity(Intent(this,CountryListActivity::class.java))
    }
}