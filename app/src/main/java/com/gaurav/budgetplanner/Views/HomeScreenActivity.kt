package com.gaurav.budgetplanner.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityHomeScreenBinding

class HomeScreenActivity : BaseActivity() {
    private var _binding:ActivityHomeScreenBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}