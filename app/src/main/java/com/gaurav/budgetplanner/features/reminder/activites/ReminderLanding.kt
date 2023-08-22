package com.gaurav.budgetplanner.features.reminder.activites

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
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

            val delayMillis: Long = 10000
            setNotificationAlarm(this, delayMillis,"Gaurav","Dahal")
        }
    }

    private fun setNotificationAlarm(context: Context, delayMillis: Long,title:String,description:String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationBroadCastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val triggerTime = System.currentTimeMillis() + delayMillis
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }
}