package com.gaurav.budgetplanner.features.reminder.Service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.gaurav.budgetplanner.features.reminder.domain.use_case.ReminderUseCases
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationBroadCastReceiver():BroadcastReceiver() {

    @Inject
    lateinit var useCases: ReminderUseCases

    private val jobScope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(p0: Context, intent: Intent) {
        val title = intent.getStringExtra(NotificationService.EXTRA_TITLE)
        val description = intent.getStringExtra(NotificationService.EXTRA_DESCRIPTION)
        val id = intent.getIntExtra(NotificationService.EXTRA_ID,0)
        val service = NotificationService(p0)
        service.showNotification(title!!,description!!,id!!)


        try {
            jobScope.launch {
                useCases.updateIsActive(id, false)
            }
        } catch (e: Exception) {
            Log.d("EXCEPT", e.toString())
        }

    }
}