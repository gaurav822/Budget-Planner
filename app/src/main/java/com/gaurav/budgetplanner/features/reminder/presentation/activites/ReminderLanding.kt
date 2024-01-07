package com.gaurav.budgetplanner.features.reminder.presentation.activites

import android.app.AlertDialog
import android.app.NotificationManager
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaurav.budgetplanner.BudgetPlannerApp
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityReminderLandingBinding
import com.gaurav.budgetplanner.features.reminder.domain.model.Reminder
import com.gaurav.budgetplanner.features.reminder.presentation.adapter.ReminderAdapter
import com.gaurav.budgetplanner.features.reminder.presentation.viewmodel.ReminderViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import com.gaurav.budgetplanner.Utils.Constants
import com.gaurav.budgetplanner.Utils.Utils
import com.gaurav.budgetplanner.Views.HomeScreenActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

@AndroidEntryPoint
class ReminderLanding :BaseActivity(){
    private var _binding: ActivityReminderLandingBinding?= null
    private val binding get() = _binding!!
    private val viewModel: ReminderViewModel by viewModels()
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
            checkNotificationPermission()
        }
    }

    private fun checkNotificationPermission(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),Constants.NOTIFICATION_CODE)
        }
        else{
            startReminderActivity()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==Constants.NOTIFICATION_CODE){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                startReminderActivity()
            }
            else{
                showSettingsDialog()
            }
        }
    }

    private fun startReminderActivity(){
        val intent = Intent(this, CreateReminderPage::class.java)
        startActivity(intent)
    }

    private fun showSettingsDialog(){
        val builder = MaterialAlertDialogBuilder(this,R.style.CustomAlertDialogStyle)
        builder.setTitle("Open Settings")
        builder.setMessage("Notification permission is required. Open app settings?")
        builder.setPositiveButton("Yes") { _, _ ->
            // Open app settings
            openAppSettings()
        }
        builder.setNegativeButton("No") { _, _ ->
            // Handle the case when the user chooses not to open settings
            Toast.makeText(this,"Notification permission is required",Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }

    private fun openAppSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            startActivity(intent)
        } else {
            val intent = Intent("android.settings.APP_NOTIFICATION_SETTINGS")
                .putExtra("app_package", packageName)
                .putExtra("app_uid", applicationInfo.uid)
            startActivity(intent)
        }
    }

    private fun goToHomeScreen(){
        val homeScreenIntent = Intent(this, HomeScreenActivity::class.java)
        homeScreenIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)

        startActivity(homeScreenIntent)
        // Finish the ReminderLandingActivity
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        goToHomeScreen()
        return true
    }

    override fun onBackPressed() {
        goToHomeScreen()
        super.onBackPressed()
    }
}