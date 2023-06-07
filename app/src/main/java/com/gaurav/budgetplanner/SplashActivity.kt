package com.gaurav.budgetplanner

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityCountryListBinding
import com.gaurav.budgetplanner.databinding.ActivitySplashBinding
import com.gaurav.budgetplanner.features.converter.Activities.CountryListActivity

class SplashActivity : BaseActivity() {
    private var _binding: ActivitySplashBinding?= null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        binding.proceedStart.setOnClickListener {
            startActivity(Intent(this,CountryListActivity::class.java))
        }
    }
}