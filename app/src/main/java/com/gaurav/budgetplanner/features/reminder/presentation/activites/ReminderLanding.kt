package com.gaurav.budgetplanner.features.reminder.presentation.activites

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityReminderLandingBinding
import com.gaurav.budgetplanner.features.reminder.Service.NotificationService
import com.gaurav.budgetplanner.features.reminder.Service.NotificationTriggeredEvent
import com.gaurav.budgetplanner.features.reminder.domain.model.Reminder
import com.gaurav.budgetplanner.features.reminder.presentation.adapter.ReminderAdapter
import com.gaurav.budgetplanner.features.reminder.presentation.viewmodel.ReminderViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
//        EventBus.getDefault().unregister(this)
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

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onNotificationTriggered(event: NotificationTriggeredEvent) {
        Log.d("EVENTBUS","DATA IS AVAILABLE ON BACK AS WELL")
        viewModel.updateChecked(event.id,false)
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
            val intent = Intent(this, CreateReminderPage::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }
}