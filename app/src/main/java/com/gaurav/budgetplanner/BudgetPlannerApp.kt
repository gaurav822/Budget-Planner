package com.gaurav.budgetplanner

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.support.multidex.MultiDexApplication
import com.gaurav.budgetplanner.Utils.Constants
import com.gaurav.budgetplanner.features.reminder.Service.NotificationService
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BudgetPlannerApp:MultiDexApplication(){

     private var sharedPreferences: SharedPreferences?=null

    override fun onCreate() {
        super.onCreate()
        sharedPreferences = getSharedPreferences(
            Constants.PREF_SHARED_KEY,
            MODE_PRIVATE
        )
        instance= this

        createNotificationChannel()
    }

    private fun createNotificationChannel(){
        val channel = NotificationChannel(
            NotificationService.CHANNEL_ID,
            "Reminder",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = "Used for reminders"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        private lateinit var instance: BudgetPlannerApp
        fun getStorage(): SharedPreferences {
            return instance.sharedPreferences!!
        }
    }

}