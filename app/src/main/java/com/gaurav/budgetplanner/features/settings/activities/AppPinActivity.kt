package com.gaurav.budgetplanner.features.settings.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.databinding.ActivityAppPinBinding

class AppPinActivity : AppCompatActivity() {
    private var _binding:ActivityAppPinBinding?=null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAppPinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        clickEvents()
    }

    private fun init(){
        setSupportActionBar(binding.toolbar.mainGenericToolbar)
        binding.toolbar.apply {
            toolbarIconLayout.visibility= View.GONE
            toolbarTitle.text = getString(R.string.PIN)
            toolbarTitle.textSize = 20f
        }
    }

    private fun clickEvents(){
        binding.tvSetPin.setOnClickListener {

        }
    }
}