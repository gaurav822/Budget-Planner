package com.gaurav.budgetplanner.features.reminder.Service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Views.HomeScreenActivity
import com.gaurav.budgetplanner.features.reminder.activites.ReminderLanding

class NotificationService(private val context:Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(title:String,description:String){
        val activityIntent = Intent(context,HomeScreenActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.icon_currency)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(1,notification)
    }

    companion object {
        const val CHANNEL_ID = "reminder_channel"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_DESCRIPTION = "extra_description"
    }
}