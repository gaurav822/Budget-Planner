package com.gaurav.budgetplanner.features.reminder.Service

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.gaurav.budgetplanner.features.reminder.presentation.viewmodel.ReminderViewModel
import org.greenrobot.eventbus.EventBus

class NotificationBroadCastReceiver():BroadcastReceiver() {
    override fun onReceive(p0: Context, intent: Intent) {
        val title = intent.getStringExtra(NotificationService.EXTRA_TITLE)
        val description = intent.getStringExtra(NotificationService.EXTRA_DESCRIPTION)
        val id = intent.getIntExtra(NotificationService.EXTRA_ID,0)
        val service = NotificationService(p0)
        service.showNotification(title!!,description!!,id!!)


//        val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(Application()).create(
//            ReminderViewModel::class.java)
//        viewModel.updateChecked(id, false)

        EventBus.getDefault().post(NotificationTriggeredEvent(id))
    }
}

data class NotificationTriggeredEvent(val id: Int)