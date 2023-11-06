package com.gaurav.budgetplanner.features.reminder.activites

import android.os.Bundle
import android.view.View
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Utils.Utils
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityCreateReminderBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class CreateReminderPage:BaseActivity() {

    private var _binding: ActivityCreateReminderBinding?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        setSupportActionBar(binding.toolbar.mainGenericToolbar)
        binding.toolbar.apply {
            toolbarIconLayout.visibility= View.GONE
            toolbarTitle.text = getString(R.string.create_reminder)
            toolbarTitle.textSize = 20f
        }

        setDateTime()

        viewClickEvents()

    }

    private fun viewClickEvents(){

        binding.day.setOnClickListener {
            pickDayForReminder()
        }

        binding.time.setOnClickListener {
            pickTimeForReminder()
        }
    }

    private var trackDate: Long = 0L

    private fun pickDayForReminder(){

        val materialDateBuilder = MaterialDatePicker.Builder.datePicker().setTheme(R.style.MyDatePickerTheme)
        if (trackDate == 0L) {
            trackDate = System.currentTimeMillis()
        }
        materialDateBuilder.setSelection(trackDate)
        materialDateBuilder.setTitleText(getText(R.string.select_day))

        // Setting max limit of dates to be picked
        val constraintsBuilder = CalendarConstraints.Builder()
//        constraintsBuilder.setEnd(System.currentTimeMillis())

        constraintsBuilder.setValidator(DateValidatorPointForward.now())


        // Setting the constraints on the materialDateBuilder
        materialDateBuilder.setCalendarConstraints(constraintsBuilder.build())

        val materialDatePicker: MaterialDatePicker<Long> = materialDateBuilder.build()

        materialDatePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")
        materialDatePicker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            trackDate = calendar.timeInMillis
            binding.day.text = Utils.getFormattedDateFromMillis(trackDate,"MMMM dd, YYYY")
        }
    }


    private fun pickTimeForReminder(){
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setTheme(R.style.CustomTimePickerTheme)
            .build()

        timePicker.addOnPositiveButtonClickListener {
            val selectedHour = timePicker.hour
            val selectedMinute = timePicker.minute

            val selectedTime = "$selectedHour:$selectedMinute"
            binding.time.text = selectedTime
        }

        timePicker.show(supportFragmentManager, "timePicker")
    }

    private fun setDateTime(){

        binding.day.text = Utils.getFormattedDateFromMillis(System.currentTimeMillis(),"MMMM dd, YYYY")

        val currentTime = Calendar.getInstance()
        currentTime.add(Calendar.HOUR_OF_DAY, 1)
        val updatedTime = currentTime.time
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formattedTime = timeFormat.format(updatedTime)
        binding.time.text = formattedTime

    }


    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }
}