package com.gaurav.budgetplanner.features.reminder.presentation.activites

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Utils.Utils
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityReminderLandingBinding
import com.gaurav.budgetplanner.features.expensetracker.presentation.Adapters.RecordAdapter
import com.gaurav.budgetplanner.features.expensetracker.presentation.ViewModel.RecordViewModel
import com.gaurav.budgetplanner.features.reminder.Service.NotificationBroadCastReceiver
import com.gaurav.budgetplanner.features.reminder.Service.NotificationService
import com.gaurav.budgetplanner.features.reminder.domain.model.Reminder
import com.gaurav.budgetplanner.features.reminder.presentation.Adapter.ReminderAdapter
import com.gaurav.budgetplanner.features.reminder.presentation.ViewModel.ReminderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReminderLanding :BaseActivity(){
    private var _binding: ActivityReminderLandingBinding?= null
    private val binding get() = _binding!!
    private lateinit var viewModel: ReminderViewModel
    private lateinit var adapter: ReminderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityReminderLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        viewClickEvents()
    }

    private fun init(){
        setSupportActionBar(binding.toolbar.mainGenericToolbar)
        viewModel = ViewModelProvider(this)[ReminderViewModel::class.java]
        binding.toolbar.apply {
            toolbarIconLayout.visibility= View.GONE
            toolbarTitle.text = getString(R.string.reminders)
            toolbarTitle.textSize = 20f
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){

        adapter = ReminderAdapter()
        binding.reminderRv.layoutManager = LinearLayoutManager(this)
        binding.reminderRv.adapter = adapter

        adapter.onSwitchChange = {
                id,isChecked,reminder ->
                viewModel.updateChecked(id,isChecked)
                updateAlaram(isChecked,reminder)
        }

        viewModel.getAllRecords().observe(this) { list ->
            list?.let {
                adapter.updateList(it)
            }
        }

        adapter.onItemClick = {
            val bundle = Bundle()
            bundle.putSerializable("reminder",it)
            val intent = Intent(this,CreateReminderPage::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    private fun updateAlaram(isChecked:Boolean,reminder:Reminder){
        if(isChecked) viewModel.scheduleAlarm(this,reminder)
        else viewModel.cancelAlarm(this,reminder)
    }

    private fun viewClickEvents(){
        binding.clCreateReminder.setOnClickListener {
//            val delayMillis: Long = 10000
//            setNotificationAlarm(this, delayMillis,"This is title","This is Description")
            val intent = Intent(this, CreateReminderPage::class.java)
            startActivity(intent)
        }
    }

    private fun setNotificationAlarm(context: Context, delayMillis: Long,title:String,description:String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationBroadCastReceiver::class.java)
        intent.putExtra(NotificationService.EXTRA_TITLE,title)
        intent.putExtra(NotificationService.EXTRA_DESCRIPTION,description)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val triggerTime = System.currentTimeMillis() + delayMillis
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }
}