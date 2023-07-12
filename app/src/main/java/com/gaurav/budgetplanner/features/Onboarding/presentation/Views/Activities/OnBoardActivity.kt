package com.gaurav.budgetplanner.features.Onboarding.presentation.Views.Activities

import android.content.Intent
import android.os.Bundle
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityOnboardBinding

class OnBoardActivity : BaseActivity() {
    private var _binding: ActivityOnboardBinding?= null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        binding.proceedStart.setOnClickListener {
            startActivity(Intent(this, CurrencySelectActivity::class.java))
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    override fun onSupportNavigateUp(): Boolean {
        finishAffinity()
        return true
    }
}