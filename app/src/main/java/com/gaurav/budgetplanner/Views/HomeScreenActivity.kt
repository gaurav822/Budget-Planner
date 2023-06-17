package com.gaurav.budgetplanner.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityHomeScreenBinding
import com.gaurav.budgetplanner.features.expensetracker.Activity.TransactionActivity

class HomeScreenActivity : BaseActivity() {
    private var _binding:ActivityHomeScreenBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickEvents()
    }

    private fun clickEvents(){
        binding.addIcon.setOnClickListener {
            startActivity(Intent(this,TransactionActivity::class.java))
        }
    }
}