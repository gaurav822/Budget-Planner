package com.gaurav.budgetplanner.features.reminder.Service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationBroadCastReceiver():BroadcastReceiver() {
    override fun onReceive(p0: Context, intent: Intent) {
        val title = intent.getStringExtra(NotificationService.EXTRA_TITLE)
        val description = intent.getStringExtra(NotificationService.EXTRA_DESCRIPTION)
        val service = NotificationService(p0)
        service.showNotification(title!!,description!!)
    }
}