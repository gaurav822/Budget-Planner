package com.gaurav.budgetplanner.features.settings.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivitySettingsBinding
import com.gaurav.budgetplanner.features.Onboarding.presentation.Views.Activities.CurrencySelectActivity

class SettingsActivity : BaseActivity() {
    private var _binding:ActivitySettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        viewClickEvents()
    }

    private fun viewClickEvents(){
        binding.clPin.setOnClickListener {
            startActivity(Intent(this,AppPinActivity::class.java))
        }

        binding.clDefaultCurrency.setOnClickListener {
            startActivity(Intent(this,CurrencySelectActivity::class.java))
        }

    }

    private fun init(){
        setSupportActionBar(binding.toolbar.mainGenericToolbar)
        binding.toolbar.apply {
            toolbarIconLayout.visibility=View.GONE
            toolbarTitle.text = getString(R.string.settings)
            toolbarTitle.textSize = 20f
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }
}