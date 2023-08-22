package com.gaurav.budgetplanner.features.reminder.Activites

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityReminderLandingBinding
import com.gaurav.budgetplanner.features.reminder.Service.NotificationBroadCastReceiver
import com.gaurav.budgetplanner.features.reminder.Service.NotificationService

class ReminderLanding :BaseActivity(){
    private var _binding: ActivityReminderLandingBinding?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityReminderLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        viewClickEvents()
    }

    private fun init(){
        setSupportActionBar(binding.toolbar.mainGenericToolbar)
        binding.toolbar.apply {
            toolbarIconLayout.visibility= View.GONE
            toolbarTitle.text = getString(R.string.reminders)
            toolbarTitle.textSize = 20f
        }
    }

    private fun viewClickEvents(){
        binding.clCreateReminder.setOnClickListener {
//            val service = NotificationService(applicationContext)
//            service.showNotification()
            var intent= Intent(this,NotificationBroadCastReceiver::class.java)

            val activityPendingIntent = PendingIntent.getActivity(
                this,
                1,
                intent,
                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
            )

            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

            val time = System.currentTimeMillis()

            val tenSecInMillis = 10*1000;

            alarmManager.set(AlarmManager.RTC_WAKEUP,time+tenSecInMillis,activityPendingIntent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }
}