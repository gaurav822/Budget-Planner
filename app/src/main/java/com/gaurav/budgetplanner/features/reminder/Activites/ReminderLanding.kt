package com.gaurav.budgetplanner.features.reminder.Activites

import android.os.Bundle
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityReminderLandingBinding

class ReminderLanding :BaseActivity(){
    private var _binding: ActivityReminderLandingBinding?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityReminderLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewClickEvents()
    }

    private fun viewClickEvents(){
        binding.clCreateReminder.setOnClickListener {

        }
    }
}