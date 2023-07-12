package com.gaurav.budgetplanner

import android.app.Application
import android.content.SharedPreferences
import android.support.multidex.MultiDexApplication
import com.gaurav.budgetplanner.Utils.Constants
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
    }

    companion object {
        private lateinit var instance: BudgetPlannerApp
        fun getStorage(): SharedPreferences {
            return instance.sharedPreferences!!
        }
    }

}