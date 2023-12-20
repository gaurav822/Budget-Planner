package com.gaurav.budgetplanner.features.reminder.presentation.activites

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Utils.Utils
import com.gaurav.budgetplanner.Views.Activity.BaseActivity
import com.gaurav.budgetplanner.databinding.ActivityCreateReminderBinding
import com.gaurav.budgetplanner.features.expensetracker.presentation.Activity.TransactionDetailActivity
import com.gaurav.budgetplanner.features.reminder.domain.model.Reminder
import com.gaurav.budgetplanner.features.reminder.presentation.viewmodel.ReminderViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class CreateReminderPage:BaseActivity() {

    private var _binding: ActivityCreateReminderBinding?= null
    private val binding get() = _binding!!
    private var reminderName:String = ""
    private var comment:String = ""
    private var reminderDate:Long = 0L
    private var isValidName = false
    private var reminder:Reminder?= null
    private var bundle:Bundle? = null
    private var editModeOn = false
    private var isValidTime = true


    private lateinit var reminderViewModel: ReminderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        reminderDate = System.currentTimeMillis()
        setSupportActionBar(binding.toolbar.mainGenericToolbar)
        binding.toolbar.apply {
            toolbarIconLayout.visibility= View.GONE
            toolbarTitle.text = getString(R.string.create_reminder)
            toolbarTitle.textSize = 20f
        }
        setMaterialTextWatcher(binding.inputReminder.editText!!,R.id.input_reminder)
        setDateTime()
        viewClickEvents()
        reminderViewModel = ViewModelProvider(this)[ReminderViewModel::class.java]
        getIntentData()

    }

    private fun getIntentData(){
        bundle = intent?.extras
        bundle?.let {
            reminder = bundle?.getSerializable("reminder") as Reminder
        }
        reminder?.let {
            triggerEditMode()
        }
    }


    private fun triggerEditMode(){
        editModeOn = true
        hour = reminder?.hour!!
        minute = reminder?.minute!!
        trackDate = reminder?.date!!
        binding.apply {
            deleteButton.visibility=View.VISIBLE
            toolbar.toolbarTitle.text = getString(R.string.edit_reminder)
            addText.text = "Save"
            inputReminder.editText?.setText(reminder?.name)
            inputComment.editText?.setText(reminder?.comment)
            day.text = Utils.getFormattedDateFromMillis(reminder?.date!!,"MMMM dd, YYYY")
            time.text = "${hour}:${String.format("%02d", minute)}"

        }
        isValidTime = Utils.checkIfPastDate(trackDate,reminder?.hour!!,reminder?.minute!!)
        isValidData()

    }


    private fun setMaterialTextWatcher(editText: EditText, id:Int) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                when (id) {
                    R.id.input_reminder -> {
                        isValidData()
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {
                //
            }
        })
    }


    private fun isValidData(){
       isValidName =  binding.inputReminder.editText?.text.toString().isNotEmpty()
        if(isValidName && isValidTime) {
            binding.proceedStart.setBackgroundResource(R.drawable.boundry_proceed_button)
            binding.addText.setTextColor(Color.parseColor("#c6000000"))
        }
        else{
            binding.proceedStart.setBackgroundResource(R.drawable.boundry_proceed_disabled)
            binding.addText.setTextColor(Color.parseColor("#796727"))
        }
    }

    private fun viewClickEvents(){

        binding.day.setOnClickListener {
            pickDayForReminder()
        }

        binding.time.setOnClickListener {
            pickTimeForReminder()
        }

        binding.proceedStart.setOnClickListener {
            if(!isValidName){
                Toast.makeText(this,"Please enter valid reminder name",Toast.LENGTH_SHORT).show()
            }
            else if(!isValidTime){
                Toast.makeText(this,"Past is in past. Please check date or time !!",Toast.LENGTH_SHORT).show()
            }
            else{
                val insertReminder = Reminder(
                    name = binding.inputReminder.editText?.text.toString(),
                    date = reminderDate,
                    hour = hour,
                    minute = minute,
                    comment = binding.inputComment.editText?.text.toString(),
                )
                if(editModeOn){
                    insertReminder.id = reminder?.id!!
                    insertReminder.isActive = reminder?.isActive!!
                    reminderViewModel.updateRecord(insertReminder)
                    Toast.makeText(this,"Reminder updated Successfully !",Toast.LENGTH_SHORT).show()
                }
                else {
                    reminderViewModel.addRecord(insertReminder)
                    Toast.makeText(this,"Reminder Added Successfully !",Toast.LENGTH_SHORT).show()
                }

                if(insertReminder.isActive){
                    reminderViewModel.scheduleAlarm(this,insertReminder)
                }

                finish()
            }
        }


        binding.deleteButton.setOnClickListener {
            showDeleteDialog()
        }
    }




    private fun showDeleteDialog(){
        val builder = MaterialAlertDialogBuilder(this, R.style.CustomAlertDialogStyle)
        builder.setTitle("Are you sure you want to delete the reminder?")
        builder.setPositiveButton("YES") { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
            reminderViewModel.deleteRecord(reminder!!)
            finish()
        }
        builder.setNegativeButton("NO") { dialog: DialogInterface, _: Int ->
            Toast.makeText(this, "Delete Cancelled", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        builder.show()
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
            reminderDate = trackDate
            isValidTime = Utils.checkIfPastDate(reminderDate,reminder?.hour!!,reminder?.minute!!)
            isValidData()
        }
    }

    private var hour:Int = 0
    private var minute:Int = 0

    private fun pickTimeForReminder(){
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setTheme(R.style.CustomTimePickerTheme)
            .setHour(hour)
            .setMinute(minute)
            .build()

        timePicker.addOnPositiveButtonClickListener {
            hour = timePicker.hour
            minute = timePicker.minute
            val selectedTime = "${String.format("%02d",hour)}:${String.format("%02d", minute)}"
            binding.time.text = selectedTime
            isValidTime = Utils.checkIfPastDate(reminderDate,timePicker.hour,timePicker.minute)
            isValidData()
        }

        timePicker.show(supportFragmentManager, "timePicker")
    }

    private fun setDateTime() {

        binding.day.text =
            Utils.getFormattedDateFromMillis(reminderDate, "MMMM dd, YYYY")

        val currentTime = Calendar.getInstance()

        //for increasing hour by 1
        currentTime.add(Calendar.HOUR_OF_DAY, 1)
        val updatedTime = currentTime.time

        val updatedCalendar = Calendar.getInstance()
        updatedCalendar.time = updatedTime

        val updatedHour = updatedCalendar.get(Calendar.HOUR_OF_DAY)
        val updatedMinute = updatedCalendar.get(Calendar.MINUTE)
        hour = updatedHour
        minute = updatedMinute

        binding.time.text = "${updatedHour}:${String.format("%02d", updatedMinute)}"

    }


    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }
}