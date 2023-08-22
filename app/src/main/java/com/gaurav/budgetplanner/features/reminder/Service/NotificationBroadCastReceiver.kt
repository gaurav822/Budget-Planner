package com.gaurav.budgetplanner.features.reminder.Service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationBroadCastReceiver:BroadcastReceiver() {
    override fun onReceive(p0: Context, p1: Intent) {
        val service = NotificationService(p0)
        service.showNotification()
    }
}