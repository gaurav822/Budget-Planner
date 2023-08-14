package com.gaurav.budgetplanner.Views.Activity

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gaurav.budgetplanner.R
import com.gaurav.budgetplanner.Utils.Utils

open class BaseActivity: AppCompatActivity() {

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}