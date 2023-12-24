package com.gaurav.budgetplanner

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters


class MyWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val notificationId =  inputData.getInt("notification_id",0)

        if (notificationId != -1) {
            // Perform the necessary background tasks, e.g., call updateChecked in ReminderViewModel

        }

        // Example: Log a message
        Log.d("MyWorker", "Background task executed")
        return Result.success()
    }

}